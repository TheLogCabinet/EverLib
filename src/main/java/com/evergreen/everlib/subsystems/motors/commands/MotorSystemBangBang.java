package com.evergreen.everlib.subsystems.motors.commands;

import com.evergreen.everlib.CommandEG;
import com.evergreen.everlib.subsystems.motors.subsystems.MotorSubsystem;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.command.Command;


/**
 * A {@link Command} that moves the a motor subsystem in a straight line to a target by input supplier,
 *  using a <a href=https://whatis.techtarget.com/definition/bang-bang-control> bang-bang control 
 * algoritm</a>.
 */
public class MotorSystemBangBang extends CommandEG {

  /**The subsystem to be moved to target. */
  private MotorSubsystem m_subsystem;
  
  /**The speed modifier of the subsystem as it moves to target.*/
  private Supplier<Double> m_speed;
  
  /**Supplier of the target's ditance from the same point a the distance supplier mesures the 
   * subsystem's distance.*/ 
  public Supplier<Double> m_target;
  
  /**Whether or not the basic subssystem's distance from the was positive or negative when the command 
   * initilized.*/
  private boolean above;
  
  /**A counter fo debugging, to distinguish between variables of different objects of this command. */
  // private static int counter = 0;

  private boolean verbose;

  /**
  * The constructor for this class, which sets its speed and target. 
  * @param subsystem - The subsystem to be moved to target.
  * @param distanceSupplier - Supplier of the distance of the subsystem from a certain point.
  * @param speedModifier - The speed modifier of the subsystem as it moves to target.
  * @param target -Supplier of the target's ditance from the same point the distanceSupplier mesures from.
  * @param stallModifier - the supplier of the stall neccesiry to hold the subsystem in place of the target.
  * If you do not wish to hold th subsystem after it moves, set it as 0.
  * @param subsystemName - The name of the subsystem to be moved to target, to be used for this command's switch
  * @param targetName - The name of the target to move the subsystem to, to be used for this command's switch../
  */
  public MotorSystemBangBang(
    MotorSubsystem subsystem,
    Supplier<Double> speedModifier, 
    Supplier<Double> target,
    String subsystemName,
    String targetName) {

        super(String.format("{0} - Command Switches - Move to {1}", subsystemName, targetName));
        requires(subsystem);
        this.m_subsystem = subsystem;
        this.m_speed = speedModifier;
        this.m_target = target;
        verbose = false;
  }


  /**
  * The constructor for this class, which sets its speed and target. 
  * @param subsystem - The subsystem to be moved to target.
  * @param distanceSupplier - Supplier of the distance of the subsystem from a certain point.
  * @param speed - The speed modifier of the subsystem as it moves to target.
  * @param target -Supplier of the target's ditance from the same point the distanceSupplier mesures from.
  * @param subsystemName - The name of the subsystem to be moved to target, to be used for this command's switch
  * @param targetName - The name of the target to move the subsystem to, to be used for this command's switch../
  */
  public MotorSystemBangBang(
  MotorSubsystem subsystem,
  Supplier<Double> speed, 
  Supplier<Double> target, 
  String targetName,
  boolean verbose) {
  
  super(String.format("{0} - Command Switches - Move to {1}", subsystem.getName(), targetName));
  
  requires(subsystem);

  m_subsystem = subsystem;
  m_speed = speed;
  m_target = target;
}

  /**When the comand starts: determine if the subsystem is above or below the target, 
   * and set up the stall.*/
  @Override
  protected void initialize() {
    
    if (verbose) {
      System.out.println(m_subsystem.getName() + " move to target - starting!");
    }
    
    if(m_target.get() - m_subsystem.getDistance() > 0) //If the subsystem is below the target:
    {
      above = false; //Turn the apropriate boolean false.
    }

    else //If the subsystem is above the target or is on the target
    {
       above = true; //Turn the apropriate boolean true
    }
  }

  
  /**As the comand executes - move */
  @Override
  protected void execute() {

    String direction;
    
    if(above) //If the subsystem is positive related to the target:
    {
      direction = "Down";
      m_subsystem.move(-m_speed.get()); //Move the subsystem downwards
      direction = "Up";
      m_subsystem.move(m_speed.get()); //Move the subsystem forward with given speed.
    }

    else //If the subsystem is negative related to the target
    {
      direction = "Up";
      m_subsystem.move(m_speed.get()); //Move the subsystem forward with given speed.
    }

    if(verbose) //Print info if requested
    {
      System.out.println(
      m_subsystem.getName() + " to Target: \n" 
      + "Power: " + m_speed.get() + "\n"
      + "Direction: " + direction + "\n"
      + "Target: " + m_target.get() + "\n"
      + "Position: " + m_subsystem.getDistance() + "\n");
    }

  }

  /**Finishe the command once the subsystem has passed the target, or if the command's switch is turned off.*/
  @Override
  protected boolean isFinished() { 
    return //Return true:
      //IF it passed the target on the way down
      (above && m_subsystem.getDistance() <= m_target.get()) 
      //OR if it passed the target on the way up
      || (!above && m_subsystem.getDistance() >= m_target.get())
      //OR if the subsystem can't move anymore.
      || !m_subsystem.canMove()
      //OR if thge subsystem's switch is turned off.
      || super.isFinished();
  }

}
