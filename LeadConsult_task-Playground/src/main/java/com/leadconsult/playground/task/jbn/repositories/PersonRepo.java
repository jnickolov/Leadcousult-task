package com.leadconsult.playground.task.jbn.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.leadconsult.playground.task.jbn.entities.Person;

@Repository
public interface PersonRepo extends JpaRepository<Person, Integer> {
	@Query ("select p from Course c, Person p where c.id = :cId and p member of c.members")
	List<Person> listCourseMembers (@Param("cId") Integer cc);

	@Query ("select p from Person p, Group g where g.id = :grId and p member of g.members")
	List<Person> listGroupMembers (@Param ("grId") Integer grId);

	@Query ("select p from Person p where p.birthDate > :dt")
	List<Person> listYoungers (@Param ("dt") LocalDate dt);

}
