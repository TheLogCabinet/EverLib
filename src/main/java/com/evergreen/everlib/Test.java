package com.evergreen.everlib;

import com.evergreen.everlib.oi.joysticks.ExtremeProJoystick;
import com.evergreen.everlib.oi.joysticks.F310GamePad;
import com.evergreen.everlib.oi.joysticks.GroupButtonBindings;
import com.evergreen.everlib.oi.joysticks.ExtremeProJoystick.X;
import com.evergreen.everlib.oi.joysticks.ExtremeProJoystick.Y;
import com.evergreen.everlib.oi.joysticks.ExtremeProJoystick.Z;
import com.evergreen.everlib.oi.joysticks.F310GamePad.F310;
import com.evergreen.everlib.shuffleboard.constants.ConstantDouble;
import com.evergreen.everlib.shuffleboard.loggables.DashboardStreams;
import com.evergreen.everlib.structure.Tree;
import com.evergreen.everlib.subsystems.motors.subsystems.MotorController;
import com.evergreen.everlib.subsystems.motors.subsystems.MotorController.ControllerType;

import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj2.command.PrintCommand;

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

    ConstantDouble m_speedModifier = new ConstantDouble("Speed", 0.5);
    ConstantDouble m_change = new ConstantDouble("Right Adjust", 1.2);

    ExtremeProJoystick m_jsLeft = new ExtremeProJoystick("Joystick Left", 0, (v) -> v * 0.5 * 0.85);
    ExtremeProJoystick m_jsRight = new ExtremeProJoystick("Joystick Right", 1, (v) -> v * 0.5);
    F310GamePad m_jsButton = new F310GamePad("Joystick Button", 2);
  
    @Override
    protected void componentSetup() {
        m_jsLeft.setInverted();
        m_jsLeft.setExponential();

        m_jsRight.setAxisAdjuster(AxisType.kX, (v) -> 0.1 * v); //TODO check this works
        m_jsButton.kill(); //TODO check this returns 0.
    }

    @Override
    protected void bindButtons() {
        //TODO Make sure positions are correct, works.

        m_jsLeft.getButton(X.LEFT, Y.BACK, Z.BOTTOM).whenPressed(
            new PrintCommand("Left, Back, Bottom"));
        
        m_jsLeft.getButton(X.RIGHT, Y.BACK, Z.BOTTOM).whenPressed(
            new PrintCommand("Right, Back, Bottom"));
        
        m_jsLeft.getButton(X.LEFT, Y.MIDDLE, Z.BOTTOM).whenPressed(
            new PrintCommand("Left, Middle, Bottom"));
        
        m_jsLeft.getButton(X.RIGHT, Y.MIDDLE, Z.BOTTOM).whenPressed(
            new PrintCommand("Right, Middle, Bottom"));
        
        m_jsLeft.getButton(X.LEFT, Y.FORWARD, Z.BOTTOM).whenPressed(
            new PrintCommand("Left, Forward, Bottom"));
        
        m_jsLeft.getButton(X.RIGHT, Y.FORWARD, Z.BOTTOM).whenPressed(
            new PrintCommand("Right, Forward, Bottom"));
        
        m_jsLeft.getButton(X.LEFT, Y.BACK, Z.TOP).whenPressed(
            new PrintCommand("Left, Back, Top"));
        
        m_jsLeft.getButton(X.RIGHT, Y.BACK, Z.TOP).whenPressed(
            new PrintCommand("Right, Back, Top"));
        
        m_jsLeft.getButton(X.LEFT, Y.BACK, Z.TOP).whenPressed(
            new PrintCommand("Left, Back, Top"));
        
        m_jsLeft.getButton(X.RIGHT, Y.BACK, Z.TOP).whenPressed(
            new PrintCommand("Right, Back, Top"));
        
        m_jsLeft.getButton(X.LEFT, Y.MIDDLE, Z.TOP).whenPressed( //TODO make sure top middle throws an exception
            new PrintCommand("Left, Middle, Top"));
        
        m_jsLeft.getButton(X.RIGHT, Y.MIDDLE, Z.TOP).whenPressed(
            new PrintCommand("Right, Middle, Top"));
        
        m_jsLeft.getButton(X.LEFT, Y.FORWARD, Z.TOP).whenPressed(
            new PrintCommand("Left, Forward, Top"));
        
        m_jsLeft.getButton(X.RIGHT, Y.FORWARD, Z.TOP).whenPressed(
            new PrintCommand("Right, Forward, Top"));

        GroupButtonBindings.whenPressed(
            new PrintCommand("Trigger"), 
            m_jsLeft.trigger(),
            m_jsRight.trigger());
        
        GroupButtonBindings.whileHeld(
            new PrintCommand("Thumb").perpetually(), 
            m_jsLeft.thumb(),
            m_jsRight.thumb());


        for (F310 button : F310.values()) {
            m_jsButton.getButton(button).whenPressed(new PrintCommand(button.toString()));
        }

    }

    @Override
    protected void commandConfig() {

    }

    @Override
    protected void log() {
        //TODO make sure all streams work.
        DashboardStreams.addLoggable(m_jsButton);
        DashboardStreams.addLoggable(m_jsLeft);
        DashboardStreams.addLoggable(m_jsRight);
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
        //TODO try driving straight - if exponential works, than same pull won't do it.
        m_motorLeft.set(m_jsLeft.getY());
        m_motorRight.set(m_jsRight.getY());
    }

    
}