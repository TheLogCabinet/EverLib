/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.evergreen.everlib.oi.joysticks;

import com.evergreen.everlib.utils.Adjuster;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * A class for <a href="https://www.logitechg.com/en-roeu/products/gamepads/extreme-3d-pro-joystick.html">
 * Logitech's Extreme 3D Pro Joystick</a>, with all of its buttons as members, so there is no need to 
 * initilize or keep track of individual buttons. <p><p>
 * The Buttons are mapped as such: <p>
 * The buttons on they joystick itself are "top", and the ones on it base are "bottom".
 * The buttons on the left of the driver (with the Y axis pointoing forward) are "left", and the ones to
 * their right are "right".
 * The buttons on closer to the driver are "back" and the ones further away from them are "forward". <p><p>
 * Note that this class inherits {@link JoystickEG}, so it can be constructed with an
 * {@link Adjuster} which will automatically adjust its getRawAxis values. 
 */
public class ExtremeProJoystick extends JoystickEG {
    
    public final Button trigger = new JoystickButton(this, 1);
    public final Button thumb = new JoystickButton(this, 2);
    public final Button topLeftBack = new JoystickButton(this, 3);
    public final Button topRightBack = new JoystickButton(this, 4);
    public final Button topLeftForward = new JoystickButton(this, 5);
    public final Button topRightForward = new JoystickButton(this, 6);
    public final Button bottomLeftForward = new JoystickButton(this, 7);
    public final Button bottomRightForward = new JoystickButton(this, 8);
    public final Button bottomLeftMiddle = new JoystickButton(this, 9);
    public final Button bottomRightMiddle = new JoystickButton(this, 10);
    public final Button bottomLeftBack = new JoystickButton(this, 11);
    public final Button bottomRightBack = new JoystickButton(this, 12);

    public ExtremeProJoystick(int port)
    {
        super(port);
    }

    public ExtremeProJoystick(int port, Adjuster<Double> adjuster)
    {
        super(port, adjuster);
    }

}
