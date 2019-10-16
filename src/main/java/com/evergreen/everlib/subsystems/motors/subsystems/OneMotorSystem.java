/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.evergreen.everlib.subsystems.motors.subsystems;

import java.util.Map;
import java.util.function.Supplier;

import com.evergreen.everlib.subsystems.sensors.DistanceSensor;
import com.evergreen.everlib.utils.ranges.Range;

import edu.wpi.first.wpilibj.command.Command;

/**
 * A motor subsystem the motors of are controlled collectively, 
 * and optionally includeds a distance sensor and a movement limit.
 * 
 * @author Atai Ambus
 */
public class OneMotorSystem extends MotorSubsystem {
   
    /**
     * Constructs a basic {@link OneMotorSystem} object according to input parameters. 
     * @param name - The name for the subsystem.
     * @param motor - The contoller of the subsystem's motors.
     */
    public OneMotorSystem(String name, MotorController motor)
    {
        super(name, motor);
    }

     /**
     * Constructs a basic {@link OneMotorSystem} object according to input parameters. 
     * @param name - The name for the subsystem.
     * @param distanceSensor - a sensor calcualting the distance the subsystem has gone through.
     * @param motor - The contoller of the subsystem's motors
     */
    public OneMotorSystem(String name, DistanceSensor distanceSensor, MotorController motor)
    {
        super(name, distanceSensor, motor);
    }

    /**
     * Constructs a basic {@link OneMotorSystem} object according to input parameters. 
     * @param name - The name for the subsystem.
     * @param distanceSensor - a sensor calcualting the distance the subsystem has gone through.
     * @param limit - The subsystem's movement limit.
     * @param motor - The contoller of the subsystem's motors
     */
    public OneMotorSystem(String name, DistanceSensor distanceSensor, Range limit, MotorController motor)
    {
        super(name, distanceSensor, limit, motor);
    }

    /**
     * Constructs a basic {@link OneMotorSystem} object according to input parameters. 
     * @param name - The name for the subsystem.
     * @param distanceSensor - a sensor calcualting the distance the subsystem has gone through.
     * @param limit - The subsystem's movement limit.
     * @param defaultCommand - The subsystem's default command.
     * @param motor - The contoller of the subsystem's motors
     */
    public OneMotorSystem(String name, DistanceSensor distanceSensor, Range limit, Command defaultCommand, MotorController motor)
    {
        super(name, distanceSensor, limit, defaultCommand, motor);
    }

    /**Deprecated since there is no need for index. Use {@link MotorSubsystem#move(double) move(double)}*/
    @Deprecated
    @Override
    public void set(int index, Supplier<Double> speed) {
        super.set(index, speed);
    }

    /**Deprecated since there is no need for index. Use {@link MotorSubsystem#move(double) move(double)}*/    
    @Override
    @Deprecated
    public void set(Map<Integer, Double> speedMap) {
        super.set(speedMap);
    }

    /**Deprecated since there is no need for index. Use {@link MotorSubsystem#move(double) move(double)}*/    
    @Override
    @Deprecated
    public void set(int index, double speed) {
        super.set(index, speed);
    }

    
}
