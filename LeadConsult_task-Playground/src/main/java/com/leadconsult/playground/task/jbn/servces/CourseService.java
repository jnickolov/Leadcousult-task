package com.leadconsult.playground.task.jbn.servces;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.leadconsult.playground.task.jbn.dto.CourseDTO;
import com.leadconsult.playground.task.jbn.entities.Course;
import com.leadconsult.playground.task.jbn.entities.MainCourse;
import com.leadconsult.playground.task.jbn.entities.SecondaryCourse;
import com.leadconsult.playground.task.jbn.repositories.CourseRepo;
import com.leadconsult.playground.task.jbn.repositories.MainCourseRepo;
import com.leadconsult.playground.task.jbn.repositories.SecondaryCourseRepo;

@Service
public class CourseService {
	@Autowired
	private CourseRepo courseRepo;

	@Autowired
	private MainCourseRepo mainCourseRepo;

	@Autowired
	private SecondaryCourseRepo secondaryCourseRepo;

	@Autowired
	private ServiceUtils serviceUtils;

	public int mainCoursesCount() {
		return (int) mainCourseRepo.count();
	}

	public int secondaryCoursesCount() {
		return (int) secondaryCourseRepo.count();
	}

	public @Nullable Course loadCourse(int id) {
		return courseRepo.findById(id).orElse(null);
	}

	public List<Course> listCourses() {
		return courseRepo.findAll();
	}

	public List<MainCourse> listMainCourses() {
		return mainCourseRepo.findAll();
	}

	public List<SecondaryCourse> listSecondaryCourses() {
		return secondaryCourseRepo.findAll();
	}

	public Course saveCourse(@NonNull Course course) {
		courseRepo.save(course);
		return course;
	}

	public Course saveCourse (@NonNull CourseDTO dto) {
		Course course;
		if (dto.getId() == null) {
			course = dto.isMain() ? new MainCourse(dto.getName()) : new SecondaryCourse(dto.getName());
		} else {
			course = courseRepo.findById(dto.getId()).get(); // can throw
		}
		course.setName(dto.getName());
		saveCourse(course);
		
		return course;
	}

	public @Nullable Integer deleteCourse(@NonNull Course course) {
		Integer cid = course.getId();
		if (cid != null) {
			courseRepo.deleteById (course.getId());
		}
		return cid;
	}


	public Integer deleteCourse (int courseId) {
		Course course = courseRepo.findById (courseId).orElseGet (null);
		if (course != null) {
			serviceUtils.removeAllParticipantsFromCourse (course);
			courseRepo.deleteById (courseId);
		}
		return courseId;
	}

	public boolean addParticipant (int courseId, int participantId) {
		return serviceUtils.addParticipantToCourse(courseId, participantId);
	}

	public void removeParticipant (int courseId, int participantId) {
		serviceUtils.removeParticipantFromCourse(courseId, participantId);
	}

	public int countCourses() {
		return (int) courseRepo.count();
	}

	public int countMainCourses() {
		return (int) mainCourseRepo.count();
	}

	public int countSecondaryCourses() {
		return (int) secondaryCourseRepo.count();
	}

}
