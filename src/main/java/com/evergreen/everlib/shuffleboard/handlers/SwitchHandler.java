/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.evergreen.everlib.shuffleboard.handlers;

import com.evergreen.everlib.Exceptions;

import edu.wpi.first.wpilibj.Preferences;

/**
 * Add your docs here.
 */
public class SwitchHandler implements Exceptions {

    /**
     * Adds a boolean switch to the shuffleboard's Prefrences instance by input key, 
     * with a init value of true, and returns the supplier for that switch.
     * @param name - The key for the shuffleboard switch
     * @return - A supplier which returns the current state of the switch
     */
    public static Switch addSwitch(String name)
    {
        return new Switch(name, DashboardConstants.addBoolean(name, true));
    }
    
    /**
     * Adds a boolean switch to the shuffleboard's Prefrences instance by input key and 
     * with a init value by input, and returns that switch.
     * @param name - The key for the shuffleboard switch
     * @param defaultValue - The value the switch gains at RobotInit() if it is not already
     * present on the shuffleboard.
     * @return the switch added
     */
    public static Switch addSwitch(String name, boolean defaultValue)
    {
        return new Switch(name, DashboardConstants.addBoolean(name, defaultValue));
    }

    public static void set(boolean value, Switch... switches)
    {
        
        for(Switch switchObj : switches)
        {
            if(Preferences.getInstance().containsKey(switchObj.getKey()))
            {
                Preferences.getInstance().putBoolean(switchObj.getKey(), value);
            }

            else
            {
                throw new SwitchNotFoundException("Input switch " + switchObj.getKey() + 
                   "Is not on the shuffleboard!");
            }
        }
    }
    

    /**
     * 
     * @param names
     * @throws SwitchNotFoundException
     */
    protected static void disable(String... names) throws SwitchNotFoundException
    {
        for(String name : names)
        {
            if(Preferences.getInstance().containsKey(name))
            {
                Preferences.getInstance().putBoolean(name, false);
            }

            else
            {
                new SwitchNotFoundException("Input key does not match any switch!");
            }
        }
    }

    public static void toggle(Switch... switches)
    {
        for (Switch switchObj : switches) {
            
            if(switchObj.get())
            {
                disable(switchObj);
            }

            else
            {
                enable(switchObj);
            }
        }
    }

    
    public static void disable(Switch... switches) throws SwitchNotFoundException
    {
        set(false, switches);
    }

    public static void enable(Switch... switches) throws SwitchNotFoundException
    {
        set(true, switches);
    }

    public static boolean isEnabled(String name)
    {
        if(Preferences.getInstance().containsKey(name))
        {
            return Preferences.getInstance().getBoolean(name, false);
        }

        else
        {
            throw new SwitchNotFoundException("Input key \""+name+"\" does not match any switch!");
        }
    }

    
    public static boolean isEnabled(Switch switchObj)
    {
        if(Preferences.getInstance().containsKey(switchObj.getKey()))
        {
            return isEnabled(switchObj.getKey());
        }

        else
        {
            throw new SwitchNotFoundException("Input switch \"" + switchObj.getKey() +
            "\" does not exist on the suffleboard");
        }
    }



}
