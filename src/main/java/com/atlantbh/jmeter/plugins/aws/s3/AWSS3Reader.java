/*!
 * AtlantBH Custom Jmeter Components v1.0.0
 * http://www.atlantbh.com/jmeter-components/
 *
 * Copyright 2011, AtlantBH
 *
 * Licensed under the under the Apache License, Version 2.0.
 */

package com.atlantbh.jmeter.plugins.aws.s3;

import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectId;
import org.apache.commons.io.IOUtils;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * AWSS3 sampler enable upload and download objects to Amazon S3 storage
 * service.
 *
 *
 * @author faruk pasalic
 *
 */
public class AWSS3Reader extends BaseS3Sampler {

	private static final Logger LOGGER = LoggerFactory.getLogger(AWSS3Reader.class);

	private static final long serialVersionUID = 1L;

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
		InputStream is = null;
		try {
			S3Object s3Object = getS3Client().getObject(new GetObjectRequest(new S3ObjectId(getBucket(), getObject())));
			is = s3Object.getObjectContent();
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
		} finally {
			IOUtils.closeQuietly(is);
		}
		result.sampleEnd();
		return result;
	}
}
