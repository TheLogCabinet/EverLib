package com.evergreen.everlib.utils;

/**
 * UselessAdjuster
 */
public class UselessAdjuster<T> implements Adjuster<T> {

    @Override
    public T adjust(T value) {
        return value;
    }
}