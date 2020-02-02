package com.evergreen.everlib;

import com.evergreen.everlib.oi.joysticks.ExtremeProJoystick;
import com.evergreen.everlib.oi.joysticks.F310GamePad;
import com.evergreen.everlib.shuffleboard.loggables.DashboardStreams;
import com.evergreen.everlib.structure.Tree;
import com.evergreen.everlib.subsystems.motors.subsystems.MotorController;
import com.evergreen.everlib.subsystems.motors.subsystems.MotorController.ControllerType;
import com.evergreen.everlib.subsystems.sensors.EvergreenGyro;
import com.evergreen.everlib.subsystems.sensors.DistanceSensor;
import com.evergreen.everlib.subsystems.sensors.DistanceSensorGroup;
import com.evergreen.everlib.subsystems.sensors.EvergreenEncoder;
import com.evergreen.everlib.utils.ranges.MaxLimit;
import com.evergreen.everlib.utils.ranges.MinLimit;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

/**
 * Test
 */
public class Test extends Tree {


    MotorController m_motorRight = new MotorController(
        "Motor Right",
        new MotorController("Right Talons", ControllerType.TALON_SRX, 0),
        new MotorController("Right Victors", ControllerType.VICTOR_SPX, 1, 2));

    MotorController m_motorLeft = new MotorController(
        "Motor Left",
        new MotorController("Left Talons", ControllerType.TALON_SRX, 3),
        new MotorController("Right Victors", ControllerType.VICTOR_SPX, 4, 5));

    ExtremeProJoystick m_jsLeft = new ExtremeProJoystick("Joystick Left", 0, (v) -> v * 0.5);
    ExtremeProJoystick m_jsRight = new ExtremeProJoystick("Joystick Right", 1, (v) -> v * 0.5);
    F310GamePad m_jsButton = new F310GamePad("Joystick Button", 2);

    EvergreenGyro m_angleSensor = new EvergreenGyro("Angle Sensor", new ADXRS450_Gyro());


    DistanceSensor m_baseSensor = new DistanceSensor("Base Sensor"){
    
        @Override
        protected double _getDistance() {
            return m_jsButton.getZ() * 100;
        }
    };

    DistanceSensor m_baseSensorLimited = new DistanceSensor(
        "Base Sensor Limited",new MinLimit(50.0)) {
    
        @Override
        protected double _getDistance() {
            return m_jsButton.getZ() * 100;
        }
        
    };

    DistanceSensor m_baseSensorOffset = new DistanceSensor(
        "Base Sensor With Offset", 30.0){
    
        @Override
        protected double _getDistance() {
            return m_jsButton.getZ() * 100;
        }
    };

    DistanceSensor m_baseSensorComplete = new DistanceSensor(
        "Base Sensor Complete", new MaxLimit(50.0), 30.0){
    
        @Override
        protected double _getDistance() {
            return m_jsButton.getZ() * 100;
        }
    };

    DistanceSensorGroup m_sensorGroup = 
        new DistanceSensorGroup(
            "Sensor Group", m_baseSensor, m_baseSensorLimited);

    EvergreenEncoder m_encoderA = new EvergreenEncoder("Test Encoder", 0, 1);

    EvergreenEncoder m_encoderB = m_motorLeft.getEncoder();



    @Override
    protected void componentSetup() {
        m_sensorGroup.addSensor(m_baseSensorOffset);
        m_sensorGroup.addSensor(m_baseSensorComplete);
    }

    @Override
    protected void bindButtons() {
       
    }

    @Override
    protected void commandConfig() {
       
    }

    @Override
    protected void log() {
        DashboardStreams.addLoggable(m_baseSensor);
        DashboardStreams.addLoggable(m_baseSensorLimited);
        DashboardStreams.addLoggable(m_baseSensorOffset);
        DashboardStreams.addLoggable(m_baseSensorComplete);
        DashboardStreams.addLoggable(m_sensorGroup);
        DashboardStreams.addLoggable(m_encoderA);
        DashboardStreams.addLoggable(m_encoderB);
        DashboardStreams.addLoggable(m_angleSensor);
    }

    @Override
    protected void whenEnabled() {
       

    }

    @Override
    protected void autoConfig() {
       

    }

    @Override
    protected void teleopConfig() {
       

    }

    @Override
    protected void test() {
       
    }

    @Override
    public void testPeriodic() {

    }

    
}