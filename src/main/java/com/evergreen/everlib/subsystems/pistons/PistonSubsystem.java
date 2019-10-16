package com.evergreen.everlib.subsystems.pistons;


import java.util.function.Consumer;
import java.util.function.Supplier;

import com.evergreen.everlib.shuffleboard.handlers.SwitchHandler;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * A generic subsystem which uses pistons for movement.
 * It can iterates between Two {@link DoubleSolenoid.Value states}: 
 * {@link DoubleSolenoid.Value#kForward Forward} and {@link DoubleSolenoid.Value#kReverse Reverse,}
 * and {@link DoubleSolenoid.Value#kOff Off},    
 */
public class PistonSubsystem extends Subsystem {
    private Consumer<Value> pistonSetter;
    public Supplier<Value> stateSupplier;
    private Supplier<Boolean> switchOn;
    public String name;

    public PistonSubsystem(Consumer<Value> pistonSetter, Supplier<Value> stateSupplier, String name)
    {
        this.name = name;
        switchOn = SwitchHandler.addSwitch(name);
        this.pistonSetter = pistonSetter;
        this.stateSupplier = stateSupplier;
    }

    public PistonSubsystem(Consumer<Value> pistonSetter, Supplier<Value> stateSupplier, String name, boolean defaultValue)
    {
        this.name = name;
        switchOn = SwitchHandler.addSwitch(name, defaultValue);
        this.pistonSetter = pistonSetter;
        this.stateSupplier = stateSupplier;
        // new DashBoardController().addString (
        //     String.format("{0} - Value", name),
        //     () -> //A supplier which returns a string representation of the current state of the piston.
        //     {
        //         if(isReversed())
        //         {
        //             return "Reversed";
        //         }

        //         else if(isForward())
        //         {
        //             return "Forward";
        //         }

        //         else
        //         {
        //             return "Off";
        //         }
        //     });
    }

    public void toggle()
    {
        if(isForward())
        {
            setReverse();
        }

        else
        {
            setForward();
        }
    }

    public void setReverse()
    {
        set(Value.kReverse);
    }

    public void setForward()
    {
        set(Value.kForward);
    }

    public void setOff()
    {
        set(Value.kOff);
    }

    public boolean isReversed()
    {
        return stateSupplier.get().compareTo(Value.kReverse) == 0;
    }

    public boolean isForward()
    {
        
        return stateSupplier.get().compareTo(Value.kForward) == 0;
    }

    public boolean isOff()
    {
        return stateSupplier.get().compareTo(Value.kOff) == 0;
    }

    public void set(Value state)
    {
        if(switchOn.get())
        {
            pistonSetter.accept(state);
        }
    }

    public void initDefaultCommand()
    {
    
    }

}
