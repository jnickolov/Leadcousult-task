package com.leadconsult.playground.task.jbn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leadconsult.playground.task.jbn.entities.MainCourse;

public interface MainCourseRepo extends JpaRepository <MainCourse, Integer> {
	default MainCourse create (String name) {
		return save (new MainCourse (name));
	}

	default MainCourse create () {
		//return save (new MainCourse ());
		MainCourse mc  = new MainCourse();
		save(mc);
		return mc;
	}
}
