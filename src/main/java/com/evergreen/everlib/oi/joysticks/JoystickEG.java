/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.evergreen.everlib.oi.joysticks;
import java.util.Arrays;
import java.util.List;

import com.evergreen.everlib.oi.OIExceptions;
import com.evergreen.everlib.shuffleboard.loggables.LoggableData;
import com.evergreen.everlib.shuffleboard.loggables.LoggableDouble;
import com.evergreen.everlib.shuffleboard.loggables.LoggableObject;
import com.evergreen.everlib.utils.Adjuster;
import com.evergreen.everlib.utils.UselessAdjuster;

import edu.wpi.first.wpilibj.Joystick;

/**
 * The base class for {@link Joystick}s in the Evergreen Framework.
 * <p>
 * <p>
 * Most importantly, it provides easy methods to invert and expontiate the
 * joytsick axis, and allowes {@link #getRawAxis(AxisType) getRawAxis} by
 * {@link AxisType}, for clearer code.
 * <p>
 * <p>
 * For more advance adjustments,this class contains an {@link Adjuster adjuster}
 * for each of the axes, which can be set using its
 * {@link #setAxisAdjuster(int, Adjuster)} method.
 * 
 * @author Atai Ambus
 */
public class JoystickEG extends Joystick implements LoggableObject, OIExceptions {

    /** The joystick's name (for logging purposes) */
    private final String m_name;

    /**
     * Whether the joystick is exponential - the position-to-value function
     * ({@link Joystick#getRawAxis(int)} is exponential or linear.
     */
    private boolean m_exponential = false;

    /**
     * Whether the joystick is inverted = should we multiply its values by -1?
     */
    private boolean m_inverted = false;

    /**
     * An array of the adjusters for each axis.
     */
    @SuppressWarnings("unchecked")
    private Adjuster<Double>[] m_adjusters = (Adjuster<Double>[]) new Adjuster[getAxisCount()];

    /**The default adjuster - applied if no adjuster was specified for an axis*/
    private Adjuster<Double> m_defaultAdjuster;

    /**
     * Constructs a {@link JoystickEG} at input port.
     * 
     * @param name - A name for the joystick (for logging purposes)
     * @param port - The joystick's port, as tuned in the Driver Station
     */
    public JoystickEG(String name, int port) {
        this(name, port, new UselessAdjuster<>());
    }

    /**
     * Constructs a {@link JoystickEG} at input port, adjusting its 
     * values according to input adjuster
     * 
     * @param name - A name for the joystick (for logging purposes)
     * @param port - The joystick's port, as tuned in the Driver Station
     * @param adjuster - A function to adjust the joystick's output
     */
    public JoystickEG(String name, int port, Adjuster<Double> adjuster) {
        super(port);
        m_name = name;
        m_defaultAdjuster = adjuster;
        Arrays.fill(m_adjusters, m_defaultAdjuster);

    }

    /**
     * Sets an {@link Adjuster} for a joystick axis - now when getting its values,
     * thhey will be first adjusted with this. 
     * Usefull for fixing faulty joysticks, exponentiating or tampering with a specific input.
     * 
     * @param axis - The axis to set the adjuser of
     * @param adjuster  - How to adjust the axis' values.
     * @throws AxisDoesNotExistException if the axis rquested does not exist on the joystick
     */
    public void setAxisAdjuster(Joystick.AxisType axis, Adjuster<Double> adjuster) {
       try {
        m_adjusters[axis.value] = adjuster;
       }

       catch (IndexOutOfBoundsException e) {
        throw new AxisDoesNotExistException(String.format(
            "Tried to set the adjuster of the %s, axis on %s, but it does not have such axis!",
            axis.name(), getName()),
            e);
       }
    }

    /**
     * Get the value of input axis, adjusted according to the joysticks' configuration.
     * @param axis - the axis to get the value of 
     * @return  the value of input axis, adjusted according to the joysticks' configuration.
     * @throws AxisDoesNotExistException if the requested axis does not exist on tbhis joystick.
     */
    @Override
    public double getRawAxis(int axis) throws AxisDoesNotExistException {
        try {
            double value = super.getRawAxis(axis);
    
            value = m_adjusters[axis].adjust(value);
    
            if (m_exponential)
                value *= Math.abs(value);
            if (m_inverted)
                value *= -1;
    
            return value;

        } catch (IndexOutOfBoundsException e) {
            throw new AxisDoesNotExistException(String.format(
                "Tried to set the get the value of %s, at axis %s, but it does not have such axis!",
                getName(), AxisType.values()[axis].name()),
                e);
        }

    }

    
    /**
     * Get the value of input axis, adjusted according to the joysticks' configuration.
     * @param axis - the axis to get the value of 
     * @return  the value of input axis, adjusted according to the joysticks' configuration.
     * @throws AxisDoesNotExistException if the requested axis does not exist on tbhis joystick.
     */
    public double getRawAxis(Joystick.AxisType axis) throws AxisDoesNotExistException {
        return getRawAxis(axis.value);
    }

    /**
     * Sets the default adjuster for this joystick - if no adjuster is specified for a 
     * requested axis, this will be used.
     * 
     * @param adjuster - the adjuster to set as default.
     */
    public void setDefaultAdjuster(Adjuster<Double> adjuster) {
        m_defaultAdjuster = adjuster;
    }

    /**
     * Sets the joystick exponential - values will be multiplied by their own,
     * absolute value, making a curved function as you move the joystick, rather than a
     * linear one. 
     */
    public void setExponential() {
        m_exponential = true;
    }

    /**
     * Sets the joystick inverted - its values will be multiplied by -1.
     */
    public void setInverted() {
        m_inverted = true;
    }

    /**Set all the joystic adjusters  */
    public void kill() {
        m_defaultAdjuster = (v) -> 0.0;
        Arrays.fill(m_adjusters, m_defaultAdjuster);
    }

    @Override
    public String getName() {
        return m_name;
    }

    @Override
    public List<LoggableData> getLoggableData() {
        return List.of(
            new LoggableDouble("X axis", this::getX),
            new LoggableDouble("Y axis", this::getY),
            new LoggableDouble("Z axis", this::getZ),
            new LoggableDouble("Throttle", this::getThrottle),
            new LoggableDouble("Twist", this::getTwist)
        );
    }
}
