package com.atlantbh.jmeter.plugins.dummy;

import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;

public class DummySampler extends AbstractSampler {
	private static final long serialVersionUID = 1L;
	public static String IS_SUCCESSFUL = "Successful";
	public static String RESPONSE_CODE = "Response Code (e.g. 200)";
	public static String RESPONSE_MESSAGE = "Response Message (e.g. OK)";
	public static String RESPONSE_DATA = "Response Data";

	public SampleResult sample(Entry e) {
		SampleResult res = new SampleResult();
		res.setSampleLabel(getName());

		res.sampleStart();

		res.setSamplerData(getResponseData());

		res.setResponseCode(getResponseCode());
		res.setResponseMessage(getResponseMessage());
		res.setSuccessful(isSuccessfull());

		res.setDataType("text");
		res.setResponseData(getResponseData().getBytes());

		res.sampleEnd();

		return res;
	}

	public void setSuccessful(boolean selected) {
		setProperty(IS_SUCCESSFUL, selected);
	}

	public void setResponseCode(String text) {
		setProperty(RESPONSE_CODE, text);
	}

	public void setResponseMessage(String text) {
		setProperty(RESPONSE_MESSAGE, text);
	}

	public void setResponseData(String text) {
		setProperty(RESPONSE_DATA, text);
	}

	public boolean isSuccessfull() {
		return getPropertyAsBoolean(IS_SUCCESSFUL);
	}

	public String getResponseCode() {
		return getPropertyAsString(RESPONSE_CODE);
	}

	public String getResponseMessage() {
		return getPropertyAsString(RESPONSE_MESSAGE);
	}

	public String getResponseData() {
		return getPropertyAsString(RESPONSE_DATA);
	}
}