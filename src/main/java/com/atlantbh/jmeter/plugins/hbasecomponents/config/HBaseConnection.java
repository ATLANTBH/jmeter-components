package com.atlantbh.jmeter.plugins.hbasecomponents.config;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.jmeter.testelement.TestListener;
import org.apache.jmeter.threads.JMeterVariables;

import org.apache.jmeter.config.ConfigElement;
import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.engine.event.LoopIterationEvent;

public class HBaseConnection  extends ConfigTestElement implements ConfigElement, TestListener {

	private static final long serialVersionUID = -2642777372269255604L;
	
	private static final String ZK_HOST = "ZK_HOST";
	private static final String ZK_NAME = "ZK_NAME";
	
	private static ConcurrentHashMap<String, HBaseConnectionVariable> pool = new ConcurrentHashMap<String, HBaseConnectionVariable>();
	
	
	//private String zkHost;
	//private String zkName;
	
	/*
	public HBaseConnectionVariable getConnection(String name) { 
		JMeterVariables vars = getThreadContext().getVariables();
		return pool.get(JMeterVarParser.parse(name, vars));
	}
	public void setConnection(String name, HBaseConnectionVariable conVar) {
		JMeterVariables vars = getThreadContext().getVariables();
		pool.put(JMeterVarParser.parse(name, vars), conVar);
	}
	*/
	
	public static HBaseConnectionVariable getConnection(String name) { 
		return pool.get(name);
	}
	
	public String getZkHost() {
		return getPropertyAsString(ZK_HOST);
	}
	
	public void setZkHost(String zkHost) {
		setProperty(ZK_HOST, zkHost);
	}
	
	public String getZkName() {
		return getPropertyAsString(ZK_NAME);
	}
	
	public void setZkName(String zkName) {
		setProperty(ZK_NAME, zkName);
	}
	
	public void testStarted(String s){testStarted();}
	
	public void testStarted(){
		JMeterVariables vars = getThreadContext().getVariables();
		String name = JMeterVarParser.parse(getZkName(), vars);
		
		if (pool.containsKey(name)){
			System.out.println("Test error: Multiple HBase connections called " + name);
			return;
		}
		
		pool.put(name, new HBaseConnectionVariable(JMeterVarParser.parse(getZkHost(), vars),name));
	}
	
	public void testEnded(String s){testEnded();}
	
	public void testEnded(){
		JMeterVariables vars = getThreadContext().getVariables();
		String name = JMeterVarParser.parse(getZkName(), vars);
		
		if (!pool.containsKey(name)){
			System.out.println("Test warning: HBase connection already cleared: " + name);
			return;
		}		
		pool.remove(name);
	}
	
	public void testIterationStart(LoopIterationEvent event) {
	}
	
}
