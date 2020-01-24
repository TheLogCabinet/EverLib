package com.evergreen.everlib;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.evergreen.everlib.oi.joysticks.ExtremeProJoystick;
import com.evergreen.everlib.shuffleboard.constants.ConstantBoolean;
import com.evergreen.everlib.shuffleboard.constants.ConstantDouble;
import com.evergreen.everlib.shuffleboard.constants.ConstantInt;
import com.evergreen.everlib.shuffleboard.constants.ConstantString;
import com.evergreen.everlib.shuffleboard.constants.DashboardConstants;
import com.evergreen.everlib.shuffleboard.loggables.DashboardStreams;
import com.evergreen.everlib.structure.Tree;
import com.evergreen.everlib.subsystems.motors.subsystems.MotorController;
import com.evergreen.everlib.subsystems.motors.subsystems.MotorController.ControllerType;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;

/**
 * Test
 */
public class Test extends Tree {
    
    
    MotorController m_chassis = new MotorController( //TODO Check ports!
        "Motor Right",
        new MotorController("Left Front", ControllerType.TALON_SRX, 9),
        new MotorController("Left Back", ControllerType.VICTOR_SPX, 0),
        new MotorController("Right Front", ControllerType.TALON_SRX, 1),
        new MotorController("Rught Back", ControllerType.VICTOR_SPX, 4),
        new MotorController("Left Up", ControllerType.VICTOR_SPX, 0),
        new MotorController("Right Up", ControllerType.VICTOR_SPX, 1));

    static {
        DashboardConstants.getInstance().startConstantsOf("subsystemName");
    }

    ConstantDouble m_speedModifier = new ConstantDouble("Speed Modifier", 0.5);
    
    static {
        DashboardConstants.getInstance().cd("/Thingies");
    }

    ConstantBoolean m_canMove = new ConstantBoolean("Can Move", true);

    static {
        DashboardConstants.getInstance().pushd("./complicated/.");
    }

    ConstantInt m_joystickPort = new ConstantInt("Joystick Port", 0);

    static {
        DashboardConstants.getInstance().cd("../simple");
    }

    ConstantString m_remarks = new ConstantString("Remarks", "testing.");

    static {
        DashboardConstants.getInstance().popd();
        new ConstantString("Path", "Where am I now?");
    }
  
    @Override
    protected void componentSetup() {
    }

    @Override
    protected void bindButtons() {
    }

    @Override
    protected void commandConfig() {

    }

    @Override
    protected void log() {
    }

    @Override
    protected void whenEnabled() {

    }

    @Override
    protected void autoConfig() {

    }

    @Override
    protected void teleopConfig() {

    }

    @Override
    protected void test() {
    }

    @Override
    public void testPeriodic() {
        
        if (m_canMove.get())
            m_chassis.set(new Joystick(m_joystickPort.get()).getY() * m_speedModifier.get());

        System.out.println(String.format(
            "%s. \nInteger %s\nDouble %s\nBoolean %s", 
            m_remarks.get(), m_joystickPort.get().toString(), m_speedModifier.get().toString(),
            String.valueOf(m_canMove.get())));
    }

    
}