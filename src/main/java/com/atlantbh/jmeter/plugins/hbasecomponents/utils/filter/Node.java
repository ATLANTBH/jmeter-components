package com.atlantbh.jmeter.plugins.hbasecomponents.utils.filter;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {

	private List<Node> childs;

	public void setChilds(List<Node> childs) {
		this.childs = childs;
	}

	public List<Node> getChilds() {
		return childs;
	}

	public void addChild(Node child) {
		if (childs == null) {
			childs = new ArrayList<Node>();
		}
		childs.add(child);
	}
}
