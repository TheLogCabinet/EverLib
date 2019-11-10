package com.evergreen.everlib.utils.loggables;

import java.util.function.Supplier;

import com.wpilib2020.deps.SmartDashboard;

/**
 * LoggableNumber
 */
public class LoggableDouble extends LoggableData {
    
    Supplier<Double> m_stream;

    public LoggableDouble(String key, Supplier<Double> stream) {
        super(key);
        m_stream = stream;
    }
    
    @Override
    public void addToDashboard() {
        SmartDashboard.putNumber(getKey(), m_stream.get());
    }


}