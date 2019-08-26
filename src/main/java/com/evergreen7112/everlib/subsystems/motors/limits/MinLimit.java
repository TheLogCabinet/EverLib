/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.evergreen7112.everlib.subsystems.motors.limits;

import java.util.function.Supplier;

import com.evergreen7112.everlib.subsystems.sensors.DistanceSesnsor;

/**
 * Add your docs here.
 */
public class MinLimit implements Limit{
    Supplier<Boolean> inRange;
    
    MinLimit(double minDistance, DistanceSesnsor... sensors)
    {
        inRange = () -> {
            for (DistanceSesnsor sensor : sensors) {
                if(!sensor.atLeast(minDistance))
                {
                    return false;
                }
            }

            return true;   
        };
    }

    public MinLimit(double minDistance, DistanceSesnsor sensor)
    {
        inRange = () -> sensor.atLeast(minDistance);
    }

    @Override
    public boolean inRange() {
        return inRange.get();
    }
}
