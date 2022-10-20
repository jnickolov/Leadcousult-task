package com.leadconsult.playground.task.jbn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leadconsult.playground.task.jbn.entities.Course;


public interface CourseRepo extends JpaRepository <Course, Integer> {

}
