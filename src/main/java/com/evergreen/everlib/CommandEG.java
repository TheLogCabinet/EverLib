package com.evergreen.everlib;

import java.util.List;

import com.evergreen.everlib.shuffleboard.handlers.DashboardStreams;
import com.evergreen.everlib.shuffleboard.handlers.Switch;
import com.evergreen.everlib.shuffleboard.handlers.SwitchHandler;
import com.evergreen.everlib.subsystems.SubsystemEG;
import com.evergreen.everlib.utils.loggables.LoggableData;
import com.evergreen.everlib.utils.loggables.LoggableInt;
import com.evergreen.everlib.utils.loggables.LoggableObject;
import com.wpilib2020.framework.CommandBase;
import com.wpilib2020.framework.Subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * The basic command for the Eververgreen Framework.
 * 
 * It contains a switch and will only start if it is on.
 * 
 * @author Atai Ambus
 */
public abstract class CommandEG extends CommandBase implements LoggableObject {
    private Switch m_commandSwitch;

    private int m_ranCounter = 0;
    
    /**
     * Constructs a new {@link CommandEG} with input name, and without logging it in the shuffleboard
     * @param name - The subsystem's name, corresponding to is {@link #getName()} method
     * as well as its {@link Switch Shuffleboard Switch}.
     */
    public CommandEG(String name) {
        setName(name);
        m_commandSwitch = SwitchHandler.addSwitch(name);
    }

    /**
     * Constructs a new {@link CommandEG} with input name, logging it on the shuffleboard if specified.
     * 
     * @param name - The subsystem's name, corresponding to is {@link #getName()} method
     * as well as its {@link Switch Shuffleboard Switch}.
     * 
     * @param log - wether to log the command on the shuffleboard or not.
     */
    public CommandEG(String name, boolean log) {
        setName(name);
        m_commandSwitch = SwitchHandler.addSwitch(name + " - Command Switch");
        if (log) DashboardStreams.addLoggable(this);
    }

    
    /**
     * Constructs a new {@link CommandEG} with input name.
     * 
     * @param name - The subsystem's name, corresponding to is {@link #getName()} method
     * as well as its {@link Switch Shuffleboard Switch}.
     * 
     * @param subsystems - Any subsystems the command requires.
     */
    public CommandEG(String name, SubsystemEG... subsystems) {
        this(name);
        
        for (SubsystemEG subsystem : subsystems) {
            addRequirements((Subsystem)subsystem);
        }

        m_commandSwitch = SwitchHandler.addSwitch(name);
    }

    /**
     * Constructs a new {@link CommandEG} with input name.
     * 
     * @param name - The subsystem's name, corresponding to is {@link #getName()} method
     * as well as its {@link Switch Shuffleboard Switch}.
     * 
     * @param subsystems - Any subsystems the command requires.
     * 
     * @param log - wether to log the command on the shuffleboard or not.
     * 
     */
    public CommandEG(String name, boolean log, SubsystemEG... subsystems) {
        this(name);
        
        for (SubsystemEG subsystem : subsystems) {
            addRequirements((Subsystem)subsystem);
        }

        m_commandSwitch = SwitchHandler.addSwitch(name);

        if (log) DashboardStreams.addLoggable(this);
    }

    /**Schedules this command, defaulting to interruptible, as long both this an*/
    @Override
    public void schedule() {
        m_ranCounter++;

        if (canStart())
            super.schedule();
    }


    /**
     * Disables the command and prevents it from starting.
     */
    public void disable() {
        SwitchHandler.disable(m_commandSwitch);
    }

    /**
     * Enables the command, allowing it to start.
     */
    public void enable() {
        SwitchHandler.enable(m_commandSwitch);
    }

    /**
     * Toggles the command - if it is enabled disable it, and vice versa.
     */
    public void toggle() {
        SwitchHandler.toggle(m_commandSwitch);
    }

    /** 
     * @return the command's switch.
     */
    public Switch getSwitch() {
        return m_commandSwitch;
    }

    @Override
    public boolean isFinished() {
        return !m_commandSwitch.get();
    }


    private boolean canStart() {
        
        for (Subsystem subsystem : getRequirements()) {
            if (subsystem instanceof SubsystemEG) {
                SubsystemEG sub = (SubsystemEG)subsystem;
                if (!sub.getSwitchState())
                    return false;
            }
        }

        return m_commandSwitch.get();
    }

    @Override
    public List<LoggableData> getLoggableData() {
        return List.of(new LoggableData[] {
            new LoggableInt(getName() + "Ran Counter", () -> m_ranCounter)
        });
    }

    @Override
    public void initSendable(SendableBuilder builder) { }
}