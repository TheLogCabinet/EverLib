/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.evergreen.everlib.shuffleboard.commands;

import java.util.List;

import com.evergreen.everlib.CommandEG;
import com.evergreen.everlib.shuffleboard.handlers.Switch;
import com.evergreen.everlib.shuffleboard.handlers.SwitchHandler;
import com.evergreen.everlib.subsystems.SubsystemEG;
import com.evergreen.everlib.utils.loggables.LoggableData;
import com.evergreen.everlib.utils.loggables.LoggableString;

public class ToggleSwitch extends CommandEG {
  private Switch[] m_switches;

  public ToggleSwitch(String name, boolean log, Switch... switches) {
    super(name, log);
    this.m_switches = switches;
  }

  public ToggleSwitch(String name, Switch... switches) {
    this(name, false, switches);
  }


  public ToggleSwitch(String name, boolean log, CommandEG... commands) {
    super(name, log);
    
    m_switches = new Switch[commands.length];

    for (int i = 0; i < commands.length; i++) {
      m_switches[i] = commands[i].getSwitch();
    }
  }

  public ToggleSwitch(String name, CommandEG... commands) {
    this(name, false, commands);
  }

  public ToggleSwitch(String name, boolean log, SubsystemEG... subsystems) {
    super(name, log);
    
    m_switches = new Switch[subsystems.length];

    for (int i = 0; i < subsystems.length; i++) {
      m_switches[i] = subsystems[i].getSwitch();
    }
  }

  public ToggleSwitch(String name, SubsystemEG... subsystems) {
      this(name, false, subsystems);
  }


  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    SwitchHandler.toggle(m_switches);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return true;
  }

  @Override
  public List<LoggableData> getLoggableData() {
    List<LoggableData> loggables = super.getLoggableData();

    for (int i = 0; i < m_switches.length; i++) {
      new LoggableString(
        getName() + "switch to toggle" + (m_switches.length > 1 ? "#" + i : ""), //Number key if there is more than one
        m_switches[i]::getKey);
    }

    return loggables;
  }
}
