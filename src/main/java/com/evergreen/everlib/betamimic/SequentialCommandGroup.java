package com.evergreen.everlib.betamimic;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * SequentialCommandGroup
 */
public class SequentialCommandGroup extends CommandGroup {

    public SequentialCommandGroup(Command... commands) {
        for (Command command : commands) {
            addSequential(command);
        }
    }
}