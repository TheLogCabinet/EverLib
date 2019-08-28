/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.evergreen7112.everlib.subsystems.motors.subsystems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.evergreen7112.everlib.functionalinterfaces.limits.Limit;
import com.evergreen7112.everlib.subsystems.motors.subsystems.MotorController.MotorType;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class MotorSubsystem extends Subsystem{
    protected MotorController[] motors;
    public Limit limit;
    protected Command defaultCommand;
    
    MotorSubsystem(MotorController... motors)
    {
        this.motors = motors;
        limit = () -> true;
    }

    MotorSubsystem(Limit limit, MotorController... motors)
    {
        this.motors = motors;
        this.limit = limit;
    }

    MotorSubsystem(Limit limit, Command defaultCommand, MotorController... motors)
    {
        this.motors = motors;
        this.limit = limit;
    }

    MotorSubsystem(MotorType motorsType, int... ports)
    {
        motors = new MotorController[ports.length];
        
        for(int i = 0; i < ports.length; i++)
        {
            motors[i] = new MotorController(motorsType, ports[i]);
        }

        this.limit = () -> true;
    }

    MotorSubsystem(Limit limit, MotorType motorsType, int... ports)
    {
        motors = new MotorController[ports.length];

        for(int i = 0; i < ports.length; i++)
        {
            motors[i] = new MotorController(motorsType, ports[i]);
        }
        
        this.limit = limit; 
    }

    MotorSubsystem(Limit limit, Command defaultCommand, MotorType motorsType, int... ports)
    {
        this.motors = new MotorController[ports.length];

        for(int i = 0; i < ports.length; i++)
        {
            motors[i] = new MotorController(motorsType, ports[i]);
        }
        
        this.limit = limit;
        this.defaultCommand = defaultCommand;
    }

    MotorSubsystem(Map<Integer, MotorType> motorMap)
    {
        ArrayList<MotorController> motors = new ArrayList<>();
        motorMap.forEach( (port, type) -> motors.add(new MotorController(type, port)) );
        this.motors = (MotorController[])motors.toArray();

        this.limit = () -> true;
    }
    
    MotorSubsystem(Limit limit, HashMap<Integer, MotorType> motorMap)
    {
        ArrayList<MotorController> motors = new ArrayList<>();
        motorMap.forEach( (port, type) -> motors.add(new MotorController(type, port)) );
        this.motors = (MotorController[])motors.toArray();

        this.limit = limit;
    }
    
    MotorSubsystem(Limit limit, Command defaultCommand, HashMap<Integer, MotorType> motorMap)
    {
        ArrayList<MotorController> motors = new ArrayList<>();
        motorMap.forEach( (port, type) -> motors.add(new MotorController(type, port)) );
        this.motors = (MotorController[])motors.toArray();

        this.limit = limit;
    }

    public void move(int index, double speed)
    {
        if(limit.inRange()) 
            motors[index].accept(speed);
    }

    public void move(int index, Supplier<Double> speed)
    {
        if(limit.inRange())        
            motors[index].accept(speed);
    }

    public void move(Map<Integer, Double> speedMap)
    {
        if(limit.inRange()) 
            speedMap.forEach(this::move);
    }

    public void stop()
    {
        for(MotorController motor : motors)
        {
            motor.accept(0.0);
        }
    }

    @Override
    protected void initDefaultCommand() {   
        if(defaultCommand != null)
        {
            setDefaultCommand(defaultCommand);
        }
    }
}
