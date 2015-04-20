/*!
 * AtlantBH Custom Jmeter Components v1.0.0
 * http://www.atlantbh.com/jmeter-components/
 *
 * Copyright 2011, AtlantBH
 *
 * Licensed under the under the Apache License, Version 2.0.
 */

package com.atlantbh.jmeter.plugins.aws.s3;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectId;

/**
 * AWSS3 sampler enable upload and download objects to Amazon S3 storage
 * service.
 * 
 * 
 * @author faruk pasalic
 * 
 */
public class AWSS3Reader extends AbstractSampler {

	private static final Logger LOGGER = LoggerFactory.getLogger(AWSS3Reader.class);

	private static final long serialVersionUID = 1L;

	private static final String OBJECT = "object";
	private static final String KEY = "key";
	private static final String SECRET = "secret";
	private static final String BUCKET = "bucket";

	public AWSS3Reader() {
		super();
	}

	@Override
	public SampleResult sample(Entry arg0) {
		LOGGER.info("Read started....");
		SampleResult result = new SampleResult();
		result.setSampleLabel(getName());
		result.setDataType(SampleResult.TEXT);
		result.sampleStart();
		try {
			BasicAWSCredentials creds = new BasicAWSCredentials(getKey(), getSecret());
			AmazonS3 client = new AmazonS3Client(creds);
			S3Object s3Object = client.getObject(new GetObjectRequest(new S3ObjectId(getBucket(), getObject())));
			InputStream is = s3Object.getObjectContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			
			String line;
			StringBuilder sb = new StringBuilder();
			while((line = reader.readLine() ) != null){
				sb.append(line);
			}
			result.setResponseData(sb.toString().getBytes());
			reader.close();
			LOGGER.info("Read finished.");
			result.setSuccessful(!false);
			result.setResponseCode("200");
			result.setResponseMessage("Read done");
			result.setContentType("text/plain");
		} catch (Exception e) {
			LOGGER.info("Read error.");
			result.setResponseData(("Read error: " + e.getMessage()).getBytes());
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
