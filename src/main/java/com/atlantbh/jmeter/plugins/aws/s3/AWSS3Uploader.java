/*!
 * AtlantBH Custom Jmeter Components v1.0.0
 * http://www.atlantbh.com/jmeter-components/
 *
 * Copyright 2011, AtlantBH
 *
 * Licensed under the under the Apache License, Version 2.0.
 */

package com.atlantbh.jmeter.plugins.aws.s3;

import java.io.File;

import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

/**
 * AWSS3 sampler enable upload and download objects to Amazon S3 storage
 * service.
 * 
 * 
 * @author faruk pasalic
 * 
 */
public class AWSS3Uploader extends AbstractSampler {

	private static final Logger LOGGER = LoggerFactory.getLogger(AWSS3Uploader.class);

	private static final long serialVersionUID = 1L;

	private static final String OBJECT = "object";
	private static final String KEY = "key";
	private static final String SECRET = "secret";
	private static final String BUCKET = "bucket";
	private static final String DESTINATION = "destination";

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
			BasicAWSCredentials creds = new BasicAWSCredentials(getKey(), getSecret());
			AmazonS3 client = new AmazonS3Client(creds);

			client.putObject(getBucket(), getDestination(), new File(getObject()));

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

	public void setObject(String object) {
		setProperty(OBJECT, object);
	}

	public String getObject() {
		return getProperty(OBJECT).getStringValue();
	}

	public void setDestination(String destination) {
		setProperty(DESTINATION, destination);
	}

	public String getDestination() {
		return getProperty(DESTINATION).getStringValue();
	}

	public void setKey(String key) {
		setProperty(KEY, key);
	}

	public String getKey() {
		return getProperty(KEY).getStringValue();
	}

	public void setSecret(String secret) {
		setProperty(SECRET, secret);
	}

	public String getSecret() {
		return getProperty(SECRET).getStringValue();
	}

	public void setBucket(String bucket) {
		setProperty(BUCKET, bucket);
	}

	public String getBucket() {
		return getProperty(BUCKET).getStringValue();
	}

}
