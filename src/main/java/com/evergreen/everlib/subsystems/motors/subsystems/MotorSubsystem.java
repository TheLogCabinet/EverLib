package com.evergreen.everlib.subsystems.motors.subsystems;

import java.util.Map;
import java.util.function.Supplier;

import com.evergreen.everlib.subsystems.SubsystemEG;
import com.evergreen.everlib.subsystems.sensors.DistanceSensor;
import com.evergreen.everlib.utils.ranges.Range;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * A {@link Subsystem} consisting of one or more motor m_controllers.
 */
public class MotorSubsystem extends SubsystemEG {
    /**The subsystem's motor controllers. */
    protected MotorController[] m_controllers;
    
    /**Map mapping motorcontrollers to modifiers. */
    protected Supplier<Double> m_speedModifier;

    /**The range in which the subsystem is allowed to move. */
    protected Range m_Range;

    /**The sensor mesuring the distance the subsystem has gone. */
    protected DistanceSensor m_distanceSensor;

    /**The subsystem's default command */
    protected Command m_defaultCommand;
    
    public MotorSubsystem(String name, MotorController... motors)
    {
        super(name);    
        m_controllers = motors;
        m_Range = (v) -> true;
    }

    public MotorSubsystem(String name, DistanceSensor distanceSensor, MotorController... motors) {
        super(name);
        m_controllers = motors;
        m_distanceSensor = distanceSensor;
    }

    public MotorSubsystem(String name, DistanceSensor sensor, Range Range, MotorController... motors)
    {
        super(name);
        m_controllers = motors;
        m_distanceSensor = sensor;
        m_Range = Range;
    }

    public MotorSubsystem(String name, DistanceSensor distanceSensor, Range Range, 
        Command defaultCommand, MotorController... motors)
    {
        super(name);
        m_controllers = motors;
        m_distanceSensor = distanceSensor;
        m_Range = Range;
        m_defaultCommand = defaultCommand;
    }


    public void move(double speed) {
        for (MotorController control : m_controllers) {
            control.set(speed);
        }
    }

    public void set(int index, double speed)
    {
        if(canMove()) 
            m_controllers[index].set(speed * m_speedModifier.get());
    }

    public void set(Map<Integer, Double> speedMap)
    {
        if(canMove()) 
            speedMap.forEach(this::set);
    }

    public void stop()
    {
        for(MotorController motor : m_controllers)
        {
            motor.stopMotor();
        }

    }


    public MotorController[] getMotorControllers() {
        return m_controllers;
    }

    public void setSpeedModifier(double modifier) {
        m_speedModifier = () -> modifier;
    }

    public void setSpeedModifier(Supplier<Double> modifier) {
        m_speedModifier = modifier;
    }
 
    public boolean canMove() {
        return m_Range.inRange(getDistance()) && m_subsystemSwitch.get();
    }

    @Override
    protected void initDefaultCommand() {   
        if(m_defaultCommand != null)
        {
            setDefaultCommand(m_defaultCommand);
        }
    }
}
