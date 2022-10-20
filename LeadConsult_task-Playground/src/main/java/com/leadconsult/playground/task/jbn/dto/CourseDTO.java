package com.leadconsult.playground.task.jbn.dto;

import com.leadconsult.playground.task.jbn.entities.Course;
import com.leadconsult.playground.task.jbn.entities.MainCourse;

public class CourseDTO {
	public static CourseDTO fromEntity (Course course) {
		return new CourseDTO (course.getId(), course.getName(), course instanceof MainCourse);
	}
	
	private Integer id = null;
	private String name = "";
	private boolean isMain = false;
	
	public CourseDTO (Integer id, String name,boolean ismain) {
		super();
		this.id = id;
		this.name = name;
		this.isMain = ismain;
	}
	
	public CourseDTO() {
		super();
	}

	public Integer getId() {
		return id;
	}
	public void setId (Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public boolean isMain() {
		return isMain;
	}

	public void setMain(boolean isMain) {
		this.isMain = isMain;
	}
	
}
