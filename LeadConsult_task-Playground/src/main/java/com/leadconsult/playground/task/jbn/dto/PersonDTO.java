package com.leadconsult.playground.task.jbn.dto;

import java.time.LocalDate;

public class PersonDTO {
	private Integer id = null;
	private String name = "";
	
	private LocalDate birthDate = null;
	private Integer groupId = null;

	public PersonDTO (Integer id, String name, LocalDate birthDate, Integer groupId) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.groupId = groupId;
	}

	public PersonDTO() {
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

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	
}
