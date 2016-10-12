/*!
 * AtlantBH Custom Jmeter Components v1.0.0
 * http://www.atlantbh.com/jmeter-components/
 *
 * Copyright 2011, AtlantBH
 *
 * Licensed under the under the Apache License, Version 2.0.
 */

package com.atlantbh.jmeter.plugins.aws.s3;

import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * AWSS3 sampler enable upload and download objects to Amazon S3 storage
 * service.
 *
 *
 * @author faruk pasalic
 *
 */
public class AWSS3Uploader extends BaseS3Sampler {

	private static final Logger LOGGER = LoggerFactory.getLogger(AWSS3Uploader.class);

	private static final long serialVersionUID = 1L;

	public AWSS3Uploader() {
		super();
	}

	@Override
	public SampleResult sample(Entry arg0) {
		LOGGER.info("Upload started....");
		SampleResult result = new SampleResult();
		result.setSampleLabel(getName());
		result.setDataType(SampleResult.TEXT);
		result.sampleStart();
		try {

			getS3Client().putObject(getBucket(), getDestination(), new File(getObject()));

			LOGGER.info("Upload finished.");
			result.setResponseData("Upload finished".getBytes());
			result.setSuccessful(!false);
			result.setResponseCode("200");
			result.setResponseMessage("Uploaded");
		} catch (Exception e) {
			LOGGER.info("Upload error.");
			result.setResponseData(("Upload error: " + e.getMessage()).getBytes());
			result.setSuccessful(false);
			result.setResponseCode("500");
			result.setResponseMessage("Error");
		}
		result.sampleEnd();
		return result;
	}

}
