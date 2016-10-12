package com.atlantbh.jmeter.plugins.aws.s3.gui;

import com.atlantbh.jmeter.plugins.aws.s3.AWSS3Reader;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.gui.JLabeledTextField;

public class AWSS3ReaderGUI extends BaseS3GUISampler {

	private static final long serialVersionUID = 1L;
	private JLabeledTextField ltfAwsKey = null;
	private JLabeledTextField ltfAwsSecret = null;
	private JLabeledTextField ltfAwsBucket = null;
	private JLabeledTextField ltfObject = null;

	public AWSS3ReaderGUI() {
		super(false);
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
			d.setObject(ltfObject.getText());
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
	public String getLabelResource() {
		return "S3 Reader";
	}

	@Override
	public String getStaticLabel() {
		return "S3 Reader";
	}

}
