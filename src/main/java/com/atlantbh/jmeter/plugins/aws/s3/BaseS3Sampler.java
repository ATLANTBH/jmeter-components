package com.atlantbh.jmeter.plugins.aws.s3;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import org.apache.jmeter.samplers.AbstractSampler;

/**
 * Base S3 sampler, implements S3 client creation.
 *
 * @author jbegic
 */
public abstract class BaseS3Sampler extends AbstractSampler {
	private static final String KEY = "key";
	private static final String SECRET = "secret";
	private static final String ENDPOINT = "endpoint";
	private static final String BUCKET = "bucket";
	private static final String OBJECT = "object";
	private static final String DESTINATION = "destination";

	private AmazonS3Client s3Client;

	/**
	 * Gets S3 client.
	 *
	 * @return the S3 client
	 */
	protected synchronized AmazonS3Client getS3Client() {
		if (s3Client == null) {
			String key = getPropertyAsString(KEY);
			String secret = getPropertyAsString(SECRET);
			String endpoint = getPropertyAsString(ENDPOINT);

			BasicAWSCredentials credentials = new BasicAWSCredentials(key, secret);
			s3Client = new AmazonS3Client(credentials);

			// Set endpoint if provided
			if (endpoint != null) {
				s3Client.setEndpoint(endpoint);
				s3Client.setS3ClientOptions(new S3ClientOptions().withPathStyleAccess(true));
			}
		}

		return s3Client;
	}

	/**
	 * Gets key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return getPropertyAsString(KEY);
	}

	/**
	 * Gets secret.
	 *
	 * @return the secret
	 */
	public String getSecret() {
		return getPropertyAsString(SECRET);
	}

	/**
	 * Gets bucket.
	 *
	 * @return the bucket
	 */
	public String getBucket() {
		return getPropertyAsString(BUCKET);
	}

	/**
	 * Gets object.
	 *
	 * @return the object
	 */
	public String getObject() {
		return getPropertyAsString(OBJECT);
	}

	/**
	 * Gets destination.
	 *
	 * @return the destination
	 */
	public String getDestination() {
		return getPropertyAsString(DESTINATION);
	}

	/**
	 * Sets key.
	 *
	 * @param key the key
	 */
	public void setKey(String key) {
		setProperty(KEY, key);
	}

	/**
	 * Sets secret.
	 *
	 * @param secret the secret
	 */
	public void setSecret(String secret) {
		setProperty(SECRET, secret);
	}

	/**
	 * Sets bucket.
	 *
	 * @param bucket the bucket
	 */
	public void setBucket(String bucket) {
		setProperty(BUCKET, bucket);
	}

	/**
	 * Sets endpoint.
	 *
	 * @param endpoint the endpoint
	 */
	public void setEndpoint(String endpoint) {
		setProperty(ENDPOINT, endpoint);
	}

	/**
	 * Sets destination.
	 *
	 * @param destination the destination
	 */
	public void setDestination(String destination) {
		setProperty(DESTINATION, destination);
	}

	/**
	 * Sets object.
	 *
	 * @param object the object
	 */
	public void setObject(String object) {
		setProperty(OBJECT, object);
	}
}
