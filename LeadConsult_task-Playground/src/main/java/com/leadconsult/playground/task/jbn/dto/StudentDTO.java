package com.leadconsult.playground.task.jbn.dto;

import java.time.LocalDate;

import org.springframework.lang.NonNull;

import com.leadconsult.playground.task.jbn.entities.Student;

public class StudentDTO extends PersonDTO {
	public static StudentDTO fromEntity (@NonNull Student entity) {
		return new StudentDTO (entity.getId(), entity.getName(), entity.getBirthDate(), 
				entity.getGroup() != null ? entity.getGroup().getId() : null, 
				entity.getGrade());
	}
	
	private int grade = 0;

	public StudentDTO() {
		super();
	}

	public StudentDTO (Integer id, String name, LocalDate birthDate, Integer groupId, int grade) {
		super (id, name, birthDate, groupId);
		this.grade = grade;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
	
}
