package com.leadconsult.playground.task.jbn.entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.NonNull;

@Entity
@Table (name = "GROUPS")
public class Group implements Serializable {
	private static final long serialVersionUID = 564755557962738439L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id = null;
	
	private String name = "unnamed group";
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "group", orphanRemoval = false)
//	private Set<Person> members = new HashSet<>();
	private List<Person> members = new LinkedList<>();
	
	public Group (String name) {
		super();
		this.name = name;
	}

	public Group() {
		super();
		// empty
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

	public void setName (String name) {
		this.name = name;
	}

	public List<Person> getMembers() {
		return members;
	}

	public void setMembers (List<Person> participants) {
		this.members = participants.stream().distinct().collect (Collectors.toList());
	}
	
	public boolean addMember (@NonNull Person p) {
		if (this.members.contains (p)) {
			return false;
		} else {
			this.members.add (p);
			return true;
		}
	}
	
	public boolean removeMember (@NonNull Person p) {
		if (this.members.contains (p)) {
			this.members.remove (p);
			return true;
		} else {
			return false;
		}
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
		Group other = (Group) obj;
		return Objects.equals(id, other.id);
	}
	
}
