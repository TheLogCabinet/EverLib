/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.evergreen7112.everlib.subsystems.motors;

import java.util.function.Consumer;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Victor;

/**
 * Add your docs here.
 */
public class Motor implements Consumer<Double> {
    private Consumer<Double> setter;
    private _MotorType type;

    Motor(int port, MotorType type)
    {

        if(type == MotorType.VICTOR)
        {
            this.type = _MotorType.VICTOR;
            this.setter = new Victor(port)::set;
        }

        else if(type == MotorType.TALON)
        {
            this.type = _MotorType.TALON;
            this.setter = new Victor(port)::set;
        }
    }
    
    Motor(SpeedController motor)
    {
        this.type = _MotorType.UNKNOWN;
        this.setter = motor::set;
    }

    enum _MotorType
    {
        VICTOR,
        TALON,
        UNKNOWN;

        public MotorType convert()
        {
            if(this == VICTOR)
            {
                return MotorType.VICTOR;
            }

            else if(this == TALON)
            {
                return MotorType.TALON;
            }

            else
            {
                return null;
            }
        }
    }

    public enum MotorType
    {
        VICTOR,
        TALON;
    }

    public boolean typeIs(MotorType type)
    {
        if(this.type == _MotorType.UNKNOWN) return true;
        return this.type.convert() == type;   
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
