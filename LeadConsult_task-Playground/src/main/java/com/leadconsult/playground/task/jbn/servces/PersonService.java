package com.leadconsult.playground.task.jbn.servces;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.leadconsult.playground.task.jbn.dto.CourseDTO;
import com.leadconsult.playground.task.jbn.dto.GroupDTO;
import com.leadconsult.playground.task.jbn.dto.StudentDTO;
import com.leadconsult.playground.task.jbn.dto.TeacherDTO;
import com.leadconsult.playground.task.jbn.entities.Course;
import com.leadconsult.playground.task.jbn.entities.Group;
import com.leadconsult.playground.task.jbn.entities.Person;
import com.leadconsult.playground.task.jbn.entities.Student;
import com.leadconsult.playground.task.jbn.entities.Teacher;
import com.leadconsult.playground.task.jbn.repositories.GroupRepo;
import com.leadconsult.playground.task.jbn.repositories.StudentRepo;
import com.leadconsult.playground.task.jbn.repositories.TeacherRepo;

@Service
public class PersonService {
	@Autowired
	private StudentRepo studentRepo;

	@Autowired
	private TeacherRepo teacherRepo;

	@Autowired
	private GroupRepo groupRepo;

	@Autowired
	private ServiceUtils serviceUtils;

	@Autowired
	private TransactionTemplate transactionTemplate;

	public int studentsCount () {
		return (int) studentRepo.count();
	}
	
	public List<StudentDTO> listStudentDTOs() {
		return listStudents().stream()
				.map(s -> new StudentDTO(s.getId(), s.getName(), s.getBirthDate(),
						(s.getGroup() != null) ? s.getGroup().getId() : null, s.getGrade()))
				.collect(Collectors.toList());
	}

	public Optional<StudentDTO> loadStudentDTO(int id) {
		Optional<Student> os = studentRepo.findById(id);
		if (os.isPresent()) {
			return Optional.of(StudentDTO.fromEntity(os.get()));
		} else {
			return Optional.empty();
		}
	}

	public Student insertStudent(StudentDTO dto) { // ignore dto's id
		return transactionTemplate.execute(tc -> {
			Integer groupId = dto.getGroupId();

			Student s = studentRepo.create(dto.getName(), dto.getBirthDate(), dto.getGrade()); // persisted
			if (groupId != null) {
				groupRepo.findById(groupId).ifPresent((gr) -> s.joinGroup(gr));
			}

			return s;
		});
	}

	public @Nullable Student updateStudent(StudentDTO dto) { // update existing student
		return transactionTemplate.execute(tc -> {
			if (dto.getId() == null) { // wrong command!
				return null;
			}

			Optional<Student> os = studentRepo.findById(dto.getId());
			if (os.isEmpty()) { // entity does not exist
				return null;
			}

			// Copy fields
			Student student = os.get();
			student.setName(dto.getName());
			student.setBirthDate(dto.getBirthDate());
			student.setGrade(dto.getGrade());

			// Handle group membership
			Integer groupId = dto.getGroupId();

			if (groupId != null) {
				groupRepo.findById(groupId).ifPresent((gr) -> {
					if (groupId != gr.getId())
						student.joinGroup(gr);
				});
			}

			return student; // no need to save student - it is persisted
		});
	}

	public List<Student> listStudents() {
		return studentRepo.findAll();
	}

	public Optional<Student> loadStudent(int id) {
		return studentRepo.findById(id);
	}

	public Student saveStudent(Student student) {
		return studentRepo.save(student);
	}

	public boolean deleteStudent(int studentId) {
		Optional<Student> oStudent = this.loadStudent(studentId);

		if (oStudent.isEmpty()) {
			return false;

		} else {
			Student student = oStudent.get();
			serviceUtils.detachPerson(student);
			studentRepo.deleteById(studentId);

			return true;
		}
	}

	public Optional<List<CourseDTO>> listCourseDTOsForStudent(int sid) {
		Optional<List<Course>> oCourses = this.listCoursesForStudent(sid);

		if (oCourses.isPresent()) {
			List<CourseDTO> ldtos = oCourses.get().stream().map(CourseDTO::fromEntity).collect(Collectors.toList());
			return Optional.of(ldtos);

		} else {
			return Optional.empty();
		}
	}

	public Optional<List<Course>> listCoursesForStudent(int sid) {
		Optional<Student> oStudent = studentRepo.findById(sid);
		return oStudent.map(s -> s.getCourses());
	}

	public void joinStudentGroup(int studentId, int groupId) {
		transactionTemplate.executeWithoutResult(tc -> {
			Student s = studentRepo.findById(studentId).get(); // can throw exception
			Group g = groupRepo.findById(groupId).get(); // can throw exception
			s.setGroup(g);
		});
	}

	// Teachers

	public int teachersCount () {
		return (int) teacherRepo.count();
	}

	public List<TeacherDTO> listTeacherDTOs() {
		return listTeachers().stream()
				.map(t -> new TeacherDTO(t.getId(), t.getName(), t.getBirthDate(),
						(t.getGroup() != null) ? t.getGroup().getId() : null, t.getSubjects()))
				.collect(Collectors.toList());
	}

