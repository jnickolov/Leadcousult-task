package com.leadconsult.playground.task.jbn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leadconsult.playground.task.jbn.entities.SecondaryCourse;

@Repository
public interface SecondaryCourseRepo extends JpaRepository <SecondaryCourse, Integer> {
	default SecondaryCourse create() {
		return save (new SecondaryCourse ());
	}
	
	default SecondaryCourse create (String name) {
		return save (new SecondaryCourse (name));
	}
}
