/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.evergreen7112.everlib.subsystems.motors.subsystems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Consumer;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;

/**
 * Add your docs here.
 */
public class MotorController implements Consumer<Double>, SpeedController {
    private Consumer<Double> setter;
    private SpeedController obj;
    private MotorType type;
    private HashMap<Integer, MotorType> motors;

    MotorController(MotorType type, int... ports)
    {

        this.motors = new HashMap<>();
        for(int port : ports)
        {
            this.motors.put(port, type);
        }

        this.type = type;
        if(type == MotorType.VICTOR)
        {
            SpeedController[] motors = new SpeedController[ports.length];
            SpeedController firstMotor = new Victor(ports[0]);
            
            for(int i = 1; i < ports.length; i++)
            {
                motors[i-1] = new Victor(ports[i]); 
            }

            obj = new SpeedControllerGroup(firstMotor, motors);
            this.setter = obj::set;
        }

        else if(type == MotorType.TALON)
        {
            
            this.type = type;
            if(type == MotorType.VICTOR)
            {
                SpeedController[] motors = new SpeedController[ports.length];
                SpeedController firstMotor = new Victor(ports[0]);
                
                for(int i = 1; i < ports.length; i++)
                {
                    motors[i-1] = new Talon(ports[i]); 
                }

                obj = new SpeedControllerGroup(firstMotor, motors);
                this.setter = obj::set;
            }
        }
    }

    MotorController(MotorController... controllers)
    {
        HashSet<Integer> ports = new HashSet<>();
        ArrayList<SpeedController> motors = new ArrayList<>();
        for(MotorController controller : controllers)
        {
            controller.motors.forEach((port, type) -> 
            {
                if(!ports.contains(port))
                {
                    if(type == MotorType.VICTOR)
                    {
                        motors.add(new Victor(port));
                    }

                    else if(type == MotorType.TALON)
                    {
                        motors.add(new Talon(port));
                    }

                    ports.add(port);
                    this.motors.put(port, type);
                }
            });
        }

        SpeedController firstMotor = motors.get(0);
        motors.remove(0);
        SpeedController[] args = (SpeedController[])motors.toArray();
        obj = new SpeedControllerGroup(firstMotor, args);
        this.setter = obj::set;
    }

    public enum MotorType
    {
        VICTOR,
        TALON;
    }

    public MotorType getType()
    {
        return type;
    }

    public void accept(Double speed)
    {
        setter.accept(speed);
    }

    public void accept(Supplier<Double> speedSupplier)
    {
        setter.accept(speedSupplier.get());
    }

    @Override
    public void pidWrite(double output) {

    }

    @Override
    public void set(double speed) {
        obj.set(speed);
    }

    @Override
    public double get() {
        return obj.get();
    }

    @Override
    public void setInverted(boolean isInverted) {
        obj.setInverted(isInverted);
    }

    @Override
    public boolean getInverted() {
        return obj.getInverted();
    }

    @Override
    public void disable() {
        obj.disable();
    }

    @Override
    public void stopMotor() {
        obj.stopMotor();
    }
}
