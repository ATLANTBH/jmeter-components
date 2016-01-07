package com.atlantbh.jmeter.plugins.jtlresultsparser;

import com.atlantbh.jmeter.plugins.jtlresultsparser.builder.JunitXmlBuilder;
import com.atlantbh.jmeter.plugins.jtlresultsparser.model.junit.TestCase;
import com.atlantbh.jmeter.plugins.jtlresultsparser.model.junit.TestStep;
import com.atlantbh.jmeter.plugins.jtlresultsparser.model.junit.TestSuite;
import org.apache.jmeter.JMeter;
import org.apache.jmeter.assertions.AssertionResult;
import org.apache.jmeter.engine.util.NoThreadClone;
import org.apache.jmeter.reporters.AbstractListenerElement;
import org.apache.jmeter.samplers.Remoteable;
import org.apache.jmeter.samplers.SampleEvent;
import org.apache.jmeter.samplers.SampleListener;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.TestStateListener;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by adnan on 07/01/16.
 */
public class JtlResultsParser extends AbstractListenerElement
        implements SampleListener, Serializable,
        NoThreadClone, TestStateListener, Remoteable {

    private ArrayList<TestCase> testCases = new ArrayList<TestCase>();
    private PrintStream out;
    private static final String OUTPUTFILE = "OUTPUTFILE";

    public JtlResultsParser() {
        super();
    }

    public void setOutputFile(String outputFile)
    {
        setProperty(OUTPUTFILE, outputFile);
    }

    public String getOutputFile()
    {
        return getPropertyAsString(OUTPUTFILE);
    }

    @Override
    public void sampleOccurred(SampleEvent sampleEvent) {
        TestCase testCase = getTestCase(sampleEvent);
        TestStep testStep = createTestStep(sampleEvent.getResult());
        testCase.addTestStep(testStep);
    }

    @Override
    public void sampleStarted(SampleEvent sampleEvent) {

    }

    @Override
    public void sampleStopped(SampleEvent sampleEvent) {

    }

    @Override
    public void testStarted() {
        if (JMeter.isNonGUI()) {
            out = System.out;
        }
    }

    @Override
    public void testStarted(String s) {
        testStarted();
    }

    @Override
    public void testEnded() {
        JunitXmlBuilder builder = JunitXmlBuilder.newInstance();
        TestSuite testSuite = new TestSuite();
        testSuite.setName("Jmeter Test Plan");
        for (TestCase testCase : testCases)
            testSuite.addTestCase(testCase);
        org.w3c.dom.Document xmlOutput = builder.generateXmlDoc(testSuite);

        builder.writeXmlDoc(xmlOutput, getOutputFile());
    }

    @Override
    public void testEnded(String s) {
        testEnded();
    }

    private TestStep createTestStep(SampleResult sampleResult){
        TestStep testStep = new TestStep();
        testStep.setName(sampleResult.getSampleLabel());
        testStep.setTime(String.valueOf(sampleResult.getTime()));

        for (AssertionResult assertionResult: sampleResult.getAssertionResults()) {
            if (assertionResult.isFailure() || assertionResult.isError()) {
                testStep.setAssertionFailures(assertionResult.getName(), assertionResult.getFailureMessage());
            }
        }

        return testStep;
    }

    private TestCase getTestCase(SampleEvent sampleEvent) {
        TestCase testCase = findTestCase(sampleEvent.getThreadGroup());
        if(testCase == null) {
            testCase = new TestCase();
            testCase.setClassName(sampleEvent.getThreadGroup());
            testCases.add(testCase);
        }
        return testCase;
    }

    private TestCase findTestCase(String testCaseName) {
        for(TestCase testCase: testCases) {
            if(testCase.getClassName().equals(testCaseName))
                return testCase;
        }
        return null;
    }
}
