package com.atlantbh.jmeter.plugins.jtlresultsparser.model.junit;

import java.util.ArrayList;

/**
 * Created by adnan on 07/01/16.
 */
public class TestCase {
    private String className;
    private ArrayList<TestStep> testSteps = new ArrayList<TestStep>();
    private int failedTests = 0;
    private double executionTime = 0;

    public TestCase() {
        super();
    }

    public ArrayList<TestStep> getTestSteps() {
        return testSteps;
    }

    public ArrayList<TestStep> getFailiuresList() {
        ArrayList<TestStep> failiures = new ArrayList<TestStep>();
        for (TestStep testStep : testSteps)
            if (testStep.getAssertionFailuresList().size() > 0)
                failiures.add(testStep);
        return failiures;
    }

    public double getExecutionTime(){
        return executionTime;
    }

    public int getFailedTests(){
        return failedTests;
    }

    public void addTestStep(TestStep testStep) {
        this.testSteps.add(testStep);
        if (testStep.getAssertionFailuresList().size() > 0 )
            failedTests++;
        executionTime += Double.parseDouble(testStep.getTime());
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}

