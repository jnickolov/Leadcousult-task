package com.leadconsult.playground.task.jbn.dto;

public class IDNameDTO {
	private Integer id = null;
	private String name = "";

	public IDNameDTO (Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public IDNameDTO() {
		super();
	}

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
