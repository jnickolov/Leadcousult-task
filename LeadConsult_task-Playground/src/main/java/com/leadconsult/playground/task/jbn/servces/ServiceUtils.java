package com.leadconsult.playground.task.jbn.servces;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.leadconsult.playground.task.jbn.entities.Course;
import com.leadconsult.playground.task.jbn.entities.Group;
import com.leadconsult.playground.task.jbn.entities.Person;
import com.leadconsult.playground.task.jbn.repositories.CourseRepo;
import com.leadconsult.playground.task.jbn.repositories.MainCourseRepo;
import com.leadconsult.playground.task.jbn.repositories.PersonRepo;
import com.leadconsult.playground.task.jbn.repositories.SecondaryCourseRepo;

@Service
class ServiceUtils {  // non-public class: package visible
	@Autowired
	private CourseRepo courseRepo;

	@Autowired
	private MainCourseRepo mainCourseRepo;

	@Autowired
	private SecondaryCourseRepo secondaryCourseRepo;

	@Autowired
	private PersonRepo personRepo;
	
	@Autowired 
	TransactionTemplate transactionTemplate;


	@Transactional
	public boolean addParticipantToCourse (int courseId, int participantId) {
		return transactionTemplate.execute (ts -> {
			Course course = courseRepo.findById (courseId).orElse(null);
			if (course == null)
				return  false;
			
			Person person = personRepo.findById (participantId).orElse(null);
			if (person == null) {
				return false;
			}
			
			person.joinCourse(course);
			return true;
		});
	}

	public void removeParticipantFromCourse (int courseId, int participantId) {
		transactionTemplate.executeWithoutResult(ts -> {
			Course course = courseRepo.findById (courseId).orElse(null);
			if (course != null) {
				Person person = personRepo.findById (participantId).orElse(null);
				if (person != null) {
					person.leaveCourse (course);
				}
			}		
		});
	}

	public void removeAllParticipantsFromCourse (@NonNull Course course) {
		transactionTemplate.executeWithoutResult (ts -> {
			course.getMembers().forEach (p -> p.leaveCourse(course));
		});
	}

	public void removeAllMembersFromGroup (@NonNull Group group) {
		transactionTemplate.executeWithoutResult (ts -> {
			group.getMembers().forEach (p -> p.leaveGroup ());
		});
	}

	// Remove a person from group and all courses
	public void detachPerson (Person person) {
		person.leaveGroup();
		
		Set<Course> courses = new HashSet<>();
		courses.addAll (person.getCourses());
		
		courses.forEach (course -> person.leaveCourse (course));
	}
}
