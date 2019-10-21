package com.evergreen.everlib.shuffleboard.handlers;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Preferences;

/**
 * DashboardConstants
 */
public class DashboardConstants {
    private static Map<String, String> strings = new HashMap<>();
    private static Map<String, Boolean> booleans = new HashMap<>();
    private static Map<String, Double> doubles = new HashMap<>();
    private static Map<String, Integer> integers = new HashMap<>();

    public static Supplier<Double> addDouble(String name, Double value)
    {
        Preferences.getInstance().putDouble(name, value);
            
        System.out.println(String.format("Added {0} double constant: {1}", name, value));

        return () -> Preferences.getInstance().getDouble(name, 0);
    }

    public static Supplier<Integer> addInt(String name, int value, boolean printVerbose)
    {
        Preferences.getInstance().putInt(name, value);

        if(printVerbose)
        {
            System.out.println(String.format("Added {0} integer constant: {1}", name, value));
        }

        return () -> Preferences.getInstance().getInt(name, 0);
    }


    public static Supplier<String> addString(String name, String value, boolean printVerbose)
    {
        Preferences.getInstance().putString(name, value);

        if(printVerbose)
        {
            System.out.println(String.format("Added {0} string constant: {1}", name, value));
        }

        return () -> Preferences.getInstance().getString(name, "Value not found");
    }


    public static Supplier<Boolean> addBoolean(String name, boolean value, boolean printVerbose)
    {
        Preferences.getInstance().putBoolean(name, value);

        if(printVerbose)
        {
            System.out.println(String.format("Added {0} boolean constant: {1}", name, value));
        }

        return () -> Preferences.getInstance().getBoolean(name, false);
    }

    
    public static Supplier<Boolean> addBoolean(String name, boolean value)
    {
        if (!Preferences.getInstance().containsKey(name))
            Preferences.getInstance().putBoolean(name, value);

        return () -> Preferences.getInstance().getBoolean(name, false);
    }


    public static void remove(String key)
    {
        strings.remove(key);
        integers.remove(key);
        doubles.remove(key);
        booleans.remove(key);
        Preferences.getInstance().remove(key);
    }


}