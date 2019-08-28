/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.evergreen7112.everlib.functionalinterfaces.limits;

import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;

import com.evergreen7112.everlib.subsystems.sensors.DistanceSesnsor;

/**
 * Add your docs here.
 */
public class MinLimit implements Limit{
    Supplier<Boolean> inRange;
    
    public MinLimit(double minDistance, DistanceSesnsor... sensors)
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

    public MinLimit(Map<DistanceSesnsor, Double> limitMap)
    {
        inRange = () -> 
        {
            Iterator<Map.Entry<DistanceSesnsor, Double>> iterator = limitMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<DistanceSesnsor, Double> pair = 
                    (Map.Entry<DistanceSesnsor, Double>)iterator.next();
                if(pair.getKey().getDistance() < pair.getValue())
                {
                    return false;
                }
            }

            return true;
        };
    }

    public MinLimit(double minDistance, Supplier<Double> speedSupplier)
    {
        inRange = () -> speedSupplier.get() < minDistance;
    }
    
    @Override
    public boolean inRange() {
        return inRange.get();
    }
}
