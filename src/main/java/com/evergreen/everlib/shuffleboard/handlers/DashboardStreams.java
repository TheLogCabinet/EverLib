package com.evergreen.everlib.shuffleboard.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.evergreen.everlib.utils.loggables.LoggableData;
import com.evergreen.everlib.utils.loggables.LoggableObject;
import com.wpilib2020.deps.SmartDashboard;

/**
 * DashboardStreams
 */
public class DashboardStreams {

    private static final Map<String, Supplier<String>> m_stringMap = new HashMap<>();
    private static final Map<String, Supplier<Integer>> m_intMap = new HashMap<>();
    private static final Map<String, Supplier<Double>> m_doubleMap = new HashMap<>();
    private static final Map<String, Supplier<Boolean>> m_boolMap = new HashMap<>();

    private static final List<LoggableData> m_loggables = new ArrayList<>();

    public static void addString(String key, Supplier<String> valueSupplier) {
        if (!SmartDashboard.containsKey(key)) {
            SmartDashboard.putString(key, valueSupplier.get());
            m_stringMap.put(key, valueSupplier);
        }

        else {
            System.out.println("Tried to add \"" + key + "\" String value to the shuffleboard,"
                    + "but couldn't becuase there was already a value under that key.");
        }
    }

    public static void addInt(String key, Supplier<Integer> valueSupplier) {

        if (!SmartDashboard.containsKey(key)) {
            SmartDashboard.putNumber(key, valueSupplier.get());
            m_intMap.put(key, valueSupplier);
        }

        else {
            System.out.println("Tried to add \"" + key + "\" String value to the shuffleboard,"
                + "but couldn't becuase there was already a value under that key.");
        }
    }

    public static void addDouble(String key, Supplier<Double> valueSupplier) {

        if (!SmartDashboard.containsKey(key)) {
            SmartDashboard.putNumber(key, valueSupplier.get());
            m_doubleMap.put(key, valueSupplier);
        }

        else {
            System.out.println("Tried to add \"" + key + "\" String value to the shuffleboard,"
                + "but couldn't becuase there was already a value under that key.");
        }
    }

    public static void addBoolean(String key, Supplier<Boolean> valueSupplier) {

        if (!SmartDashboard.containsKey(key)) {
            SmartDashboard.putBoolean(key, valueSupplier.get());
            m_boolMap.put(key, valueSupplier);
        }

        else {
            System.out.println("Tried to add \"" + key + "\" String value to the shuffleboard,"
                + "but couldn't becuase there was already a value under that key.");
        }
    }

    public static void addLoggable(LoggableObject loggable) {

        for (LoggableData loggableData : loggable.getLoggableData()) 
        {
            String key = loggableData.getKey();

            if (!SmartDashboard.containsKey(key)) {
                loggableData.addToDashboard();
                m_loggables.add(loggableData);
            }
    
            else {
                System.out.println("Tried to add \"" + key + "\" of" + loggable.getName() 
                + " String value to the shuffleboard, but couldn't becuase there was"
                + " already a value under that key.");
            }
        }
    }


    public static void update() {

        //Update Strings
        for (Map.Entry<String, Supplier<String>> field : m_stringMap.entrySet()) {
            SmartDashboard.putString(field.getKey(), field.getValue().get());
        }

        //Update Integers
        for (Map.Entry<String, Supplier<Integer>> field : m_intMap.entrySet()) {
            SmartDashboard.putNumber(field.getKey(), field.getValue().get());
        }

        //Update Double
        for (Map.Entry<String, Supplier<Double>> field : m_doubleMap.entrySet()) {
            SmartDashboard.putNumber(field.getKey(), field.getValue().get());
        }

        //Update Booleans
        for (Map.Entry<String, Supplier<Boolean>> field : m_boolMap.entrySet()) {
            SmartDashboard.putBoolean(field.getKey(), field.getValue().get());
        }

        //Update Loggables
        for (LoggableData loggableData: m_loggables) {
            loggableData.addToDashboard();
        }
    }



}