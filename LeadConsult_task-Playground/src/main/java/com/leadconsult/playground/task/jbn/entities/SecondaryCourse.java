package com.leadconsult.playground.task.jbn.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue ("secondary")
public class SecondaryCourse extends Course {
	private static final long serialVersionUID = -2146830505059294733L;

	public SecondaryCourse () {
		super ();
	}

	public SecondaryCourse (String name) {
		super (name);
	}
	
}
