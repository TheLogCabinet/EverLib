/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.evergreen7112.everlib.subsystems.motors;

import java.util.HashMap;

import com.evergreen7112.everlib.subsystems.motors.Motor.MotorType;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;

/**
 * Add your docs here.
 */
public class OneMotorSystem extends MotorSubsystem {
    
    OneMotorSystem(int port, MotorType motorType)
    {
        super(motorType, port);
    }

    OneMotorSystem(Motor motor)
    {
        super(motor);
    }

    OneMotorSystem(Talon motor)
    {
        super(new Motor(motor.getChannel(), MotorType.TALON));
    }

    OneMotorSystem(Victor motor)
    {
        super(new Motor(motor.getChannel(), MotorType.VICTOR));
    }

    @Override
    @Deprecated
    public void move(HashMap<Integer, Double> speedMap) {
        super.move(speedMap);
    }
}
