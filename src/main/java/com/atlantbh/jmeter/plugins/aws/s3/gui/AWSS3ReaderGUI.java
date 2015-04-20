package com.atlantbh.jmeter.plugins.aws.s3.gui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;

import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.gui.JLabeledTextField;

import com.atlantbh.jmeter.plugins.aws.s3.AWSS3Reader;

public class AWSS3ReaderGUI extends AbstractSamplerGui {

	private static final long serialVersionUID = 1L;
	private JLabeledTextField ltfAwsKey = null;
	private JLabeledTextField ltfAwsSecret = null;
	private JLabeledTextField ltfAwsBucket = null;
	private JLabeledTextField ltfObject = null;

	public AWSS3ReaderGUI() {
		super();
		this.init();
	}

	private void init() {
		setLayout(new BorderLayout());
		setBorder(makeBorder());
		add(makeTitlePanel(), BorderLayout.NORTH);

		VerticalPanel panel = new VerticalPanel();
		panel.setBorder(BorderFactory.createEtchedBorder());

		ltfAwsKey = new JLabeledTextField("AWS Key");
		ltfAwsSecret = new JLabeledTextField("AWS Secret");
		ltfAwsBucket = new JLabeledTextField("AWS Bucket");
		ltfObject = new JLabeledTextField("AWS Object");

		panel.add(ltfAwsKey);
		panel.add(ltfAwsSecret);
		panel.add(ltfAwsBucket);
		panel.add(ltfObject);
		add(panel, BorderLayout.CENTER);
	}

	@Override
	public TestElement createTestElement() {
		AWSS3Reader reader = new AWSS3Reader();
		modifyTestElement(reader);
		return reader;
	}

	@Override
	public void modifyTestElement(TestElement element) {
		super.configureTestElement(element);
		if (element instanceof AWSS3Reader) {
			AWSS3Reader d = (AWSS3Reader) element;
			d.setKey(ltfAwsKey.getText());
			d.setSecret(ltfAwsSecret.getText());
			d.setBucket(ltfAwsBucket.getText());
			d.setObject(this.ltfObject.getText());
		}
	}

	@Override
	public void configure(TestElement element) {
		super.configure(element);
		if (element instanceof AWSS3Reader) {
			AWSS3Reader d = (AWSS3Reader) element;
			ltfAwsKey.setText(d.getKey());
			ltfAwsSecret.setText(d.getSecret());
			ltfAwsBucket.setText(d.getBucket());
			ltfObject.setText(d.getObject());
		}
	}

	@Override
	public void clearGui() {
		super.clearGui();
		ltfAwsKey.setText("");
		ltfAwsSecret.setText("");
		ltfAwsBucket.setText("");
		ltfObject.setText("");
	}

	@Override
	public String getLabelResource() {
		return "AWS S3 Reader";
	}

	@Override
	public String getStaticLabel() {
		return "AWS S3 Reader";
	}

}
