package com.evergreen.everlib;

import java.util.List;

import com.evergreen.everlib.structure.Tree;
import com.evergreen.everlib.utils.InstantCommandEG;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Test
 */
public class Test extends Tree {

    double m_testVal = 0;

    @Override
    protected void componentSetup() {
        System.out.println("Seting Up Components... "); //RobotInit
    }

    @Override
    protected void bindButtons() {
       System.err.println("Bindind Buttons..."); //Robot Init
    }

    @Override
    protected void commandConfig() {
        System.out.println("Configuring Commands"); //Robot Init
    }

    @Override
    protected void log() {
        System.out.println("Adding Loggable Streams"); //Robot Init
    }

    @Override
    protected void whenEnabled() { //TestInit, AutonomusInit
        System.out.println("Running Enabled State");
    }

    @Override
    protected void autoConfig() { //Autonomus Init       
        System.out.println("Configuring Autonomus Commands");
    }

    @Override
    protected void teleopConfig() { //Teleop Init
       System.out.println("Configuring Teleop Commands");
    }

    @Override
    protected void test() { //TestInit
       System.out.println("Testing...");
    }

    @Override
    protected void update() { //RobotPeriodic
        SmartDashboard.putNumber("Update Test", ++m_testVal);
    }

    @Override
    protected List<CommandEG> getAutoCommands() { //Runs in AutoInit
        return List.of(
            new InstantCommandEG("Auto Printer #0", () -> System.out.println("AUTO COMMAND #0")),
            new InstantCommandEG("Auto Printer #1", () -> System.out.println("AUTO COMMAND #1"))
        );
    }

    @Override
    protected List<CommandEG> getTeleopCommands() { //Runs in AutoInit
        return List.of(
            new InstantCommandEG("Teleop Printer #0", () -> System.out.println("TELEOP COMMAND #0")),
            new InstantCommandEG("Teleop Printer #1", () -> System.out.println("TELEOP COMMAND #1"))
        );
    }

    
}