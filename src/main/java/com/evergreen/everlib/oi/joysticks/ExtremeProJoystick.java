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

    public ExtremeProJoystick(int port)
    {
        super(port);
    }

    public ExtremeProJoystick(int port, Adjuster<Double> adjuster)
    {
        super(port, adjuster);
    }

    public enum X {
        LEFT(0),
        RIGHT(1);

        int m_portsStart;

        private X(int portStart) {
            m_portsStart = portStart;
        }
    }
    
    public enum Y {
        FORWARD(0),
        MIDDLE(1),
        BACK(2);

        int m_portsStart;

        private Y(int portStart) {
            m_portsStart = portStart;
        }
    }

    public enum Z {
        TOP(3),
        BOTTOM(7);

        int m_portsStart;

        private Z(int portStart) {
            m_portsStart = portStart;
        }
    }

    public Button getButton(X x, Y y, Z z) {
        return new JoystickButton(this, x.m_portsStart + y.m_portsStart + z.m_portsStart);
    }

    public Button trigger() {
        return new JoystickButton(this, 1);
    }

    public Button thumb() {
        return new JoystickButton(this, 2);
    }
}
