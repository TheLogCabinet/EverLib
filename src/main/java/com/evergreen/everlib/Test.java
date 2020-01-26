package com.evergreen.everlib;

import com.evergreen.everlib.oi.joysticks.F310GamePad;
import com.evergreen.everlib.oi.joysticks.F310GamePad.F310;
import com.evergreen.everlib.shuffleboard.constants.ConstantBoolean;
import com.evergreen.everlib.shuffleboard.constants.ConstantDouble;
import com.evergreen.everlib.shuffleboard.constants.ConstantInt;
import com.evergreen.everlib.shuffleboard.constants.ConstantString;
import com.evergreen.everlib.shuffleboard.constants.DashboardConstants;
import com.evergreen.everlib.shuffleboard.constants.commands.SetConstant;
import com.evergreen.everlib.shuffleboard.constants.commands.SetConstantUntil;
import com.evergreen.everlib.shuffleboard.constants.commands.SetSwitch;
import com.evergreen.everlib.shuffleboard.constants.commands.ToggleSwitch;
import com.evergreen.everlib.structure.Tree;
import com.evergreen.everlib.subsystems.motors.subsystems.MotorController;
import com.evergreen.everlib.subsystems.motors.subsystems.MotorController.ControllerType;
import com.evergreen.everlib.utils.InstantCommandEG;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Test
 */
public class Test extends Tree {
    
    
    MotorController m_chassis = new MotorController( //                                                                                                                                                                 `   ```             
        "Motor Right",
        new MotorController("Left Front", ControllerType.TALON_SRX, 9),
        new MotorController("Left Back", ControllerType.VICTOR_SPX, 0),
        new MotorController("Right Front", ControllerType.TALON_SRX, 1),
        new MotorController("Rught Back", ControllerType.VICTOR_SPX, 4),
        new MotorController("Left Up", ControllerType.VICTOR_SPX, 0),
        new MotorController("Right Up", ControllerType.VICTOR_SPX, 1));
    
    F310GamePad m_gamePad = new F310GamePad("Joystick Button", 0);

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
        m_gamePad.getButton(F310.X).whenPressed(new SetConstant(
            "Increase Speed", m_speedModifier, () -> m_speedModifier.get() + 0.1));

        m_gamePad.getButton(F310.B).whenPressed(new SetConstant(
            "Decrease Speed", m_speedModifier, () -> m_speedModifier.get() - 0.1));

        m_gamePad.getButton(F310.Y).whenPressed(new SetSwitch("Enable", true, m_canMove));
        m_gamePad.getButton(F310.A).whenPressed(new SetSwitch("Disable", false, m_canMove));

        m_gamePad.getButton(F310.JOYSTICK_LEFT).whenPressed(new ToggleSwitch("Toggle", m_canMove));

        m_gamePad.getButton(F310.RT).whileHeld(new SetConstantUntil(
            "Fast Drive", m_speedModifier, () -> 0.7));

        m_gamePad.getButton(F310.RT).whileHeld(new SetConstantUntil(
            "Slow Drive", m_speedModifier, () -> 0.2, () -> !m_canMove.get()));

        m_gamePad.getButton(F310.START).whenPressed(new InstantCommandEG(
            "Reset Shuffleboard", () -> DashboardConstants.getInstance().resetValues()));

        m_gamePad.getButton(F310.START).whenPressed(new InstantCommandEG(
            "Clean Shuffleboard", () -> DashboardConstants.getInstance().resetBoard()));
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