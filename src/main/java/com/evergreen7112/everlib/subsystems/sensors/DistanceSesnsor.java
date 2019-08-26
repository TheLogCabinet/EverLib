/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.evergreen7112.everlib.subsystems.sensors;

import com.evergreen7112.everlib.functionalinterfaces.Adjuster;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * Add your docs here.
 */
public class DistanceSesnsor extends AnalogInput {
    Adjuster<Double> voltageToDistance;

    public DistanceSesnsor(int port, Adjuster<Double> converter)
    {
        super(port);
        voltageToDistance = converter;
    }

    public double getDistance()
    {
        return voltageToDistance.adjust(getVoltage());
    }

    public boolean inRange(double minDistance, double maxDistance)
    {
        return getDistance() > minDistance && getDistance() < maxDistance;
    }

    public boolean atLeast(double minDistance)
    {
        return getDistance() > minDistance;
    }

    public boolean atMost(double maxDistance)
    {
        return getDistance() < maxDistance;
    }
    
}
