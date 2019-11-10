package com.evergreen.everlib.utils.loggables;

import java.util.function.Supplier;

import com.wpilib2020.deps.SmartDashboard;

/**
 * LoggableNumber
 */
public class LoggableInt extends LoggableData {
    
    Supplier<Integer> m_stream;

    public LoggableInt(String key, Supplier<Integer> stream) {
        super(key);
        m_stream = stream;
    }
    
    @Override
    public void addToDashboard() {
        SmartDashboard.putNumber(getKey(), m_stream.get());
    }


}