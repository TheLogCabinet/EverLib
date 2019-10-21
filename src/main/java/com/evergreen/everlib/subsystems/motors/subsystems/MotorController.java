package com.evergreen.everlib.subsystems.motors.subsystems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

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

    /**
     * Constructs a new {@link MotorController} which controlls one of more motors with the same 
     * type of electronic controller.
     * 
     * @param type - The {@link ControllerType} type of the electronic speed controllers that 
     * produce the voltage for the motors. (Victor, Talon, ect.)
     * 
     * @param ports - The roborio ports the voltage controllers conect to.
     */
    public MotorController(ControllerType type, int... ports)
    {
        SpeedController controller;

        //Check fot the type, then create the aproptiate SpeedControllers and initalize the
        //Wrapped object as their SpeedControllerGroup.

        if(type == ControllerType.VICTOR_SPX) 
        {
            //The speed controllers to construct the Group with.
            SpeedController[] motors = new SpeedController[ports.length];
            SpeedController firstMotor = new WPI_VictorSPX(ports[0]);
            
            //intializing them.
            for(int i = 1; i < ports.length; i++)
            {
                controller = new WPI_VictorSPX(ports[i]);
                m_motors.add(controller);
                motors[i-1] = controller; 
            }

            //Initializing the wrapped object.
            m_obj = new SpeedControllerGroup(firstMotor, motors);
        }

        else if(type == ControllerType.TALON_SRX)
        {
            //The speed controller to construct the Group with.
            SpeedController[] motors = new SpeedController[ports.length];
            SpeedController firstMotor = new WPI_TalonSRX(ports[0]);
            
            for(int i = 1; i < ports.length; i++)
            {
                controller = new WPI_TalonSRX(ports[i]);
                
                m_motors.add(controller);
                motors[i-1] = controller; 
            }

            m_obj = new SpeedControllerGroup(firstMotor, motors);
        }
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
            //For each of the moto
            controller.m_motors.forEach((control) -> motors.add(control));
        }

        //Extract the first element, convert the list to array, and use the resulting objects
        //To initialize the wrapped object.
        SpeedController firstMotor = motors.get(0);
        motors.remove(0);
        SpeedController[] args = (SpeedController[])motors.toArray();
        m_obj = new SpeedControllerGroup(firstMotor, args);
    }

    @Override
    public void pidWrite(double output) {
        m_obj.pidWrite(output);
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
        VICTOR_SPX,
        VICTOR_SP,
        TALON_SRX;
    }
}
