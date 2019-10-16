/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.evergreen.everlib.shuffleboard.commands;

import com.evergreen.everlib.CommandEG;
import com.evergreen.everlib.shuffleboard.handlers.Switch;
import com.evergreen.everlib.shuffleboard.handlers.SwitchHandler;

/**A command that sets a {@link Switch shuffleboard switch} an input value. <p>
 * This command has a shuffleboard switch (set at constucion), which, if diabled,
 * will stop this command from running.
*/
public class SetSwitch extends CommandEG {

  private Switch[] switches;
  private boolean value;

  /**
   * Constructs a {@link CommandEG} which when ran, sets the input switches at input value
   * @param name - The name of this command and its shuffleboard switch.
   * @param value - The value to set the switches
   * @param switches - The switches to swt.
   */
  public SetSwitch(String name, boolean value, Switch... switches) {
    super(name);
    this.switches = switches;
  }

  /**Runs once when this command is called: if this command is enabled, 
   * sets the switches given at construction to the value given at construction.*/
  @Override
  protected void execute() 
  {
    if(getSwitch().get())
    {
      SwitchHandler.set(value, switches);
    }
  }

  /**Always returns true - this command should run only once.*/
  @Override
  protected boolean isFinished() {
    return true;
  }
}
