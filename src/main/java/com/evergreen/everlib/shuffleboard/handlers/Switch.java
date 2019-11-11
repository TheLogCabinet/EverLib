/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.evergreen.everlib.shuffleboard.handlers;

import java.util.function.Supplier;

/**
 * A boolean {@link Supplier} which also has a String name member. 
 * Meant for usage in shuffleboard switches (via the {@link SwitchHandler}),
 * such that programmers will be able to access them manipulate without needing 
 * to keep a sperate name or key variable.
 */
public class Switch implements Supplier<Boolean> {
    /**The switche's name, as set in construction */
    private String name;
    /**The supplier ran in get().*/
    private Supplier<Boolean> getter;

    /**Initializes the Switch's members by the given parameters. 
     * @param name - the name for the switch. This will be the String returned when accessing
     * the name member.
     * @param getter - the Supplier itself. This is the method that will be ran in get().*/
    protected Switch(String name, Supplier<Boolean> getter) {
        this.name = name;
        this.getter = getter;
    }

    @Override
    public Boolean get() {
        return getter.get();
    }

    /**Returns  */
    public String getKey() {
        return name;
    }
}
