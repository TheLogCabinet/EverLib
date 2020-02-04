package com.evergreen.everlib;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.evergreen.everlib.oi.joysticks.F310GamePad;
import com.evergreen.everlib.shuffleboard.constants.ConstantBoolean;
import com.evergreen.everlib.shuffleboard.constants.ConstantDouble;
import com.evergreen.everlib.shuffleboard.constants.ConstantInt;
import com.evergreen.everlib.shuffleboard.constants.ConstantString;
import com.evergreen.everlib.shuffleboard.constants.DashboardConstants;
import com.evergreen.everlib.shuffleboard.constants.commands.SetConstant;
import com.evergreen.everlib.shuffleboard.constants.commands.SetConstantUntil;
import com.evergreen.everlib.shuffleboard.constants.commands.SetSwitch;
import com.evergreen.everlib.shuffleboard.constants.commands.ToggleSwitch;
import com.evergreen.everlib.shuffleboard.loggables.DashboardStreams;
import com.evergreen.everlib.structure.Tree;
import com.evergreen.everlib.subsystems.motors.subsystems.MotorController;
import com.evergreen.everlib.subsystems.motors.subsystems.MotorController.ControllerType;
import com.evergreen.everlib.utils.InstantCommandEG;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;

/**
 * Test
 */
public class Test extends Tree {
    
    
    static MotorController m_chassis = new MotorController( //                                                                                                                                                                 `   ```             
        "Motor Right",
        new MotorController("Left Front", ControllerType.TALON_SRX, 9),
        new MotorController("Left Back", ControllerType.VICTOR_SPX, 0),
        new MotorController("Right Front", ControllerType.TALON_SRX, 1),
        new MotorController("Rught Back", ControllerType.VICTOR_SPX, 4),
        new MotorController("Left Up", ControllerType.VICTOR_SPX, 0),
        new MotorController("Right Up", ControllerType.VICTOR_SPX, 1));
    
    F310GamePad m_gamePad = new F310GamePad("Joystick Button", 0);

    Joystick m_js = new Joystick(0);

    static {
        DashboardConstants.getInstance().startConstantsOf("subsytemStuff");
    }
    static ConstantDouble m_speedModifier = new ConstantDouble("Velocity Modifier", 0.5);
    
    
    static {
        DashboardConstants.getInstance().cd("/Stuff");
    }
    static ConstantBoolean m_canMove = new ConstantBoolean("Moveable", true);
    
    
    static {
        DashboardConstants.getInstance().pushd("./Checks/.");
    }
    static ConstantInt m_joystickPort = new ConstantInt("Joystick Port", 0);
    
    
    static {
        DashboardConstants.getInstance().cd("../Calcs");
    }
    static ConstantString m_remarks = new ConstantString("Remarks", "testing.");
    
    
    static {
        DashboardConstants.getInstance().popd();
        new ConstantString("Path", "Where am I now?");
    }
  
    @Override
    protected void componentSetup() {
        new WPI_VictorSPX(7).set(0.5);
        }

    @Override
    protected void update() {
        Preferences.getInstance().removeAll();
        if (m_js.getRawButtonPressed(1))
            new SetConstant(
                "Increase Speed", m_speedModifier, () -> m_speedModifier.get() + 0.1).schedule();

        if (m_js.getRawButtonPressed(3))
            new SetConstant(
            "Decrease Speed", m_speedModifier, () -> m_speedModifier.get() - 0.1).schedule();

        if (m_js.getRawButtonPressed(4))
            new SetSwitch("Enable", true, m_canMove).schedule();

        if (m_js.getRawButtonPressed(2))
            new SetSwitch("Disable", false, m_canMove).schedule();

        if (m_js.getRawButtonPressed(11))
            new ToggleSwitch("Toggle", m_canMove).schedule();
        
        if (m_js.getRawButtonPressed(11))
            new SetConstantUntil("Fast Drive", m_speedModifier, () -> 0.7).schedule();

        if (m_js.getRawButtonPressed(9))
            new SetConstantUntil("Slow Drive", m_speedModifier, () -> 0.2, () -> !m_canMove.get()).schedule();

        if (m_js.getRawButtonPressed(10))
            new InstantCommandEG("Reset Shuffleboard",
             () -> DashboardConstants.getInstance().resetValues()).schedule();
        
        if (m_js.getRawButtonPressed(10))
            new InstantCommandEG("Clean Shuffleboard", 
            () -> DashboardConstants.getInstance().resetBoard()).schedule();


            
        }
            
            
    @Override
    protected void commandConfig() {
    }

    @Override
    protected void log() {
        DashboardStreams.getInstance().addLoggable(DashboardConstants.getInstance());
        DashboardStreams.getInstance().addDouble("DOUBLE", m_speedModifier);
        DashboardStreams.getInstance().addBoolean("BOOLEAN", m_canMove);
        DashboardStreams.getInstance().addInt("INT", m_joystickPort);
        DashboardStreams.getInstance().addString("STRING", m_remarks);
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
        
        // if (m_canMove.get())
        //     m_chassis.set(new Joystick(m_joystickPort.get()).getY() * m_speedModifier.get());
    }

    @Override
    protected void bindButtons() {


    }

    
}