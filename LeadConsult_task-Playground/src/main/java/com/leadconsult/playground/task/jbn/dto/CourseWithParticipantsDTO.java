package com.leadconsult.playground.task.jbn.dto;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;

import com.leadconsult.playground.task.jbn.entities.Person;

public class CourseWithParticipantsDTO extends CourseDTO {
	private List<IDNameDTO> participants = new LinkedList<>();

	public CourseWithParticipantsDTO() {
		super();
	}

	public CourseWithParticipantsDTO (Integer id, String name,boolean ismain) {
		super (id, name,ismain);
	}

	public List<IDNameDTO> getParticipants() {
		return participants;
	}

	public void setParticipants (List<IDNameDTO> participants) {
		this.participants = participants;
	}
	
	public void fillParticipants (@NonNull List<Person> participants) {
		List<IDNameDTO> lst = participants.stream()
				.map (p -> new IDNameDTO (p.getId(), p.getName()))
				.collect (Collectors.toList());
		
		this.setParticipants (lst);
	}
}
