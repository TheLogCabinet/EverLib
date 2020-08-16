package com.evergreen.everlib.shuffleboard.loggables;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A basic abstract class that allows us to group various shuffleboard data streams, independently of the data type.
 */
public abstract class LoggableData {
    private String m_key;

    /***
     * Constructs a loggable data object and inputs its name into the key
     * @param key: the name of the data stream
     */
    public LoggableData(String key) {
        m_key = key;
    }

    public String getKey() {
        return m_key;
    }

    public void setKey(String key) {
        m_key = key;
    }

    
    public abstract void addToDashboard();

    public void remove() {
        SmartDashboard.delete(m_key);
    }

    public abstract String getType();
    public abstract String getValue();
}
