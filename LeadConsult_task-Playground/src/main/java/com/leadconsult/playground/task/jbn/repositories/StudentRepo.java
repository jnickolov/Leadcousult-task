package com.leadconsult.playground.task.jbn.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.leadconsult.playground.task.jbn.entities.Student;

public interface StudentRepo extends JpaRepository<Student, Integer> {
	default Student create (String name, LocalDate dob, int grd) {
		Student s = new Student (name, dob, grd);
		save (s);
		return s;
	}
	
	default Student create (String name, LocalDate dob) {
		Student s = new Student (name, dob);
		save (s);
		return s;
	}

	default Student create () {
		Student s = new Student();
		save (s);
		return s;
	}

	@Query ("select s from Student s, Group g " +
			" where g.id = :gId and s member of g.members")
	List<Student> listStudentsInGroup (@Param ("gId") int gId);

	@Query ("select s from Student s, Course c " +
			" where c.id = :cId and s member of c.members")
	List<Student> listStudentsInCourse (@Param ("cId") int cId);

	@Query ("select s from Student s, Course c, Group g " +
			" where c.id = :cId and g.id = :gId and " + 
			" s member of c.members and s member of g.members")
	List<Student> listStudentsInGroupAndCourse (@Param ("gId") int gId, @Param ("cId") int cId);

	
	@Query ("select s from Student s, Course c where s.birthDate > :date and c.id = :cId and s member of c.members")
	List<Student> listYoungersInCourse (@Param ("cId") int cId, @Param ("date") LocalDate date);

}
