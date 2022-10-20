package com.leadconsult.playground.task.jbn.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NonNull;

@Entity
@Table(name = "PERSONS")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person implements Serializable {
	private static final long serialVersionUID = -7871683985032668560L;

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Integer id;
	
	@NonNull
	private String name;
	
	@NonNull
	private LocalDate birthDate;
	
	@ManyToOne (fetch = FetchType.LAZY)
	private Group group;
	
	@ManyToMany (mappedBy = "members",fetch = FetchType.LAZY)
	private List<Course> courses = new LinkedList<>();
	
	public Person () {
		this ("", LocalDate.now());
	}
	
	public Person (@NonNull String name, @NonNull LocalDate dob) {
		this.id = null;
		this.name = name;
		this.birthDate = dob;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId (Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate (LocalDate dob) {
		this.birthDate = dob;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup (Group group) {
		this.group = group;
	}

	
	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses.stream().distinct().collect(Collectors.toList());
	}

	public void joinGroup (@NonNull Group group) {
		leaveGroup ();
		
		if (group != null) {
			group.addMember (this);
			this.group = group;
		}
	}
	
	public void leaveGroup () {
		if (this.group != null) {
			this.group.removeMember (this);
			this.group = null;
		}
	}
	
	public void joinCourse (@NonNull Course course) {
		this.courses.add (course);
		course.addMember (this);
	}
	
	public void leaveCourse (@NonNull Course course) {
		this.courses.remove (course);
		course.removeMember (this);
	}
	
	
	public int getAge() {
		return (int) ChronoUnit.YEARS.between (this.getBirthDate(), LocalDate.now());
	}

	public int hashCode() {
		return Objects.hash(birthDate, id, name);
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		return Objects.equals(birthDate, other.birthDate) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name);
	}

}
