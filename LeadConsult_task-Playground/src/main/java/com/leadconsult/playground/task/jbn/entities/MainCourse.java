package com.leadconsult.playground.task.jbn.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue ("main")
public class MainCourse extends Course {

	private static final long serialVersionUID = -8395070243349573802L;

	public MainCourse () {
		super();
	}

	public MainCourse (String name) {
		super (name);
	}
}
