/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.evergreen7112.everlib.subsystems.motors.subsystems;

import java.util.Map;
import java.util.function.Supplier;

import com.evergreen7112.everlib.functionalinterfaces.limits.Limit;
import com.evergreen7112.everlib.subsystems.motors.subsystems.MotorController.MotorType;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Add your docs here.
 */
public class OneControllerSystem extends MotorSubsystem {
    
    OneControllerSystem(MotorType motorType, int port)
    {
        super(motorType, port);
    }

    OneControllerSystem(Limit limit, MotorType motorType, int port)
    {
        super(limit, motorType, port);
    }
    OneControllerSystem(Limit limit, Command defaultCommand, MotorType motorType, int port)
    {
        super(limit, defaultCommand, motorType, port);
    }

    OneControllerSystem(MotorController motor)
    {
        super(motor);
    }

    OneControllerSystem(Limit limit, MotorController motor)
    {
        super(limit, motor);
    }

    OneControllerSystem(Limit limit, Command defaultCommand, MotorController motor)
    {
        super(limit, defaultCommand, motor);
    }

    OneControllerSystem(Talon motor)
    {
        super(new MotorController(MotorType.TALON, motor.getChannel()));
    }

    OneControllerSystem(Limit limit, Talon motor)
    {
        super(limit, new MotorController(MotorType.TALON, motor.getChannel()));
    }

    OneControllerSystem(Limit limit, Command defaultCommand, Talon motor)
    {
        super(limit, defaultCommand, new MotorController(MotorType.TALON, motor.getChannel()));
    }

    OneControllerSystem(Victor motor)
    {
        super(new MotorController(MotorType.VICTOR, motor.getChannel()));
    }

    OneControllerSystem(Limit limit, Victor motor)
    {
        super(limit, new MotorController(MotorType.VICTOR, motor.getChannel()));
    }

    OneControllerSystem(Limit limit, Command defaultCommand, Victor motor)
    {
        super(limit, defaultCommand, new MotorController(MotorType.VICTOR, motor.getChannel()));
    }

    /**Deprecated since there is no need for index.*/
    @Deprecated
    @Override
    public void move(int index, Supplier<Double> speed) {
        super.move(index, speed);
    }

    /**Deprecated since there is no need for index.*/
    @Override
    @Deprecated
    public void move(Map<Integer, Double> speedMap) {
        super.move(speedMap);
    }

    /**Deprecated since there is no need for index.*/
    @Override
    @Deprecated
    public void move(int index, double speed) {
        super.move(index, speed);
    }

    public void move(double speed)
    {
        super.move(0, speed);
    }

    public void move(Supplier<Double> speed)
    {
        super.move(0, speed);
    }

    
}
