package com.evergreen.everlib.subsystems;

import java.util.ArrayList;
import java.util.List;

import com.evergreen.everlib.Exceptions;
import com.evergreen.everlib.shuffleboard.constants.ConstantBoolean;
import com.evergreen.everlib.shuffleboard.loggables.LoggableData;
import com.evergreen.everlib.shuffleboard.loggables.LoggableDouble;
import com.evergreen.everlib.shuffleboard.loggables.LoggableObject;
import com.evergreen.everlib.subsystems.sensors.DistanceSensor;
import com.evergreen.everlib.subsystems.sensors.DistanceSensorGroup;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


/**
 * The subsys
 */
public abstract class SubsystemEG extends SubsystemBase implements Exceptions, LoggableObject {

    protected ConstantBoolean m_subsystemSwitch;
    protected DistanceSensorGroup m_distanceSensor = new DistanceSensorGroup();
    protected double m_lastPosition;
    protected double m_lastCalcualtionTime;


    public SubsystemEG(String name) {
        setName(name);
        m_subsystemSwitch  = new ConstantBoolean(name);
        
    }

    public SubsystemEG(String name, Command defaultCommand) {
        this(name);
        setDefaultCommand(defaultCommand);
    }

    public SubsystemEG(String name, Command defaultCommand, DistanceSensor distanceSesnsor) {
        this(name, defaultCommand);
        addSensor(distanceSesnsor);
        m_distanceSensor.setSubsystem(this);
    }

    public ConstantBoolean getSwitch() {
        return m_subsystemSwitch;
    }

    public boolean getSwitchState() {
        return m_subsystemSwitch.get();
    }

    public void addSensor(DistanceSensor sensor) {
        m_distanceSensor.addSensor(sensor);
    }


    public double getPosition() throws SensorDoesNotExistException {
        if (!m_distanceSensor.isEmpty()) {
            m_lastPosition = m_distanceSensor.getDistance();
            m_lastCalcualtionTime = System.currentTimeMillis() / 1000;
            return m_lastPosition;
        }

        throw new SensorDoesNotExistException(
            "Tried to get the distance of " + getName() 
            + ", but it does not have a distance sensor!");
    }

    public synchronized double getVelocity() throws SensorDoesNotExistException {
        if (!m_distanceSensor.isEmpty()) {
            double lastPos = m_lastPosition;
            double lastPosTime = m_lastCalcualtionTime;
            double now = System.currentTimeMillis() / 1000;
            
            return (getPosition() - lastPos) / (now - lastPosTime);
        }

        throw new SensorDoesNotExistException(
            "Tried to get the velocity of " + getName() 
            + ", but it does not have a distance sensor!");
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