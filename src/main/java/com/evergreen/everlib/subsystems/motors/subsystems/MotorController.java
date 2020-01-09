package com.evergreen.everlib.subsystems.motors.subsystems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;



/**
 * This is a wrapper class for {@link SpeedController}, which allowes easier and more generic
 * construction of SpeedControlelr objects.
 * 
 * @author Atai Ambus
 */
public class MotorController implements SpeedController {

    /**
     * MotorInitialize
     */
    public interface MotorInitializer {
        SpeedController generate(int channel);
    }

    /**
     * The wrapped {@link SpeedController} object.
     */
    private SpeedController m_obj;

    /**A {@link HashMap} which lists all motors this controller controls - their port
     * and the type pf their hardware controller
     * 
     * Within this class, it is used for the constructio of new {@link MotorController} objects
     * from other MotorController objects.
      */
    private ArrayList<SpeedController> m_motors = new ArrayList<>();


    private List<CANEncoder> m_encoders = new ArrayList<>();


    /**
     * Constructs a new {@link MotorController} which controlls one of more motors with the same 
     * type of electronic controller.
     * 
     * @param type - The {@link ControllerType} type of the electronic speed controllers that 
     * produce the voltage for the motors. (Victor, Talon, ect.)
     * 
     * @param ports - The roborio ports the voltage controllers conect to.
     */
    public MotorController(ControllerType type, int... ports) {
        
        SpeedController firstMotor;
        SpeedController[] motors;
        SpeedController finalController;
        
        //Check fot the type, then create the aproptiate SpeedControllers and initalize the
        //Wrapped object as their SpeedControllerGroup.

        //The speed controllers to construct the Group with.
        motors = new SpeedController[ports.length];
        firstMotor = type.m_init.generate(ports[0]);
        
        //intializing them.
        for(int i = 1; i < ports.length; i++)
        {
            finalController = type.initlize(ports[i]);
            if (type == ControllerType.SPARKMAX_BRUSHED || type == ControllerType.SPARKMAX_BRUSHLESS)
                m_encoders.add( ((CANSparkMax)finalController).getEncoder() );
            m_motors.add(finalController);
            motors[i-1] = finalController; 
        }   

        //Initializing the wrapped object.
        m_obj = new SpeedControllerGroup(firstMotor, motors);
    }


    /**
     * @return A list of all CAN encoders this motor uses.
     */
    public List<CANEncoder> getCANEncoders() {
        return m_encoders;
    }

    /**
     * Returns the encoder of the first SparkMax this controller uses. 
     * Usefull if there is only one.
     */
    public CANEncoder getCANEncoder() {
        return m_encoders.get(0);
    }

    /**
     * Consrtucts a {@link MotorController} which combines multiple other MotorControllers.
     * Also works as a copy constructor. <p>
     * 
     * The new Controller's methods will aplly on all the given controllers.
     * 
     * @param controllers - The controllers to combine.
     */
    public MotorController(MotorController... controllers)
    {

        //An array list of all speed controllers created, which will be used to initialize 
        //The wrapped SpeedControlelrGroup object.
        ArrayList<SpeedController> motors = new ArrayList<>();   
        
        //Foreach of the motors, add its controller to the controller list.
        for(MotorController controller : controllers)
        {
            controller.m_motors.forEach((control) -> motors.add(control));
            m_encoders.addAll(controller.getCANEncoders());
        }

        //Extract the first element, convert the list to array, and use the resulting objects
        //To initialize the wrapped object.
        SpeedController firstMotor = motors.get(0);
        motors.remove(0);
        SpeedController[] args = (SpeedController[])motors.toArray();
        m_obj = new SpeedControllerGroup(firstMotor, args);
    }

    @Override
    public void set(double speed) {
        m_obj.set(speed);
    }

    public void set(Supplier<Double> speed) {
        set(speed.get());
    }

    @Override
    public double get() {
        return m_obj.get();
    }

    @Override
    public void setInverted(boolean isInverted) {
        m_obj.setInverted(isInverted);
    }

    @Override
    public boolean getInverted() {
        return m_obj.getInverted();
    }

    @Override
    public void disable() {
        m_obj.disable();
    }

    @Override
    public void stopMotor() {
        m_obj.stopMotor();
    }

    /**A controller model - Victor SPX, Talon SRX, ect. */
    public enum ControllerType
    {
        VICTOR_SPX(WPI_VictorSPX::new),
        TALON_SRX(WPI_TalonSRX::new),
        SPARKMAX_BRUSHED( (port) -> new CANSparkMax(port, MotorType.kBrushed)),
        SPARKMAX_BRUSHLESS( (port) -> new CANSparkMax(port, MotorType.kBrushless));

        MotorInitializer m_init;

        SpeedController initlize(int channel) {
            return m_init.generate(channel);
        }

        ControllerType(MotorInitializer init) {
            m_init = init;
        }
    }

    @Deprecated
    @Override
    public void pidWrite(double output) {
        m_obj.pidWrite(output);
    }
}
