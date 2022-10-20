package com.leadconsult.playground.task.jbn.dto;

import java.time.LocalDate;

import org.springframework.lang.NonNull;

import com.leadconsult.playground.task.jbn.entities.Teacher;

public class TeacherDTO extends PersonDTO {
	public static TeacherDTO fromEntity (@NonNull Teacher entity) {
		return new TeacherDTO (entity.getId(), entity.getName(), entity.getBirthDate(), 
				entity.getGroup() != null ? entity.getGroup().getId() : null, 
				entity.getSubjects());
	}

	private String subjects = "";
	
	public TeacherDTO() {
		super();
	}

	public TeacherDTO (Integer id, String name, LocalDate birthDate, Integer groupId, String subjects) {
		super(id, name, birthDate, groupId);
		this.subjects = subjects;
	}

	public String getSubjects() {
		return subjects;
	}

	public void setSubjects(String subjects) {
		this.subjects = subjects;
	}
}
