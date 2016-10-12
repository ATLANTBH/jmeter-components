package com.atlantbh.jmeter.plugins.aws.s3.gui;

import com.atlantbh.jmeter.plugins.aws.s3.AWSS3Uploader;
import org.apache.jmeter.testelement.TestElement;

public class AWSS3UploaderGUI extends BaseS3GUISampler {

	private static final long serialVersionUID = 1L;

	public AWSS3UploaderGUI() {
		super(true, true);
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
			u.setDestination(ltfDestination.getText());
			u.setObject(ltfFile.getText());
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
	public String getLabelResource() {
		return "S3 Uploader";
	}

	@Override
	public String getStaticLabel() {
		return "S3 Uploader";
	}

}
