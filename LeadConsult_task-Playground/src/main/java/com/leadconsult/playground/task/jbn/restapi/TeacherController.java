package com.leadconsult.playground.task.jbn.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.leadconsult.playground.task.jbn.dto.CourseDTO;
import com.leadconsult.playground.task.jbn.dto.GroupDTO;
import com.leadconsult.playground.task.jbn.dto.TeacherDTO;
import com.leadconsult.playground.task.jbn.servces.PersonService;

@RestController
@RequestMapping ("/api/teachers")
public class TeacherController {
	
	@Autowired
	private PersonService personService;

	@GetMapping (path = "/{sid}")
	public ResponseEntity<TeacherDTO> loadTeacher (@PathVariable int sid) {
		return ResponseEntity.of (personService.loadTeacherDTO (sid));
	}

	@GetMapping (path = "")
	public List<TeacherDTO> listTeachers () {
		return personService.listTeacherDTOs();
	}

	@PostMapping (path = "")
    @ResponseStatus (HttpStatus.CREATED)
	public void createTeacher (@RequestBody TeacherDTO dto) {
		personService.insertTeacher (dto);
	}

	@PutMapping (path = "/{sid}")
    @ResponseStatus (HttpStatus.ACCEPTED)
	public void createTeacher (@PathVariable int sid, @RequestBody TeacherDTO teacher) {
		personService.updateTeacher (teacher);
	}
	
	@DeleteMapping (path = "/{sid}")
    @ResponseStatus (HttpStatus.OK)
	public void deleteTeacher (@PathVariable int sid) {
		personService.deleteTeacher (sid);
	}
	
	@GetMapping (path = "/{sid}/courses")
    @ResponseStatus (HttpStatus.OK)
	public ResponseEntity <List<CourseDTO>> listCoursesForTeacher (@PathVariable int sid) {
		return ResponseEntity.of (personService.listCourseDTOsForTeacher (sid));
	}

	@PostMapping (path = "/{sid}/courses/{cid}")
    @ResponseStatus (HttpStatus.OK)
	public void joinCourse (@PathVariable("sid") int teacherId, @PathVariable("cid") int courseId) {
		personService.joinCourse (courseId, teacherId);
	}

	@DeleteMapping (path = "/{teacherId}/courses/{courseId}")
    @ResponseStatus (HttpStatus.OK)
	public void leaveCourse (
			@PathVariable ("teacherId") int teacherId, 
			@PathVariable ("courseId") int courseId) {
		personService.leaveCourse (courseId, teacherId);
	}

	
	@GetMapping (path = "/{sid}/group")
    @ResponseStatus (HttpStatus.OK)
	public ResponseEntity<GroupDTO> loadGroup (@PathVariable int sid) {
		return ResponseEntity.of (personService.loadStudentGroupDTO (sid));
	}
	@PostMapping (path = "/{sid}/group/{gid}")
    @ResponseStatus (HttpStatus.ACCEPTED)
	public void joinGroup (@PathVariable int sid, @PathVariable int gid) {
		personService.joinTeacherGroup (sid, gid);  //can throw
	}

	@ExceptionHandler
    public ResponseEntity<String> handle (Exception ex) {
		//  TODO: improve exception handling
        return ResponseEntity.status (HttpStatus.INTERNAL_SERVER_ERROR).body (ex.getMessage());
    }

}
