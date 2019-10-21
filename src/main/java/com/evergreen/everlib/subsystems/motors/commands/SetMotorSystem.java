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
import com.evergreen.everlib.subsystems.motors.subsystems.MotorSubsystem;
import com.evergreen.everlib.utils.ranges.Range;

import edu.wpi.first.wpilibj.command.Command;

/**A {@link Command} which moves a {@link MotorSubsystem} according to a given speed map.
 * 
 * @author Atai Ambus
*/
public class SetMotorSystem extends CommandEG {

  /**The command's subsystem to be moved. */
  protected MotorSubsystem m_subsystem;

  /**The {@link Map} matching the index of the */
  protected Map<Integer, Supplier<Double>> m_speedMap;

  protected Supplier<Double> m_speedModifier;
  
  protected Range m_limit;

  protected static double s_defaultModifier = 0.5;

  /**
   * Constructs a {@link SetMotorSystem MoveMotorSystem Command} according to input parameteres   
   * @param name - The command's name - will correspond to its getName() method, and be the key
   * to its shuffleboard switch.
   * @param subsystem - The subsystem to move.
   * @param speedMap - A {@link Map} which links controllers (by index) to speed suppliers.
   */
  public SetMotorSystem(String name, MotorSubsystem subsystem, Map<Integer, Supplier<Double>> speedMap) {
    super(name, subsystem);
    
    m_subsystem = subsystem;
    m_speedMap = speedMap;
    
    m_limit = (v) -> true;
    m_speedModifier = () -> s_defaultModifier;
  }

  /**
   * Constructs a {@link SetMotorSystem MoveMotorSystem Command} according to input parameteres   
   * @param name - The command's name - will correspond to its getName() method, and be the key
   * to its shuffleboard switch.
   * @param subsystem - The subsystem to move.
   * @param speedMap - A {@link Map} which links controllers (by index) to speed suppliers.
   */
  public SetMotorSystem(String name, MotorSubsystem subsystem, Supplier<Double> speedModifier,
   Map<Integer, Supplier<Double>> speedMap) {
    super(name, subsystem);
    
    m_subsystem = subsystem;
    m_speedModifier = speedModifier;
    m_speedMap = speedMap;

    m_limit = (v) -> true;
  }

  /**
   * Constructs a {@link SetMotorSystem MoveMotorSystem Command} according to input parameteres   
   * @param name - The command's name - will correspond to its getName() method, and be the key
   * to its shuffleboard switch.
   * @param subsystem - The to move.
   * @param speedMap - A {@link Map} which links controllers (by index) to speed suppliers.
   * @param limit - a {@link Range} which supplies the range in which to allow the subsystem to move.
   */
  public SetMotorSystem(String name, MotorSubsystem subsystem, Range limit, Map<Integer, Supplier<Double>> speedMap) {
    super(name, subsystem);
    
    m_subsystem = subsystem;
    m_speedMap = speedMap;
    m_limit = limit;

    m_speedModifier = () -> s_defaultModifier;
  }

  /**
   * Constructs a {@link SetMotorSystem MoveMotorSystem Command} according to input parameteres   
   * @param name - The command's name - will correspond to its getName() method, and be the key
   * to its shuffleboard switch.
   * @param subsystem - The subsystem to move.
   * @param speedMap - A {@link Map} which links controllers (by index) to speed suppliers.
   * @param limit - a {@link Range} which supplies the range in which to allow the subsystem to move.
   */
  public SetMotorSystem(String name, MotorSubsystem subsystem, Range limit, Supplier<Double> speedModifier,
    Map<Integer, Supplier<Double>> speedMap) {
    super(name, subsystem);
    
    m_subsystem = subsystem;
    m_speedMap = speedMap;
    m_speedModifier = speedModifier;
    m_limit = limit;
  }
  
/**As the command starts: stop the subsystem, making sure motors 
   * that are not explicitly moved by the command won't move.  */
  @Override
  protected void initialize() {
    m_subsystem.stop();
  }

  /**Repeatedly as it runs: set the subsystem's motors according to the suppliers */
  @Override
  protected void execute() {
    m_speedMap.forEach( (index, speed) -> m_subsystem.set(index, speed.get() * m_speedModifier.get()) );
  }

  /**Finish if the subsystem goes out of its permitted range, or if the command times out. */
  @Override
  protected boolean isFinished() {
    return !m_limit.inRange(m_subsystem.getDistance()) || !m_subsystem.canMove() || isTimedOut();
  }

  
  /**As the command ends, stop the subsystem */
  @Override
  protected void end() {
    m_subsystem.stop();
  }

  /**As the command ends, stop the subsystem */
  @Override
  protected void interrupted() {
    end();
  }


  public static void setDefaultModifier(double modifier) {
      s_defaultModifier = modifier;
    }
}
