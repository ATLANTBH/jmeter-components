package com.atlantbh.jmeter.plugins.dummy.gui;

import com.atlantbh.jmeter.plugins.dummy.DummySampler;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;

public class DummySamplerGui extends AbstractSamplerGui {
	private static final long serialVersionUID = 1L;
	private JCheckBox isSuccessful;
	private JTextField responseCode;
	private JTextField responseMessage;
	private JTextArea responseData;

	public DummySamplerGui() {
		init();
	}

	public String getStaticLabel() {
		return "Dummy Sampler";
	}

	public void configure(TestElement element) {
		super.configure(element);

		this.isSuccessful.setSelected(element.getPropertyAsBoolean(DummySampler.IS_SUCCESSFUL));
		this.responseCode.setText(element.getPropertyAsString(DummySampler.RESPONSE_CODE));
		this.responseMessage.setText(element.getPropertyAsString(DummySampler.RESPONSE_MESSAGE));
		this.responseData.setText(element.getPropertyAsString(DummySampler.RESPONSE_DATA));
	}

	public TestElement createTestElement() {
		DummySampler sampler = new DummySampler();
		modifyTestElement(sampler);
		return sampler;
	}

	public void modifyTestElement(TestElement sampler) {
		super.configureTestElement(sampler);

		if ((sampler instanceof DummySampler)) {
			DummySampler dummySampler = (DummySampler) sampler;
			dummySampler.setSuccessful(this.isSuccessful.isSelected());
			dummySampler.setResponseCode(this.responseCode.getText());
			dummySampler.setResponseMessage(this.responseMessage.getText());
			dummySampler.setResponseData(this.responseData.getText());
		}
	}

	public void clearGui() {
		super.clearGui();

		this.isSuccessful.setSelected(true);
		this.responseCode.setText("200");
		this.responseMessage.setText("OK");
		this.responseData.setText("");
	}

	public String getLabelResource() {
		return getClass().getSimpleName();
	}

	private void init() {
		setLayout(new BorderLayout(0, 5));
		setBorder(makeBorder());

		add(makeTitlePanel(), "North");

		VerticalPanel mainPanel = new VerticalPanel();

		this.isSuccessful = new JCheckBox(DummySampler.IS_SUCCESSFUL);
		mainPanel.add(this.isSuccessful);

		this.responseCode = new JTextField();
		mainPanel.add(this.responseCode);

		this.responseMessage = new JTextField();
		mainPanel.add(this.responseMessage);

		this.responseData = new JTextArea();
		mainPanel.add(this.responseData);

		add(mainPanel, "Center");
	}
}