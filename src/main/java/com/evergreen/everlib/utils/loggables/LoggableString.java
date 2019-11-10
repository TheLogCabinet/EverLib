package com.evergreen.everlib.utils.loggables;

import java.util.function.Supplier;

import com.wpilib2020.deps.SmartDashboard;

/**
 * LoggableNumber
 */
public class LoggableString extends LoggableData {
    
    Supplier<String> m_stream;

    public LoggableString(String key, Supplier<String> stream) {
        super(key);
        m_stream = stream;
    }
    
    @Override
    public void addToDashboard() {
        SmartDashboard.putString(getKey(), m_stream.get());
    }


}