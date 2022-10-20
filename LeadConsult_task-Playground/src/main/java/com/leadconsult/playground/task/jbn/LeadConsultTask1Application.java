package com.leadconsult.playground.task.jbn;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import com.github.javafaker.Faker;
import com.leadconsult.playground.task.jbn.entities.Course;
import com.leadconsult.playground.task.jbn.entities.Group;
import com.leadconsult.playground.task.jbn.entities.MainCourse;
import com.leadconsult.playground.task.jbn.entities.Person;
import com.leadconsult.playground.task.jbn.entities.SecondaryCourse;
import com.leadconsult.playground.task.jbn.entities.Student;
import com.leadconsult.playground.task.jbn.entities.Teacher;
import com.leadconsult.playground.task.jbn.repositories.CourseRepo;
import com.leadconsult.playground.task.jbn.repositories.GroupRepo;
import com.leadconsult.playground.task.jbn.repositories.MainCourseRepo;
import com.leadconsult.playground.task.jbn.repositories.PersonRepo;
import com.leadconsult.playground.task.jbn.repositories.SecondaryCourseRepo;
import com.leadconsult.playground.task.jbn.repositories.StudentRepo;
import com.leadconsult.playground.task.jbn.repositories.TeacherRepo;
import com.leadconsult.playground.task.jbn.servces.PersonService;

@SpringBootApplication
@EnableTransactionManagement
public class LeadConsultTask1Application implements CommandLineRunner {

	@Autowired
	PersonRepo personRepo;

	@Autowired
	StudentRepo studentRepo;
	
	@Autowired
	TeacherRepo teacherRepo;

	@Autowired
	CourseRepo courseRepo;
	
	@Autowired
	MainCourseRepo mainCourseRepo;

	@Autowired
	SecondaryCourseRepo secondaryCourseRepo;
	
	
	@Autowired
	GroupRepo groupRepo;
	
	@Autowired
	PersonService personService;

	
	
	@Autowired 
	TransactionTemplate transactionTemplate;

	private Faker faker  = new Faker(new Locale ("bg"));
	
	public static void main(String[] args) {
		SpringApplication.run (LeadConsultTask1Application.class, args);
	}

	@Override
	public void run (String... args) throws Exception {
		initDB ();
	}

	private void initDB () {
		initStudents();
		initTeachers();

		initCourses ();
		initGroups();
		
		addPersonsToGroups();
		addStudentsToCourses();
		addTeachersToCourses();
	}

	private void initStudents() {
		for (int i = 0; i < 100; i++) {
			String name = faker.name().name();
			Student s = new Student (name, 
				LocalDate.of (2000 + (int)faker.number().numberBetween (0L, 10L), 
					(int)faker.number().numberBetween (1L, 13L),
					(int)faker.number().numberBetween (1L, 29L)), 
				(int)faker.number().numberBetween (1L, 13L));
			studentRepo.save (s);
		}
	}

	private void initTeachers() {
		for (int i = 0; i < 10; i++) {
			String name = faker.name().name();

			Teacher t = new Teacher (name, 
				LocalDate.of (1970 + (int)faker.number().numberBetween (0L, 30L), 
						(int)faker.number().numberBetween (1L, 13L),
						(int)faker.number().numberBetween (1L, 29L)), 
				faker.educator().course());
			teacherRepo.save (t);
		}
	}

	private void initCourses() {
		mainCourseRepo.save (new MainCourse ("Математика"));
		mainCourseRepo.save (new MainCourse ("Физика"));
		mainCourseRepo.save (new MainCourse ("Химия"));
		mainCourseRepo.save (new MainCourse ("Биология"));
		mainCourseRepo.save (new MainCourse ("ИТ"));

		secondaryCourseRepo.save (new SecondaryCourse("Литература"));
		secondaryCourseRepo.save (new SecondaryCourse("Музика"));
		secondaryCourseRepo.save (new SecondaryCourse("Рисуване"));
		secondaryCourseRepo.save (new SecondaryCourse("Гимнастика"));
		
		// Try base class repo to save subcalss entity 
		courseRepo.save (new SecondaryCourse ("Шахмат"));
	}
	
	private void initGroups() {
		for (int i = 1; i <= 10; i++) {
			Group g = new Group ("Group-" + i);

			groupRepo.save (g);
		}
	}

	private void addPersonsToGroups() {
		transactionTemplate.executeWithoutResult ((ts) -> {
			List<Group> groups = groupRepo.findAll();
			List<Person> persons = personRepo.findAll();
			Random rnd = new Random();
			persons.forEach (p -> {
				Group rndGroup = groups.get (rnd.nextInt (groups.size()));
				p.joinGroup (rndGroup);
				groupRepo.save (rndGroup);
				personRepo.save (p);
			});
			
//			System.out.println("Initial Values for groups:");
//			for  (Group g: groups) {
//				System.out.println(g.getId() + ": " + g.getName() + "  -> " + g.getMembers().size());
//			}
		});
	}

	private void addStudentsToCourses() {
		transactionTemplate.executeWithoutResult ((ts) -> {
			List<Course> courses = courseRepo.findAll();
			List<Student> students = studentRepo.findAll();
			Random rnd = new Random();
			courses.forEach (course -> {
				int n = 10 + rnd.nextInt (10);
				for (int i = 0; i < n; i++) {
					Student s = students.get(rnd.nextInt (students.size()));
					s.joinCourse(course);
				}
			});
			
//			System.out.println("Initial Values for courses:");
//			for  (Course c: courses) {
//				System.out.println(c.getId() + ": " + c.getName() + "  -> " + c.getMembers().size());
//			}
		});
	}
	
	private void addTeachersToCourses() {
		transactionTemplate.executeWithoutResult ((ts) -> {
			List<Course> courses = courseRepo.findAll();
			List<Teacher> teachers = teacherRepo.findAll();
			Random rnd = new Random();
			courses.forEach (course -> {
				int n = 1 + rnd.nextInt (4);
				for (int i = 0; i < n; i++) {
					Teacher t = teachers.get(rnd.nextInt (teachers.size()));
					t.joinCourse(course);
				}
			});
		});
	}
}
