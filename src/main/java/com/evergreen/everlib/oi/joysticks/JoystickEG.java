/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// multiplication resistance is the number of steps it takes when you multiply all digits of a number until you get a single digit.
// so 17's resistance is 1: 1*7 = 7.
// 876: 8*7*6 = 336: 3*3*6 = 54: 5*4 = 20: 2*0 = 0 4 
//277,777,788,888,899 is the current record for largest resistance. 11
//cool.
// I'm writing a program in C# to find the biggest resistance in a range of numbers

package com.evergreen.everlib.oi.joysticks;
import java.util.Arrays;

import com.evergreen.everlib.oi.OIExceptions;
import com.evergreen.everlib.utils.Adjuster;

import edu.wpi.first.wpilibj.Joystick;


/**
 * The base class for {@link Joystick}s in the Evergreen Framework.
 * 
 * Most importantly, it provides easy methods to ivert and expontiate the joytsick axis,
 * and allowes {@link #getRawAxis(AxisType) getRawAxis} by {@link AxisType}, for clearer code.
 * 
 * For more advance adjustments,this class contains an {@link Adjuster adjuster} for each of the axes, 
 * which can be set using its {@link #setAxisAdjuster(int, Adjuster)} method.
 */
public class JoystickEG extends Joystick {


    /**number of axes on the joystick */
    private static final int AXES_NUM = 5;

    /**An adjuster which makes no change. 
     * Since the code requires an adjuster, ff not specified - 
     * this will be the adjuster for all axesx.*/
    private static final Adjuster<Double> USELESS_ADJUSTER = (val) -> val;

    /**
     * Whether the joystick is exponential - the position-to-value function ({@link Joystick#getRawAxis(int)} 
     * is exponential or linear.
     */
    private boolean m_exponential = false;
    
    /**
     * Whether the joystick is inverted = should we multiply its values by -1?
     */
    private boolean m_inverted = false;

    @SuppressWarnings("unchecked")
    private Adjuster<Double>[] m_adjusters = (Adjuster<Double>[]) new Adjuster[AXES_NUM]; 
    
    private Adjuster<Double> m_defaultAdjuster;
    
    public JoystickEG(int port)
    {
        super(port);
        m_defaultAdjuster = USELESS_ADJUSTER;
        Arrays.fill(m_adjusters, USELESS_ADJUSTER);
    }

    public JoystickEG(int port, Adjuster<Double> adjuster)
    {
        super(port);
        this.m_defaultAdjuster = adjuster;
        Arrays.fill(m_adjusters, m_defaultAdjuster);
    }

    public void setAxisAdjuster(Joystick.AxisType axis, Adjuster<Double> adjuster)
    {
        m_adjusters[axis.value] = adjuster;
    }

    @Override
    public double getRawAxis(int axis) throws OIExceptions.AxisDoesNotExistException
    {
        if (axis > AXES_NUM) throw new OIExceptions.AxisDoesNotExistException();

        double value = super.getRawAxis(axis);

        value = m_adjusters[axis].adjust(value);
        
        if (m_exponential) value *= Math.abs(value);
        if (m_inverted) value *= -1;

        return value;
    }

    public double getRawAxis(Joystick.AxisType axis) throws OIExceptions.AxisDoesNotExistException
    {
        return getRawAxis(axis.value);
    }

    public void setDefaultAdjuster(Adjuster<Double> adjuster)
    {
        m_defaultAdjuster = adjuster;
    }

    public void setExponential() {
        m_exponential = true;
    }

    public void setInverted() {
        m_inverted = true;
    }

    public void kill() {
        m_defaultAdjuster = (v) -> 0.0;
    }
}
