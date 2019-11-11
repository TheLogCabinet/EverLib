/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.evergreen.everlib.oi.joysticks;

import com.evergreen.everlib.utils.Adjuster;
import com.wpilib2020.framework.button.Button;
import com.wpilib2020.framework.button.JoystickButton;


/**
 * A class for <a href="https://www.logitechg.com/en-roeu/products/gamepads/f310-gamepad.html">
 * Logitech's F310 Gamepad</a>, with all of its buttons as members, so there is no need to 
 * initilize or keep track of individual buttons. <p><p>
 * Noth that this class inherits {@link JoystickEG}, so it can be constructed with an
 * {@link Adjuster} which will automatically adjust its getRawAxis values.
 */
public class F310GamePad extends JoystickEG {
    
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

    public enum F310 {
        X(1),
        A(2),
        B(3),
        Y(4),
        LB(5),
        RB(6),
        LT(7),
        RT(8),
        BACK(9),
        START(10),
        JOYSTICK_LEFT(11),
        JOYSTICK_RIGHT(12);
        
        int m_buttonPort;

        private F310(int buttonPort) {
            m_buttonPort = buttonPort;
        }
    }

    public Button get(F310 button) {
        return new JoystickButton(this, button.m_buttonPort);
    }

}
