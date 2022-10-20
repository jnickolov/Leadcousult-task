package com.leadconsult.playground.task.jbn.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.NonNull;

@Entity
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn (name = "COURSE_TYPE",  discriminatorType = DiscriminatorType.STRING)
@Table(name = "COURSES")
public class Course implements Serializable {
	private static final long serialVersionUID = 6156603487710496589L;

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Integer id;

	private String name;
	
	@ManyToMany
	private Set<Person> members = new HashSet<>();
	
	public Course(String name) {
		super();
		this.name = name;
	}

	public Course() {
		this ("unnamed course");
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(@NonNull String name) {
		this.name = name;
	}

	
	public Set<Person> getMembers() {
		return members;
	}

	public void setMembers(Set<Person> members) {
		this.members = members;
	}

	public void addMember (@NonNull Person p) {
		this.members.add (p);
	}
	
	public void removeMember (@NonNull Person p) {
		this.members.remove (p);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		return Objects.equals(id, other.id);
	}
}