	public Optional<TeacherDTO> loadTeacherDTO(int id) {
		Optional<Teacher> ot = teacherRepo.findById(id);
		if (ot.isPresent()) {
			return Optional.of(TeacherDTO.fromEntity(ot.get()));
		} else {
			return Optional.empty();
		}
	}

	public Teacher insertTeacher (TeacherDTO dto) { // ignore dto's id
		return transactionTemplate.execute(tc -> {
			Integer groupId = dto.getGroupId();

			Teacher t = teacherRepo.create(dto.getName(), dto.getBirthDate(), dto.getSubjects()); // persisted
			if (groupId != null) {
				groupRepo.findById(groupId).ifPresent((gr) -> t.joinGroup(gr));
			}

			return t;
		});
	}

	public @Nullable Teacher updateTeacher(TeacherDTO dto) { // update existing student
		return transactionTemplate.execute(tc -> {
			if (dto.getId() == null) { // wrong command!
				return null;
			}

			Optional<Teacher> os = teacherRepo.findById(dto.getId());
			if (os.isEmpty()) { // entity does not exist
				return null;
			}

			// Copy fields
			Teacher teacher = os.get();
			teacher.setName(dto.getName());
			teacher.setBirthDate(dto.getBirthDate());
			teacher.setSubjects(dto.getSubjects());

			// Handle group membership
			Integer groupId = dto.getGroupId();

			if (groupId != null) {
				groupRepo.findById(groupId).ifPresent((gr) -> {
					if (groupId != gr.getId())
						teacher.joinGroup(gr);
				});
			}

			return teacher; // no need to save student - it is persisted
		});
	}

	public List<Teacher> listTeachers() {
		return teacherRepo.findAll();
	}

	public Optional<Teacher> loadTeacher(int id) {
		return teacherRepo.findById(id);
	}

	public Teacher saveTeacher(Teacher teacher) {
		return teacherRepo.save(teacher);
	}

	public boolean deleteTeacher(int teacherId) {
		Optional<Teacher> oTeacher = this.loadTeacher(teacherId);

		if (oTeacher.isEmpty()) {
			return false;

		} else {
			Teacher teacher = oTeacher.get();
			serviceUtils.detachPerson(teacher);
			teacherRepo.deleteById(teacherId);

			return true;
		}
	}

	public Optional<List<CourseDTO>> listCourseDTOsForTeacher(int tid) {
		Optional<List<Course>> oCourses = this.listCoursesForTeacher(tid);

		if (oCourses.isPresent()) {
			List<CourseDTO> ldtos = oCourses.get().stream().map(CourseDTO::fromEntity).collect(Collectors.toList());
			return Optional.of(ldtos);

		} else {
			return Optional.empty();
		}
	}

	public Optional<List<Course>> listCoursesForTeacher(int tid) {
		Optional<Teacher> oTeacher = teacherRepo.findById(tid);
		return oTeacher.map(s -> s.getCourses());
	}

	public void joinTeacherGroup(int teacherId, int groupId) {
		transactionTemplate.executeWithoutResult(tc -> {
			Teacher t = teacherRepo.findById(teacherId).get(); // can throw exception
			Group g = groupRepo.findById(groupId).get(); // can throw exception
			t.setGroup(g);
		});
	}

	// Common

	public boolean joinCourse(int courseId, int personId) {
		return serviceUtils.addParticipantToCourse(courseId, personId);
	}

	public void leaveCourse(int courseId, int personId) {
		serviceUtils.removeParticipantFromCourse (courseId, personId);
	}

	public void detachPerson(@NonNull Person person) {
		serviceUtils.detachPerson(person);
	}

	public Optional<GroupDTO> loadStudentGroupDTO(int studentId) {
		return this.loadPersonGroupDTO(studentId);
	}

	public Optional<GroupDTO> loadTeacherGroupDTO(int teacherId) {
		return this.loadPersonGroupDTO(teacherId);
	}

	public Optional<GroupDTO> loadPersonGroupDTO(int personId) {
		Group g = groupRepo.loadGroupForPersonId(personId);
		if (g != null) {
			return Optional.of(GroupDTO.fromEntity(g));
		} else {
			return Optional.empty();
		}
	}

	public List<Student> listStudentsInGroup (int groupId) {
		return studentRepo.listStudentsInGroup (groupId);
	}
	public List<Student> listStudentsInCourse (int courseId) {
		return studentRepo.listStudentsInCourse (courseId);
	}

	public List<Student> listStudentsInGroupAndCourse(int groupId, int courseId) {
		return studentRepo.listStudentsInGroupAndCourse(groupId, courseId);
	}
	public List<Teacher> listTeachersInGroupAndCourse(int groupId, int courseId) {
		return teacherRepo.listTeachersInGroupAndCourse(groupId, courseId);
	}

	public List<StudentDTO> listYoungersInCourse(int courseId, int age) {
		LocalDate dt = LocalDate.now().minusYears(age);
		return studentRepo.listYoungersInCourse(courseId, dt).stream()
			.map(s -> StudentDTO.fromEntity(s))
			.collect(Collectors.toList());
	}

}
