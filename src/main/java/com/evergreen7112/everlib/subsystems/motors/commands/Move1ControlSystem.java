package com.evergreen7112.everlib.subsystems.motors.commands;

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


import java.util.function.Supplier;

import com.evergreen7112.everlib.functionalinterfaces.limits.Limit;
import com.evergreen7112.everlib.subsystems.motors.subsystems.OneControllerSystem;

import edu.wpi.first.wpilibj.command.Command;

public class Move1ControlSystem extends Command {
  OneControllerSystem subsSystem;
  Supplier<Double> speedSupplier;
  Limit limit;
  public Move1ControlSystem(OneControllerSystem subsystem, Supplier<Double> speed, Limit speedRange ) {
    requires(subsystem);
    this.subsSystem = subsystem;
    this.speedSupplier = speed;
    this.limit = speedRange;
  }

  public Move1ControlSystem(OneControllerSystem subsystem, Supplier<Double> speed)
  {
    
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
      subsSystem.move(speedSupplier.get());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return !subsSystem.limit.inRange() || isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    subsSystem.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
