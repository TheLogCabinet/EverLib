package com.evergreen.everlib.subsystems.sensors;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;

import edu.wpi.first.wpilibj.Encoder;

/**
 * EncoderBase
 */
public class EncoderEG extends DistanceSensor {

    double m_distancePerPulse;
    Supplier<Integer> m_ticksSupplier;
    Consumer<Integer> m_ticksSetter;
    double m_lastPosition;
    double m_lastCalculation;

    //TODO Try moving ensor tuning to RobotInit().

    private EncoderEG(Supplier<Integer> ticksSupplier,
     Consumer<Integer> ticksSetter, double distancePerPulse) {
        m_ticksSupplier = ticksSupplier;
        m_distancePerPulse = distancePerPulse;

        m_lastPosition = 0;
        m_lastCalculation = 0;
    }

    public EncoderEG(CANEncoder wrappedEncoder, double distancePerPulse) {
        

        this( 
            () -> (int)wrappedEncoder.getPosition() / 
                //Divide by pos factor to make sure to get ticksSupplier instead of distance.
                (int)wrappedEncoder.getPositionConversionFactor(),
            (position) -> wrappedEncoder.setPosition(position), 
            distancePerPulse);
    }
    
    public EncoderEG(CANEncoder wrappedEncoder) {
        this(wrappedEncoder, 1);
    }


    public EncoderEG(WPI_TalonSRX wrappedEncoder, double distancePerPulse) {
        this(
           wrappedEncoder::getSelectedSensorPosition, 
           wrappedEncoder::setSelectedSensorPosition,
           distancePerPulse);
    }
    
    public EncoderEG(WPI_TalonSRX wrappedEncoder) {
        this(wrappedEncoder, 1);
    }

    public EncoderEG(Encoder wrappedEncoder, double distancePerPulse) {
        this(wrappedEncoder::getRaw,
        (position) -> wrappedEncoder.reset(),
        distancePerPulse);
    }
    
    public EncoderEG(Encoder wrappedEncoder) {
        this(wrappedEncoder, 1);
    }
    
    public EncoderEG(int portA, int portB, double distancePerPulse) {
        this(new Encoder(portA, portB), distancePerPulse);
    }

    public EncoderEG(int portA, int portB) {
        this(portA, portB, 1);
    }

    public double getTicks() {
        return m_ticksSupplier.get();
    }

    public double getPosition() {
        m_lastPosition =  m_ticksSupplier.get() * m_distancePerPulse;
        m_lastCalculation = System.currentTimeMillis() / 1000;

        return m_lastPosition;
    }

    public double getSpeed() {

        //Save the last time, sinmce getPosition will override it.
        double lastPosition = m_lastPosition; 

        double now = System.currentTimeMillis() / 1000;
        return (getPosition() - lastPosition) / ( now - m_lastCalculation);
    }

    public double getDistancePerPulse() {
        return m_distancePerPulse;
    }
    public void setDistancePerPulse(double distancePerPulse) {
        m_distancePerPulse = distancePerPulse;
    }

    public void setPosition(double position) {
        m_ticksSetter.accept( (int)(position / m_distancePerPulse) );
        getPosition();
    }

    public void reset() {
        setPosition(0);
    }

    @Override
    protected double _getDistance() {
        return getPosition();
    }

    

}