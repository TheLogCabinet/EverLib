/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.evergreen7112.everlib.subsystems.motors.commands;

import java.util.Map;
import java.util.function.Supplier;

import com.evergreen7112.everlib.functionalinterfaces.limits.Limit;
import com.evergreen7112.everlib.subsystems.motors.subsystems.MotorSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class MoveMotorSystem extends Command {
  MotorSubsystem subsystem;
  Map<Integer, Supplier<Double>> speedMap;
  Limit limit;
  
  public MoveMotorSystem(MotorSubsystem subsystem, Map<Integer, Supplier<Double>> speedMap) {
    requires(subsystem);
    
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
