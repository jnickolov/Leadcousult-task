package com.leadconsult.playground.task.jbn.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NonNull;


@Entity
@Table(name = "STUDENTS")
public class Student extends Person {
	private static final long serialVersionUID = -7288534857243212669L;

	private int grade = 1;
	
	public Student() {
		super();
	}
	
	public Student(int grade) {
		super();
		this.grade = grade;
	}

	public Student(@NonNull String name, @NonNull LocalDate dob) {
		super(name, dob);
	}
	public Student (@NonNull String name, @NonNull LocalDate dob, int grade) {
		super (name, dob);
		this.grade = grade;
	}

	public int getGrade() {
		return this.grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

}
