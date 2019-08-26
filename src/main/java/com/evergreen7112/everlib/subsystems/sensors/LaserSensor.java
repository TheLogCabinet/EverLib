/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.evergreen7112.everlib.subsystems.sensors;

/**
 * Add your docs here.
 */
public class LaserSensor extends DistanceSesnsor {
    
    public LaserSensor(int port, double m, double b)
    {
        super(port, (v) -> v*m+b);
    }

    public void setConverterByPoints(double voltage1, double distance1, double voltag2, double distance2)
    {
        double slope = (distance2 - distance1) / (voltag2 - voltage1);
        double intercept = distance2 - (voltag2 * slope); 
        this.voltageToDistance = (v) -> v * slope + intercept;
    }
}
