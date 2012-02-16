package com.atlantbh.jmeter.plugins.hbasecomponents.utils;

import java.util.Map.Entry;
import java.util.NavigableMap;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.jmeter.threads.JMeterVariables;

public class Row2XML {
	public static String row2xmlstring(Result result, JMeterVariables vars, int index) {
		StringBuilder xml = new StringBuilder();
		
		NavigableMap<byte[], NavigableMap<byte[], byte[]>> map = result.getNoVersionMap();

		
		xml.append("<row>\n");
		for(byte[] fam : map.keySet()) {
			String family = new String(fam);
			
			xml.append(" <family name=\"" + family + "\">\n");
			
			NavigableMap<byte[], byte[]> famMap = result.getFamilyMap(fam);
			for(Entry<byte[] , byte[]> e  : famMap.entrySet()) {
				String key = new String(e.getKey());
				String value = new String(e.getValue());
				xml.append("  <column name=\"" + key + "\" value=\"" + value + "\"/>\n");
				
				if (vars != null) {
					
					if (index != 0) {
						vars.put(family + ":" + key + "_" + index, value);
					}
					else {
						vars.put(family + ":" + key, value);
					}
				}
			}
			xml.append(" </family>\n");
			
		}
		xml.append("</row>\n");
		
		return xml.toString();
	}
	
	public static String row2xmlStringLatest(Result result, JMeterVariables vars, int index)
	{
		StringBuilder xml = new StringBuilder();

		NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> map = result.getMap();

		long maxts = Long.MIN_VALUE;
		for (KeyValue kv : result.list()) {
			if (maxts < kv.getTimestamp()) {
				maxts = kv.getTimestamp();
			}
		}

		xml.append("<row>\n");
		for (byte[] fam : map.keySet()) {
			String family = new String(fam);

			xml.append(" <family name=\"" + family + "\">\n");

			NavigableMap<byte[], NavigableMap<Long, byte[]>> qualifierMap = map.get(fam);
			
			for (Entry<byte[], NavigableMap<Long, byte[]>> e : qualifierMap.entrySet()) {
				NavigableMap<Long, byte[]> valueMap = e.getValue();
				byte[] value = valueMap.get(Long.valueOf(maxts));
				
				if (value != null) {
					String key = new String(e.getKey());
					String strValue = new String(value);
					xml.append("  <column name=\"" + key + "\" value=\"" + strValue.toString() + "\"/>\n");

					if (vars != null) {

						if (index != 0) {
							vars.put(family + ":" + key + "_" + index, strValue.toString());
						} else {
							vars.put(family + ":" + key, strValue.toString());
						}
					}
				}
			}
			xml.append(" </family>\n");

		}
		xml.append("</row>\n");

		return xml.toString();
	}
}
