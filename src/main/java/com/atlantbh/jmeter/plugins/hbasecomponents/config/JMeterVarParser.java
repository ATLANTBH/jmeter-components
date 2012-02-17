package com.atlantbh.jmeter.plugins.hbasecomponents.config;

import java.util.regex.*;
import org.apache.jmeter.threads.JMeterVariables;

public class JMeterVarParser {
	private static Pattern pattern = Pattern.compile("\\$\\{\\w+\\}");
	
	public static String parse(String format, JMeterVariables vars){
		Matcher matcher = pattern.matcher(format);
		StringBuffer result = new StringBuffer();
		String s;
		
		while (matcher.find())    		
			if ((s = vars.get(format.substring(matcher.start()+2, matcher.end()-1))) != null)
				matcher.appendReplacement(result, s);
			else
				System.out.println("Undefined variable: " + matcher.group());

		matcher.appendTail(result);
		return result.toString();
	}
}