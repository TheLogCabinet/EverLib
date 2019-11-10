package com.evergreen.everlib.utils.loggables;

/**
 * Loggable
 */
public abstract class LoggableData {
    private String m_key;

    public LoggableData(String key) {
        m_key = key;
    }

    public String getKey() {
        return m_key;
    }

    public abstract void addToDashboard();
}