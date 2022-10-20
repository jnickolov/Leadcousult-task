package com.leadconsult.playground.task.jbn.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.leadconsult.playground.task.jbn.entities.Student;
import com.leadconsult.playground.task.jbn.entities.Teacher;

@Repository
public interface TeacherRepo extends JpaRepository <Teacher, Integer> {
	default Teacher create (String name, LocalDate dob, String sbj) {
		Teacher p = new Teacher (name, dob, sbj);
		save (p);
		return p;
	}
	
	default Teacher create (String name, LocalDate dob) {
		Teacher p = new Teacher (name, dob);
		save (p);
		return p;
	}
	
	default Teacher create () {
		Teacher p = new Teacher ();
		save (p);
		return p;
	}
	
	@Query ("select t from Teacher t, Course c, Group g " +
			" where c.id = :cId and g.id = :gId and " + 
			" t member of c.members and t member of g.members")
	List<Teacher> listTeachersInGroupAndCourse (@Param ("gId") int gId, @Param ("cId") int cId);

}

