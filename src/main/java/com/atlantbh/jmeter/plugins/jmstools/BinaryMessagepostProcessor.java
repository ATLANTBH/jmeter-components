package com.atlantbh.jmeter.plugins.jmstools;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.jms.core.MessagePostProcessor;

public class BinaryMessagepostProcessor implements MessagePostProcessor{

	private Map<String, String> messageProperties = new HashMap<String, String>();

	public Map<String, String> getMessageProperties() {
		return messageProperties;
	}

	public void setMessageProperties(Map<String, String> messageProperties) {
		this.messageProperties = messageProperties;
	}

	@Override
	public Message postProcessMessage(Message message) throws JMSException {
		if(message != null ){
			for(String key : messageProperties.keySet()){
				message.setStringProperty(key, messageProperties.get(key));
			}
			return message;
		}
		return null;
	}
	
}
