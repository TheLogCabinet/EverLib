package com.evergreen.everlib.structure;

import com.evergreen.everlib.CommandEG;
import com.evergreen.everlib.shuffleboard.loggables.DashboardStreams;

import edu.wpi.first.wpilibj.TimedRobot;

/**
 * Tree
 */
public abstract class Tree extends TimedRobot {
    @Override
    public void robotInit() {
        constantOrganize();
        commandConfig();
        log();
    }

    @Override
    public void autonomousInit() {
        initState();

        if (getAutoCommand() != null) getAutoCommand().schedule();
    }

    @Override
    public void teleopInit() {
        teleopConfig();
    }

    @Override
    public void robotPeriodic() {
        DashboardStreams.update();
    }

    protected abstract void constantOrganize();
    protected abstract void commandConfig();
    protected abstract void log();

    protected abstract void initState();

    protected abstract void autoConfig();
    protected abstract void teleopConfig();



    protected CommandEG getAutoCommand() { return null; }
}