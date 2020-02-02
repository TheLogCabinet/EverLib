package com.evergreen.everlib.subsystems;

import java.util.ArrayList;
import java.util.List;

import com.evergreen.everlib.Exceptions;
import com.evergreen.everlib.shuffleboard.constants.ConstantBoolean;
import com.evergreen.everlib.shuffleboard.loggables.LoggableData;
import com.evergreen.everlib.shuffleboard.loggables.LoggableDouble;
import com.evergreen.everlib.shuffleboard.loggables.LoggableObject;
import com.evergreen.everlib.subsystems.sensors.EvergreenGyro;
import com.evergreen.everlib.subsystems.sensors.DistanceSensor;
import com.evergreen.everlib.subsystems.sensors.DistanceSensorGroup;

import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


/**
 * The subsys
 */
public abstract class EvergreenSubsystem extends SubsystemBase implements Exceptions, LoggableObject {

    protected ConstantBoolean m_subsystemSwitch;

    protected DistanceSensorGroup m_distanceSensor = 
        new DistanceSensorGroup(getName() + "/Distance Sensor");

    protected EvergreenGyro m_angleSensors = 
        new EvergreenGyro(getName() + "/Angle Sensor");
        
    protected EvergreenCommand m_currentlyUsedBy;


    public EvergreenSubsystem(String name) {
        setName(name);
        m_subsystemSwitch  = new ConstantBoolean(name);
        
    }

    public ConstantBoolean getSwitch() {
        return m_subsystemSwitch;
    }

    public boolean getSwitchState() {
        return m_subsystemSwitch.get();
    }

    public void addDistanceSensor(DistanceSensor sensor) {
        m_distanceSensor.addSensor(sensor);
    }

    public void addAngleSensor(Gyro angleSensor) {
        m_angleSensors.addSensor(angleSensor);
    }

    public double getAngle() {
        if (!m_angleSensors.isEmpty()) {
            return m_angleSensors.getAngle();
        }

        throw new SensorDoesNotExistException(
            "Tried to get the angle of " + getName() 
            + ", but it does not have a angle sensor!");
    }


    public double getPosition() throws SensorDoesNotExistException {
        if (!m_distanceSensor.isEmpty()) {
            return m_distanceSensor.getPosition();
        }

        throw new SensorDoesNotExistException(
            "Tried to get the distance of " + getName() 
            + ", but it does not have a distance sensor!");
    }

    void useWith(EvergreenCommand command) {
        m_currentlyUsedBy = command;
    }

    public double getVelocity() throws SensorDoesNotExistException {
        if (!m_distanceSensor.isEmpty()) {
            return m_distanceSensor.getVelocity();
        }

        throw new SensorDoesNotExistException(
            "Tried to get the velocity of " + getName() 
            + ", but it does not have a distance sensor!");
    }

    public double getAngularVelocity() throws SensorDoesNotExistException {
        if (!m_angleSensors.isEmpty()) {
            return m_angleSensors.getRate();
        }

        throw new SensorDoesNotExistException(
            "Tried to get the angular velocity of " + getName()
            + ", but it does not have any angke sensors!"
        );
    }



    @Override
    public List<LoggableData> getLoggableData() {
        List<LoggableData> loggables = new ArrayList<>();
        
        if (m_distanceSensor instanceof DistanceSensorGroup) {
            DistanceSensorGroup sensorGroup = (DistanceSensorGroup)m_distanceSensor;
            loggables.addAll(sensorGroup.getLoggableData());
        }

        loggables.add(new LoggableDouble(getName() + "/Position", this::getPosition));
        loggables.add(new LoggableDouble(getName() + "/Velocity", this::getVelocity));

        return loggables;
    }
}