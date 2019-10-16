package com.evergreen.everlib.subsystems;

import com.evergreen.everlib.shuffleboard.handlers.Switch;
import com.evergreen.everlib.shuffleboard.handlers.SwitchHandler;
import com.evergreen.everlib.subsystems.sensors.DistanceSensor;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The subsys
 */
public abstract class SubsystemEG extends Subsystem {

    protected Switch m_subsystemSwitch;
    private Command m_defaultCommand;
    private DistanceSensor m_distanceSensor;

    public SubsystemEG(String name) {
        super(name);
        m_subsystemSwitch  = SwitchHandler.addSwitch(name);
    }

    public SubsystemEG(String name, Command defaultCommand) {
        
        this(name);
        m_defaultCommand = defaultCommand;
    }

    public SubsystemEG(String name, Command defaultCommand, DistanceSensor distanceSesnsor) {
        this(name, defaultCommand);
        m_distanceSensor = distanceSesnsor;
    }

    public Switch getSwitch() {
        return m_subsystemSwitch;
    }

    public boolean getSwitchState() {
        return m_subsystemSwitch.get();
    }

    @Override
    protected void initDefaultCommand() {
        if (m_defaultCommand != null) {
            setDefaultCommand(m_defaultCommand);
        }
    }

    public double getDistance() {
        return m_distanceSensor.getDistance();
    }
}