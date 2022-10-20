package com.leadconsult.playground.task.jbn.restapi;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.leadconsult.playground.task.jbn.dto.StudentDTO;
import com.leadconsult.playground.task.jbn.dto.TeacherDTO;
import com.leadconsult.playground.task.jbn.dto.TeachersAndStudentsDTO;
import com.leadconsult.playground.task.jbn.entities.Student;
import com.leadconsult.playground.task.jbn.entities.Teacher;
import com.leadconsult.playground.task.jbn.servces.PersonService;

@RestController
@RequestMapping ("/api/reports")
public class ReportsController {

	@Autowired
	private PersonService personService;

	@GetMapping (path = "/students-count")
	@ResponseStatus(HttpStatus.OK)
	public String studentsCount () {
		return "Students count: " + personService.studentsCount();
	}
	
	@GetMapping (path = "/teachers-count")
	@ResponseStatus(HttpStatus.OK)
	public String teachersCount () {
		return "Teachers count: " + personService.teachersCount();
	}
	
	@GetMapping (path = "/students-in-group/{groupId}")
	@ResponseStatus(HttpStatus.OK)
	public List<StudentDTO> studentsInGroup (@PathVariable ("groupId") int groupId) {
		return personService.listStudentsInGroup (groupId).stream()
			.map (StudentDTO::fromEntity).collect (Collectors.toList());
	}
	
	@GetMapping (path = "/students-in-course/{courseId}")
	@ResponseStatus(HttpStatus.OK)
	public List<StudentDTO> studentsInCourse (@PathVariable ("courseId") int courseId) {
		return personService.listStudentsInCourse (courseId).stream()
			.map (StudentDTO::fromEntity).collect (Collectors.toList());
	}
	
	@GetMapping (path = "/group-and-course-members/{groupId}/{courseId}")
	@ResponseStatus(HttpStatus.OK)
	public TeachersAndStudentsDTO teachersAndStudentsInGroupAndCourse (
			@PathVariable ("groupId") int groupId,
			@PathVariable ("courseId") int courseId) {
		
		List<Student> students = personService.listStudentsInGroupAndCourse (groupId, courseId);
		List<Teacher> teachers = personService.listTeachersInGroupAndCourse (groupId, courseId);
		
		return new TeachersAndStudentsDTO (
			teachers.stream().map (t -> TeacherDTO.fromEntity (t)).collect (Collectors.toList()),
			students.stream().map (s -> StudentDTO.fromEntity (s)).collect (Collectors.toList())
		);
	}
	
	@GetMapping (path = "/younger-course-members/{courseId}/{age}")
	@ResponseStatus(HttpStatus.OK)
	public List<StudentDTO> youngerStudentsInCourse (
			@PathVariable ("courseId") int courseId, 
			@PathVariable ("age") int age) {
		return personService.listYoungersInCourse (courseId, age);
	}
	
}
