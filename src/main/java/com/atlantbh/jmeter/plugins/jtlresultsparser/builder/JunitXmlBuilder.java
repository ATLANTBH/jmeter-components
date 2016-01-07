package com.atlantbh.jmeter.plugins.jtlresultsparser.builder;

import com.atlantbh.jmeter.plugins.jtlresultsparser.model.junit.TestCase;
import com.atlantbh.jmeter.plugins.jtlresultsparser.model.junit.TestStep;
import com.atlantbh.jmeter.plugins.jtlresultsparser.model.junit.TestSuite;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by adnan on 07/01/16.
 */
public class JunitXmlBuilder {

    private static JunitXmlBuilder builder = null;

    private JunitXmlBuilder() {
        super();
    }

    public static JunitXmlBuilder newInstance() {
        if (builder == null) {
            builder = new JunitXmlBuilder();
        }
        return builder;
    }

    public Document generateXmlDoc(TestSuite testSuite) {
        Document doc = null;

        try {
            doc = getDocument(doc);
            Element testSuitesElement = doc.createElement("testsuites");
            doc.appendChild(testSuitesElement);

            Element testSuiteElement = createTestSuiteElement(doc, testSuite);
            testSuitesElement.appendChild(testSuiteElement);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
        return doc;
    }

    private Document getDocument(Document doc) throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        doc = docBuilder.newDocument();
        return doc;
    }

    public void writeXmlDoc(Document doc, String output) {
        try {
            Transformer transformer = getTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(output));
            transformer.transform(source, result);
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    private Transformer getTransformer() throws TransformerConfigurationException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        return transformer;
    }

    private void appendTestCaseElements(ArrayList<Element> testCaseElements, Element testSuiteElement) {
        for (Element testCaseElement : testCaseElements) {
            testSuiteElement.appendChild(testCaseElement);
        }
    }

    private Element createTestSuiteElement(Document doc, TestSuite testSuite) {
        Element testSuiteElement = doc.createElement("testsuite");
        setTestSuiteAttributes(testSuiteElement, testSuite);
        testSuiteElement.appendChild(createTestSuiteProperties(doc)); // potentially not needed, if properties nodes are not needed

        for (TestCase testCase : testSuite.getTestCases()) {
            ArrayList<Element> testCaseElements = createTestCaseElements(doc, testCase);
            appendTestCaseElements(testCaseElements, testSuiteElement);
        }

        return testSuiteElement;
    }

    private ArrayList<Element> createTestCaseElements(Document doc, TestCase testCase) {
        ArrayList<Element> testCaseElements = new ArrayList<Element>();

        for (TestStep testStep : testCase.getTestSteps()) {
            Element testCaseElement = doc.createElement("testcase");
            testCaseElement.setAttribute("classname", testCase.getClassName());
            testCaseElement.setAttribute("name", testStep.getName());
            testCaseElement.setAttribute("time", testStep.getTime());

            for (Map.Entry<String, String> entry : testStep.getAssertionFailuresList().entrySet()) {

                Element failureElement = doc.createElement("failure");
                failureElement.setAttribute("message", entry.getKey());
                if (!entry.getValue().equals(""))
                    failureElement.appendChild(doc.createTextNode(entry.getValue()));
                testCaseElement.appendChild(failureElement);
            }

            testCaseElements.add(testCaseElement);
        }

        return testCaseElements;
    }

    private Element createTestSuiteProperties(Document doc) {
        Element properties = doc.createElement("properties");

        Element vendorProperty = doc.createElement("property");
        vendorProperty.setAttribute("name", "java.vendor");
        vendorProperty.setAttribute("value", "Sun Microsystems Inc.");
        properties.appendChild(vendorProperty);

        Element compilerProperty = doc.createElement("property");
        compilerProperty.setAttribute("name", "compiler.debug");
        compilerProperty.setAttribute("value", "on");
        properties.appendChild(compilerProperty);

        Element projectProperty = doc.createElement("property");
        projectProperty.setAttribute("name", "project.jdk.classpath");
        projectProperty.setAttribute("value", "jdk.classpath.1.6");
        properties.appendChild(projectProperty);

        return properties;
    }

    private void setTestSuiteAttributes(Element element, TestSuite testSuite) {
        element.setAttribute("name", testSuite.getName());
        element.setAttribute("tests", testSuite.getTests());
        element.setAttribute("failures", testSuite.getFailures());
        element.setAttribute("time", testSuite.getTime());
        element.setAttribute("timestamp", new Timestamp(new Date().getTime()).toString()); // Placeholder for timestamp attribute
        element.setAttribute("errors", "0"); // We do not have 'errors' attribute in the current Junit Model
        element.setAttribute("skipped", "0"); // We do not have 'skipped' attribute in the current Junit Model
    }
}
