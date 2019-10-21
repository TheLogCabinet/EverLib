package com.evergreen.everlib.subsystems.pistons.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import com.evergreen.everlib.CommandEG;
import com.evergreen.everlib.subsystems.pistons.subsystems.PistonSubsystem;

public class SetPistonSubsystem extends CommandEG {
  /**Supplier for the state to be set.
   * When this command executes, the */
  private Value m_state;
  private PistonSubsystem m_pistons;
  
  public SetPistonSubsystem(String name, PistonSubsystem pistons, Value state) {
    super(name, pistons);
    m_state = state;
    m_pistons = pistons;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
      m_pistons.set(m_state);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }
}