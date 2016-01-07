package com.atlantbh.jmeter.plugins.jtlresultsparser.model.junit;

import java.util.ArrayList;

/**
 * Created by adnan on 07/01/16.
 */
public class TestSuite {

    private String name;
    private String tests;
    private String failures;
    private String time;
    private ArrayList<TestCase> testCases = new ArrayList<TestCase>();

    public TestSuite() {
        super();
        failures = "0";
        time = "0";
        tests = "0";
    }

    public ArrayList<TestCase> getTestCases() {
        return testCases;
    }

    public void addTestCase(TestCase testCase) {
        this.testCases.add(testCase);
        tests = String.valueOf(Integer.parseInt(tests) + testCase.getTestSteps().size());
        failures = String.valueOf(Integer.parseInt(failures) + testCase.getFailedTests());
        time = String.valueOf(Double.parseDouble(time) + testCase.getExecutionTime());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    public String getFailures() {
        return failures;
    }

    public void setFailures(String failures) {
        this.failures = failures;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

