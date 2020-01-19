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


    public double getDistance() throws SensorDoesNotExistException {
        if (m_distanceSensor.isEmpty())
            return m_distanceSensor.getDistance();
        else
            throw new SensorDoesNotExistException(
                "Tried to get the distance of " + getName() 
                + ", but it does not have a distance sensor!");

    }

    @Override
    public List<LoggableData> getLoggableData() {
        List<LoggableData> loggables = new ArrayList<>();
        
        if (m_distanceSensor instanceof DistanceSensorGroup) {
            DistanceSensorGroup sensorGroup = (DistanceSensorGroup)m_distanceSensor;
            loggables.addAll(sensorGroup.getLoggableData());
        }

        loggables.add(new LoggableDouble(getName() + "/distance", () -> getDistance()));

        return loggables;
    }

}