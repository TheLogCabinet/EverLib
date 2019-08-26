/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.evergreen7112.everlib.subsystems.motors;

import java.util.function.Consumer;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Victor;

/**
 * Add your docs here.
 */
public class Motor implements Consumer<Double> {
    private Consumer<Double> setter;
    private MotorType type;

    Motor(int port, MotorType type)
    {
        this.type = type;

        if(type == MotorType.VICTOR)
        {
            this.setter = new Victor(port)::set;
        }

        else if(type == MotorType.TALON)
        {
            this.setter = new Victor(port)::set;
        }
    }

    public enum MotorType
    {
        VICTOR,
        TALON;
    }

    public MotorType getType()
    {
        return type;
    }

    public void accept(Double speed)
    {
        setter.accept(speed);
    }

    public void accept(Supplier<Double> speedSupplier)
    {
        setter.accept(speedSupplier.get());
    }
}
