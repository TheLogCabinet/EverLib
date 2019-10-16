package com.evergreen.everlib;

import java.util.HashSet;
import java.util.Set;

import com.evergreen.everlib.shuffleboard.handlers.Switch;
import com.evergreen.everlib.shuffleboard.handlers.SwitchHandler;
import com.evergreen.everlib.subsystems.SubsystemEG;

import edu.wpi.first.wpilibj.command.Command;

/**
 * The basic command for the Eververgreen Framework.
 * 
 * It contains a switch and will only start if it is on.
 * 
 * @author Atai Ambus
 */
public abstract class CommandEG extends Command {
    private Switch m_commandSwitch;
    private Set<SubsystemEG> m_requirments = new HashSet<>();

    /**
     * Constructs a new {@link CommandEG} with input name
     * @param name - The subsystem's name, corresponding to is {@link #getName()} method
     * as well as its {@link Switch Shuffleboard Switch}.
     */
    public CommandEG(String name) {
        super(name);
        m_commandSwitch = SwitchHandler.addSwitch(name);
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
        super(name);
        
        for (SubsystemEG subsystem : subsystems) {
            requires(subsystem);
        }

        m_commandSwitch = SwitchHandler.addSwitch(name);
    }

    @Override
    public synchronized void start() {
    
        if (canStart())
            super.start();
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
     * 
     * @return the subsystem's switch.
     */
    public Switch getSwitch() {
        return m_commandSwitch;
    }

    @Override
    protected boolean isFinished() {
        return !m_commandSwitch.get();
    }


    private boolean canStart() {
        
        for (SubsystemEG subsystem : m_requirments) {
            if (!subsystem.getSwitchState()) return false;
        }

        return m_commandSwitch.get();
    }

    protected synchronized void requires(SubsystemEG subsystem) {
        super.requires(subsystem);
        m_requirments.add(subsystem);
    }


}