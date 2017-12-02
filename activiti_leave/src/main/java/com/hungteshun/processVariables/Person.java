package com.hungteshun.processVariables;

import java.io.Serializable;

public class Person implements Serializable{
	
	private static final long serialVersionUID = -3358785238495082748L;
	
	private Integer id;//编号
	private String name;//姓名
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
