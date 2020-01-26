package com.evergreen.everlib;

import com.evergreen.everlib.oi.joysticks.ExtremeProJoystick;
import com.evergreen.everlib.oi.joysticks.F310GamePad;
import com.evergreen.everlib.shuffleboard.loggables.DashboardStreams;
import com.evergreen.everlib.structure.Tree;
import com.evergreen.everlib.subsystems.motors.subsystems.MotorController;
import com.evergreen.everlib.subsystems.motors.subsystems.MotorController.ControllerType;
import com.evergreen.everlib.utils.InstantCommandEG;

/**
 * Test
 */
public class Test extends Tree {


    MotorController m_motorRight = new MotorController(
        "Motor Right",
        new MotorController("Right Talons", ControllerType.TALON_SRX, 0),
        new MotorController("Right Victors", ControllerType.VICTOR_SPX, 1, 2));

    MotorController m_motorLeft = new MotorController(
        "Motor Left",
        new MotorController("Left Talons", ControllerType.TALON_SRX, 3),
        new MotorController("Right Victors", ControllerType.VICTOR_SPX, 4, 5));

    ExtremeProJoystick m_jsLeft = new ExtremeProJoystick("Joystick Left", 0, (v) -> v * 0.5);
    ExtremeProJoystick m_jsRight = new ExtremeProJoystick("Joystick Right", 1, (v) -> v * 0.5);
    F310GamePad m_jsButton = new F310GamePad("Joystick Button", 2);

    CommandEG m_testCommand = new InstantCommandEG("Test Command", () -> System.out.println("Test"));

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
        DashboardStreams.addLoggable(m_testCommand);
    }

    @Override
    protected void whenEnabled() {
       m_testCommand.schedule();

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
        if (m_jsLeft.getRawButtonPressed(1)) m_testCommand.enable();
        if (m_jsRight.getRawButtonPressed(1)) m_testCommand.disable();

        if (m_jsLeft.getRawButtonPressed(2)) m_testCommand.toggle();
    }

    
}