package com.atlantbh.jmeter.plugins.jmstools;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

public class BinaryMessageConverter implements MessageConverter{

	
	private Map<String, String> messageProperties;
	
	public Map<String, String> getMessageProperties() {
		return messageProperties;
	}

	public void setMessageProperties(Map<String, String> messageProperties) {
		this.messageProperties = messageProperties;
	}

	@Override
	public Object fromMessage(Message arg0) throws JMSException, MessageConversionException {
		
		Enumeration<String> names = arg0.getPropertyNames();
		messageProperties = new HashMap<String, String>();
		while(names.hasMoreElements()){
			String name = names.nextElement();
			messageProperties.put(name, arg0.getStringProperty(name));
		}
		
		BytesMessage bm = (BytesMessage) arg0;
    	byte[] transfer = new byte[(int) bm.getBodyLength()];
    	bm.readBytes(transfer);
    	return new String(transfer);
	}

	@Override
	public Message toMessage(Object arg0, Session session) throws JMSException, MessageConversionException {
		BytesMessage msg = session.createBytesMessage();
		msg.writeBytes(arg0.toString().getBytes());
		return msg;
	}
}
