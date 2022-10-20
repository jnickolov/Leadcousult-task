package com.leadconsult.playground.task.jbn.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NonNull;

@Entity
@Table(name = "TEACHERS")
public class Teacher extends Person {
	private static final long serialVersionUID = -975366009550896408L;

	private String subjects = "";

	public Teacher() {
		super();
	}

	public Teacher (@NonNull String name, @NonNull LocalDate dob) {
		this (name, dob, "");
	}

	public Teacher (@NonNull String name, @NonNull LocalDate dob, @NonNull String subjects) {
		super (name, dob);
		this.setSubjects (subjects);
	}

	public String getSubjects() {
		return this.subjects;
	}

	public void setSubjects (@NonNull String subjects) {
		this.subjects = subjects;
	}
}
