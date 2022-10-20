package com.leadconsult.playground.task.jbn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.leadconsult.playground.task.jbn.entities.Course;
import com.leadconsult.playground.task.jbn.entities.MainCourse;
import com.leadconsult.playground.task.jbn.entities.Student;
import com.leadconsult.playground.task.jbn.repositories.PersonRepo;
import com.leadconsult.playground.task.jbn.repositories.StudentRepo;
import com.leadconsult.playground.task.jbn.servces.CourseService;
import com.leadconsult.playground.task.jbn.servces.PersonService;

@SpringBootTest
public class ServiceTests {
	@Autowired
	CourseService courseService;

	@Autowired
	PersonService personService;

	@Autowired
	StudentRepo studentRepo;

	@Autowired
	PersonRepo personRepo;

	@Test
	void countCourses () {
		System.out.println("_________Main Courses: " + courseService.countMainCourses());
		courseService.listMainCourses().forEach (gr -> System.out.println ("       " + gr.getId() + ": " + gr.getName()));
		System.out.println("_________Secondary Courses: " + courseService.countSecondaryCourses());
		courseService.listSecondaryCourses().forEach (gr -> System.out.println ("       " + gr.getId() + ": " + gr.getName()));
	}
	
	@Test
	@Transactional
	@Commit	
	void courseTests () {
		int oldMainCourses = courseService.countMainCourses(); 
		MainCourse course = new MainCourse ("География");
		courseService.saveCourse(course);
		System.out.println("----------New main courses:");
		courseService.listMainCourses().forEach (gr -> System.out.println ("       " + gr.getId() + ": " + gr.getName()));
		int newMainCourses = courseService.countMainCourses();
		assertEquals (oldMainCourses+1, newMainCourses);
		
		Page<Student> pg = studentRepo.findAll(Pageable.ofSize(3));
		pg.getContent().forEach(s -> s.joinCourse(course));
		
		assertEquals(3, course.getMembers().size());
		
		Student s0 = pg.getContent().get(0);
		Student s1 = pg.getContent().get(1);
		
		// Remove from student'sside
		s0.leaveCourse(course);
		assertEquals(2, course.getMembers().size());
		
		// Remove from course's side
		course.removeMember(s1);
		assertEquals(1, course.getMembers().size());
		
		// Repeat removing from both sides: should have no effect
		course.removeMember(s1);
		assertEquals(1, course.getMembers().size());
		
		s1.leaveCourse(course);
		assertEquals(1, course.getMembers().size());
		
		// using service layer
		int studentId0 = s0.getId();
		int courseId = course.getId();
		
		personService.joinCourse (courseId, studentId0);
		assertEquals(2, course.getMembers().size());
		personService.leaveCourse(courseId, studentId0);
		assertEquals(1, course.getMembers().size());
	}
	@Test
	@Transactional
	@Commit	
	void listParticipantsInCourses () {
		List<Course> courses = courseService.listCourses();
		
		System.out.println("------------------------\nParticipanst by course");
		courses.forEach (course -> {
			System.out.println("\tCourse: " + course.getName() + ((course instanceof MainCourse) ? " M" : " S") + " members: " + course.getMembers().size());
			course.getMembers().forEach(p -> System.out.println("\t\t" + p.getBirthDate() +", " + p.getName()));
		});
		System.out.println("------------------------");
	}
	
	@Test
	@Transactional
	@Commit	
	void listYoungersInCourses () {
		LocalDate ldate = LocalDate.of(2006, 1, 1);
		
		List<Course> courses = courseService.listCourses();
		
		System.out.println("------------------------\nYoungers by course");
		courses.forEach (course -> {
			System.out.println("\tCourse: " + course.getName() + ((course instanceof MainCourse) ? " M" : " S") + " members: " + course.getMembers().size());
			List<Student> yng = studentRepo.listYoungersInCourse(course.getId(), ldate);
			
			yng.forEach (p -> System.out.println("\t\t" + p.getBirthDate() +", " + p.getName()));
			yng.forEach (p -> assertTrue (p.getBirthDate().isAfter(ldate)));
			
		});
		System.out.println("------------------------");
	}

}
