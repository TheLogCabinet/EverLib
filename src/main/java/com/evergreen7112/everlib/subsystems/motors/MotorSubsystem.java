/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.evergreen7112.everlib.subsystems.motors;

import java.util.HashMap;
import java.util.function.Supplier;

import com.evergreen7112.everlib.subsystems.motors.Motor.MotorType;
import com.evergreen7112.everlib.subsystems.sensors.DistanceSesnsor;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class MotorSubsystem extends Subsystem{
    Motor[] motors;
    Supplier<Boolean> limit;
    
    MotorSubsystem(Motor... motors)
    {
        this.motors = motors;
        limit = () -> true;
    }

    MotorSubsystem(DistanceSesnsor distanceSensor, double minDistance, double maxDistance, 
        Motor... motors)
    {
        this.motors = motors;
        limit = () -> distanceSensor.inRange(minDistance, maxDistance);
    }

    MotorSubsystem(MotorType motorsType, int... ports)
    {
        motors = new Motor[ports.length];
        for(int i = 0; i < ports.length; i++)
        {
            motors[i] = new Motor(ports[i], motorsType);
        }
    }

    public void move(int index, double speed)
    {
        motors[index].accept(speed);
    }

    public void move(int index, Supplier<Double> speed)
    {
        motors[index].accept(speed);
    }

    public void move(HashMap<Integer, Double> speedMap)
    {
        speedMap.forEach(this::move);
    }

    public void stop()
    {
        for(Motor motor : motors)
        {
            motor.accept(0.0);
        }
    }

    @Override
    protected void initDefaultCommand() {
        
    }
}
