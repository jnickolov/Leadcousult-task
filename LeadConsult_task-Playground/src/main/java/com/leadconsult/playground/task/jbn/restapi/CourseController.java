package com.leadconsult.playground.task.jbn.restapi;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leadconsult.playground.task.jbn.dto.CourseDTO;
import com.leadconsult.playground.task.jbn.entities.Course;
import com.leadconsult.playground.task.jbn.servces.CourseService;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

	@Autowired
	private CourseService courseService;
	
	@GetMapping(path="")
	public List <CourseDTO> listCourses () {
		return courseService.listCourses().stream()
				.map (CourseDTO::fromEntity)
				.collect (Collectors.toList());
	}
	
	@GetMapping(path="/main")
	public List <CourseDTO> listMainCourses () {
		return courseService.listMainCourses().stream()
				.map (CourseDTO::fromEntity)
				.collect (Collectors.toList());
	}

	@GetMapping(path="/secondary")
	public List <CourseDTO> listSecondaryCourses () {
		return courseService.listSecondaryCourses().stream()
				.map (CourseDTO::fromEntity)
				.collect (Collectors.toList());
	}
	
	@GetMapping(path="/{courseId}")
	public ResponseEntity<CourseDTO> listCourses (@PathVariable int courseId) {
		Course c = courseService.loadCourse (courseId);
		if (c != null) 
			return ResponseEntity.ok (CourseDTO.fromEntity(c));
		else
			return ResponseEntity.notFound().build();
	}

	@PostMapping (path="")
	public void addCourse (@RequestBody CourseDTO dto) {
		courseService.saveCourse (dto);
	}
	
	@PutMapping (path="/{courseId}")
	public void updateCourse (@PathVariable int courseId, @RequestBody @NonNull CourseDTO dto) {
		dto.setId (courseId);
		courseService.saveCourse (dto);
	}

	@DeleteMapping (path="/{courseId}")
	public void deleteCourse (@PathVariable int courseId) {
		courseService.deleteCourse (courseId);
	}

	
	
}
