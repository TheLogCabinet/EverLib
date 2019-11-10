package com.evergreen.everlib.utils.loggables;

import java.util.function.Supplier;

import com.wpilib2020.deps.SmartDashboard;

/**
 * LoggableNumber
 */
public class LoggableBoolean extends LoggableData {
    
    Supplier<Boolean> m_stream;

    public LoggableBoolean(String key, Supplier<Boolean> stream) {
        super(key);
        m_stream = stream;
    }
    
    @Override
    public void addToDashboard() {
        SmartDashboard.putBoolean(getKey(), m_stream.get());
    }


}