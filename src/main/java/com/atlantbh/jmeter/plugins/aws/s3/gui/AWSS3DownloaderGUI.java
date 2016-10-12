package com.atlantbh.jmeter.plugins.aws.s3.gui;

import com.atlantbh.jmeter.plugins.aws.s3.AWSS3Downloader;
import org.apache.jmeter.testelement.TestElement;

public class AWSS3DownloaderGUI extends BaseS3GUISampler {
	private static final long serialVersionUID = 1L;


	public AWSS3DownloaderGUI() {
		super(true, false);
	}
	@Override
	public TestElement createTestElement() {
		AWSS3Downloader downloader = new AWSS3Downloader();
		modifyTestElement(downloader);
		return downloader;
	}

	@Override
	public void modifyTestElement(TestElement element) {
		super.configureTestElement(element);
		if (element instanceof AWSS3Downloader) {
			AWSS3Downloader d = (AWSS3Downloader) element;
			d.setKey(ltfAwsKey.getText());
			d.setSecret(ltfAwsSecret.getText());
			d.setBucket(ltfAwsBucket.getText());
			d.setDestination(ltfDestination.getText());
			d.setObject(ltfObject.getText());
			d.setEndpoint(ltfAwsEndpoint.getText());
		}
	}

	@Override
	public void configure(TestElement element) {
		super.configure(element);
		if (element instanceof AWSS3Downloader) {
			AWSS3Downloader d = (AWSS3Downloader) element;
			ltfAwsKey.setText(d.getKey());
			ltfAwsSecret.setText(d.getSecret());
			ltfAwsBucket.setText(d.getBucket());
			ltfDestination.setText(d.getDestination());
			ltfObject.setText(d.getObject());
			ltfAwsEndpoint.setText(d.getEndpoint());
		}
	}


	@Override
	public String getLabelResource() {
		return "S3 Downloader";
	}

	@Override
	public String getStaticLabel() {
		return "S3 Downloader";
	}

}
