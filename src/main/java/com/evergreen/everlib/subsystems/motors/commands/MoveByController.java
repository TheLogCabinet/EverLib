package com.evergreen.everlib.subsystems.motors.commands;

import java.util.Map;

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


import java.util.function.Supplier;

import com.evergreen.everlib.utils.ranges.Range;
import com.evergreen.everlib.shuffleboard.handlers.Switch;
import com.evergreen.everlib.subsystems.motors.subsystems.MotorSubsystem;
import com.evergreen.everlib.subsystems.motors.subsystems.OneMotorSystem;

/**
 * Moves a {@link OneMotorSystem} according to iput speed and range.
 */
public class MoveByController extends MoveMotorSystem {

  /**
   * Constructs a {@link MoveByController Move1ControlSystem Command} according to input parameters.
   * 
   * @param name - The name of the command, corresponding to its {@link #getName()} method and to its
   * {@link Switch shuffleboard switch}
   * @param subsystem - The subsystem to move.
   * @param speed - The speed supplier ywhich will supply the speed to move the subsystem.
   * @param speedRange - The range in which to move the subsystem.
   */
  public MoveByController(String name, OneMotorSystem subsystem, Supplier<Double> speed, Range speedRange ) {
    super(name, subsystem, speedRange,  getMap(speed));
  }

  /**
  * Constructs a {@link MoveByController Move1ControlSystem Command} according to input parameters.
  * 
  * @param name - The name of the command, corresponding to its {@link #getName()} method and to its
  * {@link Switch shuffleboard switch}
  * @param subsystem - The subsystem to move.
  * @param speed - The speed supplier ywhich will supply the speed to move the subsystem.
  */
  public MoveByController(String name, OneMotorSystem subsystem, Supplier<Double> speed)
  {
    super(name, (MotorSubsystem)subsystem, getMap(speed));
  }

  private static Map<Integer, Supplier<Double>> getMap(Supplier<Double> sup)
  {
    return Map.of(0, sup);
  }
}
