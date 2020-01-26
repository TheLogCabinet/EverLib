package com.evergreen.everlib;

import com.evergreen.everlib.shuffleboard.constants.ConstantDouble;
import com.evergreen.everlib.shuffleboard.loggables.DashboardStreams;
import com.evergreen.everlib.structure.Tree;
import com.evergreen.everlib.utils.ranges.DoubleLimit;
import com.evergreen.everlib.utils.ranges.Limitless;
import com.evergreen.everlib.utils.ranges.MaxLimit;
import com.evergreen.everlib.utils.ranges.MinLimit;
import com.evergreen.everlib.utils.ranges.Range;

/**
 * Test
 */
public class Test extends Tree {

    ConstantDouble m_maxLimit = new ConstantDouble("Max Limit", -5);
    ConstantDouble m_minLimit = new ConstantDouble("Min Limit", 5);
    ConstantDouble m_tolerance = new ConstantDouble("Tolerance", 1);

    ConstantDouble m_testAgainst = new ConstantDouble("Testing Against", 0);

    Range m_lambdaRange = (v) -> (Math.pow(v, 2) + 4 * v - 2 ) > 0;

    Range m_maxRange = new MaxLimit(m_maxLimit);  
    Range m_tolerantMaxRange = new MaxLimit(m_maxLimit, m_tolerance); 

    Range m_minRange = new MinLimit(m_minLimit);  
    Range m_tolerantMinRange = new MinLimit(m_minLimit, m_tolerance); 

    Range m_doubleRange = new DoubleLimit(m_maxLimit, m_minLimit);  
    Range m_tolerantDoubleRange = new DoubleLimit(m_maxLimit, m_minLimit, m_tolerance);

    Range m_limitless = new Limitless();

    @Override
    protected void componentSetup() {

    }

    @Override
    protected void bindButtons() {
       
    }

    @Override
    protected void commandConfig() {
       

    }

    @Override
    protected void log() {

        DashboardStreams.addBoolean(
            "In Range - Lambda", 
            () -> m_lambdaRange.inRange(m_testAgainst.get()));
        
        DashboardStreams.addBoolean(
            "In Range - Min", 
            () -> m_minRange.inRange(m_testAgainst.get()));
        
        DashboardStreams.addBoolean(
            "In Range - Max", 
            () -> m_maxRange.inRange(m_testAgainst.get()));
    
        DashboardStreams.addBoolean(
            "In Range - Double", 
            () -> m_doubleRange.inRange(m_testAgainst.get()));
    
        DashboardStreams.addBoolean(
            "In Range - Limitless", 
            () -> m_limitless.inRange(m_testAgainst.get()));
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