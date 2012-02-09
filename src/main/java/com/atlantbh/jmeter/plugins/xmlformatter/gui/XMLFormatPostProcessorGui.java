package com.atlantbh.jmeter.plugins.xmlformatter.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.processor.gui.AbstractPostProcessorGui;
import org.apache.jmeter.testelement.TestElement;

import com.atlantbh.jmeter.plugins.xmlformatter.XMLFormatPostProcessor;

public class XMLFormatPostProcessorGui extends AbstractPostProcessorGui 
{

	private static final long serialVersionUID = 2058383675974452993L;

	public XMLFormatPostProcessorGui() {
		init();
	}

	private void init() {
        setBorder(makeBorder());
        setLayout(new BorderLayout());
        JPanel vertPanel = new VerticalPanel();
        vertPanel.add(makeTitlePanel());
        
        add(vertPanel, BorderLayout.NORTH);
	}

	public TestElement createTestElement() {
		XMLFormatPostProcessor te = new XMLFormatPostProcessor();
		modifyTestElement(te);
		return te;
	}

	public String getLabelResource() {
		return "xml_format_post_processor_label";
	}
	
	public String getStaticLabel()
	{
		return "XML Format Post Processor";	
	}
	
	public void modifyTestElement(TestElement te) {
		configureTestElement(te);
	}


}
