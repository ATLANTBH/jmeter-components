package com.atlantbh.jmeter.plugins.aws.s3.gui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;

import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.gui.JLabeledTextField;

import com.atlantbh.jmeter.plugins.aws.s3.AWSS3Uploader;

public class AWSS3UploaderGUI extends AbstractSamplerGui {

	private static final long serialVersionUID = 1L;
	private JLabeledTextField ltfAwsKey = null;
	private JLabeledTextField ltfAwsSecret = null;
	private JLabeledTextField ltfAwsBucket = null;
	private JLabeledTextField ltfFile = null;
	private JLabeledTextField ltfDestination = null;

	public AWSS3UploaderGUI() {
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
		ltfFile = new JLabeledTextField("File");
		ltfDestination = new JLabeledTextField("S3 Destination");

		panel.add(ltfAwsKey);
		panel.add(ltfAwsSecret);
		panel.add(ltfAwsBucket);
		panel.add(ltfFile);
		panel.add(ltfDestination);
		add(panel, BorderLayout.CENTER);
	}

	@Override
	public TestElement createTestElement() {
		AWSS3Uploader uploader = new AWSS3Uploader();
		modifyTestElement(uploader);
		return uploader;
	}

	@Override
	public void modifyTestElement(TestElement element) {
		super.configureTestElement(element);
		if (element instanceof AWSS3Uploader) {
			AWSS3Uploader u = (AWSS3Uploader) element;
			u.setKey(ltfAwsKey.getText());
			u.setSecret(ltfAwsSecret.getText());
			u.setBucket(ltfAwsBucket.getText());
			u.setDestination(this.ltfDestination.getText());
			u.setObject(this.ltfFile.getText());
		}
	}

	@Override
	public void configure(TestElement element) {
		super.configure(element);
		if (element instanceof AWSS3Uploader) {
			AWSS3Uploader u = (AWSS3Uploader) element;
			ltfAwsKey.setText(u.getKey());
			ltfAwsSecret.setText(u.getSecret());
			ltfAwsBucket.setText(u.getBucket());
			ltfDestination.setText(u.getDestination());
			ltfFile.setText(u.getObject());
		}
	}

	@Override
	public void clearGui() {
		super.clearGui();
		ltfAwsKey.setText("");
		ltfAwsSecret.setText("");
		ltfAwsBucket.setText("");
		ltfFile.setText("");
		ltfDestination.setText("");
	}

	@Override
	public String getLabelResource() {
		return "AWS S3 Uploader";
	}

	@Override
	public String getStaticLabel() {
		return "AWS S3 Uploader";
	}

}
