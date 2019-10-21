package com.evergreen.everlib.betamimic;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * ParallelCommandGroup
 */
public class ParallelCommandGroup extends CommandGroup{

    public ParallelCommandGroup(Command... commands) {
        for (Command command : commands) {
            addParallel(command);
        }
    }
}