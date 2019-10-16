package com.evergreen.everlib.subsystems.sensors;

import java.util.List;

/**
 * SensorGroup
 */
public class DistanceSensorGroup extends DistanceSensor {
    List<DistanceSensor> m_sesnsors;
    int sum;

    public DistanceSensorGroup(DistanceSensor... sensors) {
        m_sesnsors = List.of(sensors);
    }
    
    @Override
    public double _getDistance() {
        int sum = 0;
        
        for (DistanceSensor sensor : m_sesnsors) {
            
            if (sensor.m_killSwitch) {
                m_sesnsors.remove(sensor);
                continue;
            }

            sum += sensor.getDistance();
        }

        return sum / m_sesnsors.size();
    }
}