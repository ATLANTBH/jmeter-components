package com.atlantbh.jmeter.plugins.jsonutils.jsonformatter;

import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.samplers.SampleResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class JSONFormatterTest {
	private JSONFormatter formatter = new JSONFormatter();

	@Mock
	private JMeterContext context;

	@Mock
	private SampleResult sampleResult;

	@Before
	public void setUp() throws Exception {
		formatter.setThreadContext(context);
	}

	@Test
	public void shouldProcessJSONObjectString() throws Exception {
		when(context.getPreviousResult()).thenReturn(sampleResult);
		String json = "{\"foo\":\"bar\"}";

		when(sampleResult.getResponseDataAsString()).thenReturn(json);

		// the method under test
		formatter.process();

		ArgumentCaptor<byte[]> result = ArgumentCaptor.forClass(byte[].class);

		verify(sampleResult, times(1)).setResponseData(result.capture());

		String formattedJson = new String(result.getValue());
		formattedJson = formattedJson.replaceAll("\\s", "");

		// formatted json stripped of all whitespace should equal unformatted json
		assertEquals(json, formattedJson);
	}

	@Test
	public void shouldProcessJSONArrayString() throws Exception {
		when(context.getPreviousResult()).thenReturn(sampleResult);
		String json = "[\"foo\",\"bar\"]";

		when(sampleResult.getResponseDataAsString()).thenReturn(json);

		// the method under test
		formatter.process();

		ArgumentCaptor<byte[]> result = ArgumentCaptor.forClass(byte[].class);

		verify(sampleResult, times(1)).setResponseData(result.capture());

		String formattedJson = new String(result.getValue());
		formattedJson = formattedJson.replaceAll("\\s", "");

		// formatted json stripped of all whitespace should equal unformatted json
		assertEquals(json, formattedJson);
	}

	@Test
	public void shouldLetUnparseableJsonFallThrough() throws Exception {
		when(context.getPreviousResult()).thenReturn(sampleResult);
		String json = "<html><body><h1>Something bad happened</h1></body></html>";

		when(sampleResult.getResponseDataAsString()).thenReturn(json);

		// the method under test
		formatter.process();

		// should never have attempted to set the body
		verify(sampleResult, times(0)).setResponseData(any(byte[].class));
	}
}
