package com.leadconsult.playground.task.jbn.dto;

import com.leadconsult.playground.task.jbn.entities.Group;

public class GroupDTO {
	public static GroupDTO fromEntity(Group entity) {
		return new GroupDTO(entity.getId(), entity.getName());
	}

	public static void fillEntity (GroupDTO dto, Group entity) {
		entity.setName (dto.getName());
		// don't touch id
	}

	private Integer id = null;
	private String name = "";

	public GroupDTO (Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public GroupDTO() {
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
