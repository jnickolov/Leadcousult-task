package com.leadconsult.playground.task.jbn.dto;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;

import com.leadconsult.playground.task.jbn.entities.Person;

public class GroupWithMembers extends GroupDTO {
	private List<IDNameDTO> members = new LinkedList<>();

	public GroupWithMembers() {
		super();
	}

	public GroupWithMembers(Integer id, String name) {
		super (id, name);
	}

	public List<IDNameDTO> getMembers() {
		return members;
	}

	public void setMembers(List<IDNameDTO> members) {
		this.members = members;
	}

	public void fillMembers (@NonNull List<Person> members) {
		List<IDNameDTO> lst = members.stream()
				.map (p -> new IDNameDTO (p.getId(), p.getName()))
				.collect (Collectors.toList());
		
		this.setMembers (lst);
	}

}
