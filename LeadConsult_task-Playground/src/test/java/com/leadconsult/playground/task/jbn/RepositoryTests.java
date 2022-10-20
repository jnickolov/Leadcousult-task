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
import com.leadconsult.playground.task.jbn.entities.Group;
import com.leadconsult.playground.task.jbn.entities.MainCourse;
import com.leadconsult.playground.task.jbn.entities.Person;
import com.leadconsult.playground.task.jbn.entities.Student;
import com.leadconsult.playground.task.jbn.repositories.GroupRepo;
import com.leadconsult.playground.task.jbn.repositories.PersonRepo;
import com.leadconsult.playground.task.jbn.repositories.StudentRepo;
import com.leadconsult.playground.task.jbn.repositories.TeacherRepo;
import com.leadconsult.playground.task.jbn.servces.CourseService;
import com.leadconsult.playground.task.jbn.servces.PersonService;

@SpringBootTest
class RepositoryTests {

	@Autowired
	PersonRepo personRepo;

	@Autowired
	StudentRepo studentRepo;
	
	@Autowired
	TeacherRepo teacherRepo;

	@Autowired
	GroupRepo groupRepo;
	
	@Test
	void contextLoads() {
		//  empty
	}

	@Test
	void countPersons() {
		System.out.println("_________Persons: " + personRepo.count());
	}

	@Test
	void countStudents () {
		System.out.println("_________STUDENTS: " + studentRepo.count());
	}
	
	@Test
	void countTeachers () {
		System.out.println("_________TEACHERS: " + teacherRepo.count());
	}

	
	
	void listGroups () {
		System.out.println("_________Groups:");
		groupRepo.findAll().forEach (gr -> System.out.println ("       " + gr.getId() + ": " + gr.getName()));
		System.out.println("_________");
	}
	
	@Test
	void studentsInGroup () {
		System.out.println("_________Students in Groups:");
		groupRepo.findAll().forEach (gr -> {
			List<Person> prs = personRepo.listGroupMembers (gr.getId()); 
			System.out.println ("       " + gr.getId() + ": " + gr.getName() + " ->" + prs.size());
			prs.forEach(p -> System.out.println("                " + p.getName()));
		});
		System.out.println("_________");
	}
	
	@Test
	@Transactional
	@Commit	
	void createNewGroupAndAddStudent () {
		int oldGroupCnt;

		Group g = new Group ("New Group");
		groupRepo.save (g);

		Student s = studentRepo.findById(1).get();
		
		Integer oldGroupId = s.getGroup().getId();
		Group oldGroup = groupRepo.findById(oldGroupId).get();
		oldGroupCnt = oldGroup.getMembers().size();
		
		s.joinGroup(g);
		
		oldGroup = groupRepo.findById(oldGroupId).get();
		int newGroupCnt = oldGroup.getMembers().size();
		
		assertEquals (oldGroupCnt-1, newGroupCnt);
	}
	
	@Test
	void listYoungers () {
		LocalDate ldate = LocalDate.of(2003, 1, 1);
		List<Person> y = personRepo.listYoungers(ldate);
		y.forEach(p -> System.out.println("Younger: " + p.getBirthDate() +", " + p.getName()));
	}

}
