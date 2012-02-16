package com.atlantbh.jmeter.plugins.hbasecomponents.utils.filter;

public class ExpressionNode extends Node {
	private String columnFamily;
	private String qualifier;
	private String value;
	private String oper;

	public String getColumnFamily() {
		return columnFamily;
	}

	public void setColumnFamily(String columnFamily) {
		this.columnFamily = columnFamily;
	}

	public String getQualifier() {
		return qualifier;
	}

	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getOper() {
		return oper;
	}
}