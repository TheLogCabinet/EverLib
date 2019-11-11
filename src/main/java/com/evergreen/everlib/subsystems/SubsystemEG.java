package com.evergreen.everlib.subsystems;

import java.util.ArrayList;
import java.util.List;

import com.evergreen.everlib.Exceptions;
import com.evergreen.everlib.shuffleboard.handlers.Switch;
import com.evergreen.everlib.shuffleboard.handlers.SwitchHandler;
import com.evergreen.everlib.subsystems.sensors.DistanceSensor;
import com.evergreen.everlib.subsystems.sensors.DistanceSensorGroup;
import com.evergreen.everlib.utils.loggables.LoggableData;
import com.evergreen.everlib.utils.loggables.LoggableDouble;
import com.evergreen.everlib.utils.loggables.LoggableObject;
import com.wpilib2020.framework.Command;
import com.wpilib2020.framework.SubsystemBase;


/**
 * The subsys
 */
public abstract class SubsystemEG extends SubsystemBase implements Exceptions, LoggableObject {

    protected Switch m_subsystemSwitch;
    private DistanceSensor m_distanceSensor;

    public SubsystemEG(String name) {
        setName(name);
        m_subsystemSwitch  = SwitchHandler.addSwitch(name);
    }

    public SubsystemEG(String name, Command defaultCommand) {
        this(name);
        setDefaultCommand(defaultCommand);
    }

    public SubsystemEG(String name, Command defaultCommand, DistanceSensor distanceSesnsor) {
        this(name, defaultCommand);
        m_distanceSensor = distanceSesnsor;
        m_distanceSensor.setSubsystem(this);
    }

    public Switch getSwitch() {
        return m_subsystemSwitch;
    }

    public boolean getSwitchState() {
        return m_subsystemSwitch.get();
    }


    public double getDistance() throws SensorDoesNotExistException {
        try {
            return m_distanceSensor.getDistance();
        } 

        catch (NullPointerException e) {
            throw new SensorDoesNotExistException(getName() + " does not have a distance sensor!");
        }
    }

    @Override
    public List<LoggableData> getLoggableData() {
        List<LoggableData> loggables = new ArrayList<>();
        
        if (m_distanceSensor instanceof DistanceSensorGroup) {
            DistanceSensorGroup sensorGroup = (DistanceSensorGroup)m_distanceSensor;
            loggables.addAll(sensorGroup.getLoggableData());
        }

        loggables.add(new LoggableDouble(getName() + " - distance", () -> getDistance()));

        return loggables;
    }

}