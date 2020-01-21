package com.evergreen.everlib.subsystems.motors.commands;

import java.util.function.Supplier;

import com.evergreen.everlib.utils.PIDSettings;
import edu.wpi.first.wpilibj2.command.PIDCommand;

/**
 * Wrapps WPILib's {@link PIDCommand} to use supplier constants rather than plain doubles,
 * and
 */
public class _RunPID extends PIDCommand { 

    PIDSettings m_settings;

    public _RunPID(String name, PIDSettings pidSettings, Supplier<Double> setpoint) {
        super(
            pidSettings.getController(),
            pidSettings::getMeasurment,
            setpoint::get,
            pidSettings::write,
            pidSettings.getSubsystem());

        m_settings = pidSettings;
    }

    @Override
    public void execute() {
        super.execute();
        m_controller.setPID(m_settings.kP(), m_settings.kI(), m_settings.kD());
        m_controller.setTolerance(m_settings.getTolerance());
    }

    @Override
    public boolean isFinished() {
        return m_controller.atSetpoint();
    }

    public PIDSettings getSettings() {
        return m_settings;
    }

    public double getSetpoint() {
        return m_setpoint.getAsDouble();
    }
}