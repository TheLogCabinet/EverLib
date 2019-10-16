/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.evergreen.everlib.oi.joysticks;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import com.evergreen.everlib.utils.Adjuster;


/**
 * A class for <a href="https://www.logitechg.com/en-roeu/products/gamepads/f310-gamepad.html">
 * Logitech's F310 Gamepad</a>, with all of its buttons as members, so there is no need to 
 * initilize or keep track of individual buttons. <p><p>
 * Noth that this class inherits {@link JoystickEG}, so it can be constructed with an
 * {@link Adjuster} which will automatically adjust its getRawAxis values.
 */
public class F310GamePad extends JoystickEG {
    
    
    public final Button X = new JoystickButton(this, 1);
    public final Button A = new JoystickButton(this, 2);
    public final Button B = new JoystickButton(this, 3);
    public final Button Y = new JoystickButton(this, 4);
    public final Button LB = new JoystickButton(this, 5);
    public final Button RB = new JoystickButton(this, 6);
    public final Button LT = new JoystickButton(this, 7);
    public final Button RT = new JoystickButton(this, 8);
    public final Button BACK = new JoystickButton(this, 9);
    public final Button START = new JoystickButton(this, 10);
    public final Button leftJoystick = new JoystickButton(this, 11);
    public final Button rightJoystick = new JoystickButton(this, 12);
    
    /**Constructs and returns a new F310 Gamepad joystick, connected to input port.
     * @param port - the port the gamepad is connected to.
     */
    public F310GamePad(int port)
    {
        super(port);
    }

    /**
     * Constructs and returns a new F310 Gamepad joystick, connected to input port, and adjusts its
     * axis' values according to the input adjuster.
     * @param port - the port the gamepad is connected to.
     * @param adjuster - the function that should adjust the joysticks' values.
     */
    public F310GamePad(int port, Adjuster<Double> adjuster)
    {
        super(port, adjuster);
    }

}
