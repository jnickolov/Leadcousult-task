package com.leadconsult.playground.task.jbn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.leadconsult.playground.task.jbn.entities.Group;

public interface GroupRepo extends JpaRepository <Group, Integer> {

	@Query("select p.group from Person p where p.id = :personId")
	public Group loadGroupForPersonId (@Param ("personId") Integer personId);
}
