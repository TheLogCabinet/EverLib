/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.evergreen.everlib.oi.joysticks;


import com.wpilib2020.framework.Command;
import com.wpilib2020.framework.button.Button;


/**
 * This class consists of the whileHeld, whileReleased annd whenPressed methods, like the ones
 * in {@link Button}, except instead of belonging to a specific {@link Button} object, they are static 
 * and recieve the buttons as parameters. The advantage here, over using the {@link Button}'s methods,
 * is that these methods can take arrays of buttons (using the eclipsis syntax), which can greatly
 * simplify code that uses multiple buttons for the same command.
 */
public class GroupButtonBindings {

    /**
     * Makes input buttons to run the input command's {@link Command#start()} constantly, as long
     * as they are held. 
     * @param command - The command to be run while the buttons are held.
     * @param buttons - The buttons to wo hold to run the command.
     */
    public static void whileHeld(Command command, Button...buttons)
    {
        for (Button button : buttons) {
            button.whileHeld(command);
        }
    }
    
    /**
     * Make input buttons run the input command's {@link Command#start()} once, when they they are released.
     * @param command - The command to be run when the buttons are released.
     * @param buttons - The buttons to release to run the command
     */
    public static void whenReleased(Command command, Button... buttons)
    {
        for (Button button : buttons) {
            button.whenReleased(command);
        }
    }

    /**
     * Make input buttons run the input command's {@link Command#start()} once, when they they are first
     * pressed.
     * @param command - The command to be run when the buttons are pressed.
     * @param buttons - The buttons to press to run the comand
     */
    public static void whenPressed(Command command, Button... buttons)
    {
        for (Button button : buttons) {
            button.whenPressed(command);
        }
    }
}
