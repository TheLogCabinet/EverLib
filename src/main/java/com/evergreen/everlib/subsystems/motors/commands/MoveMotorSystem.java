/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.evergreen.everlib.subsystems.motors.commands;

import java.util.Map;
import java.util.function.Supplier;

import com.evergreen.everlib.CommandEG;
import com.evergreen.everlib.utils.ranges.Range;
import com.evergreen.everlib.subsystems.motors.subsystems.MotorSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**A {@link Command} which moves a {@link MotorSubsystem} according to a given speed map.*/
public class MoveMotorSystem extends CommandEG {
  /**The command's subsystem to be moved. */
  MotorSubsystem subsystem;
  /**The {@link Map} matching the index of the */
  Map<Integer, Supplier<Double>> speedMap;
  Range limit;
  
  /**
   * Constructs a {@link MoveMotorSystem MoveMotorSystem Command} according to input parameteres   
   * @param name - The command's name - will correspond to its getName() method, and be the key
   * to its shuffleboard switch.
   * @param subsystem - The subsystem to move.
   * @param speedMap - A {@link Map} which links controllers (by index) to speed suppliers.
   */
  public MoveMotorSystem(String name, MotorSubsystem subsystem, Map<Integer, Supplier<Double>> speedMap) {
    super(name, subsystem);
    this.subsystem = subsystem;
    this.speedMap = speedMap;
    this.limit = (v) -> true;
  }

  /**
   * Constructs a {@link MoveMotorSystem MoveMotorSystem Command} according to input parameteres   
   * @param name - The command's name - will correspond to its getName() method, and be the key
   * to its shuffleboard switch.
   * @param subsystem - The subsystem to move.
   * @param speedMap - A {@link Map} which links controllers (by index) to speed suppliers.
   * @param limit - a {@link Range} which supplies the range in which to allow the subsystem to move.
   */
  public MoveMotorSystem(String name, MotorSubsystem subsystem, Range limit, Map<Integer, Supplier<Double>> speedMap) {
    super(name, subsystem);
    this.subsystem = subsystem;
    this.speedMap = speedMap;
    this.limit = limit;
  }

/**As the command starts: stop the subsystem, making sure motors 
   * that are not explicitly moved by the command won't move.  */
  @Override
  protected void initialize() {
    subsystem.stop();
  }

  /**Repeatedly as it runs: set the subsystem's motors according to the suppliers */
  @Override
  protected void execute() {
    speedMap.forEach( (index, speed) -> subsystem.set(index, speed) );
  }

  /**Finish if the subsystem goes out of its permitted range, or if the command times out. */
  @Override
  protected boolean isFinished() {
    return !limit.inRange(subsystem.getDistance()) || !subsystem.canMove() || isTimedOut();
  }

  /**As the command ends, stop the subsystem */
  @Override
  protected void end() {
    subsystem.stop();
  }

  /**As the command ends, stop the subsystem */
  @Override
  protected void interrupted() {
    end();
  }
}
