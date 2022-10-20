package com.leadconsult.playground.task.jbn.dto;

import java.util.LinkedList;
import java.util.List;

public class TeachersAndStudentsDTO {
	private final List<StudentDTO> students;
	private final List<TeacherDTO> teachers;

	public TeachersAndStudentsDTO (List<TeacherDTO> teachers, List<StudentDTO> students) {
		this.teachers = teachers;
		this.students = students;
	}

	public List<StudentDTO> getStudents() {
		return students;
	}

	public List<TeacherDTO> getTeachers() {
		return teachers;
	}
}
