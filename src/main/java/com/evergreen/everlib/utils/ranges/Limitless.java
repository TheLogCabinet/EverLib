package com.evergreen.everlib.utils.ranges;

/**
 * Limitless
 */
public class Limitless implements Range {
    
    @Override
    public boolean inRange(double value) {
        return true;
    }
}