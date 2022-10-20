package com.leadconsult.playground.task.jbn.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
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
import com.leadconsult.playground.task.jbn.dto.StudentDTO;
import com.leadconsult.playground.task.jbn.servces.PersonService;

@RestController
@RequestMapping ("/api/students")
public class StudentController {
	
	@Autowired
	private PersonService personService;

	@GetMapping (path = "/{sid}")
	public ResponseEntity<StudentDTO> loadStudent (@PathVariable int sid) {
		return ResponseEntity.of (personService.loadStudentDTO (sid));
	}

	@GetMapping (path = "")
	public List<StudentDTO> listStudents () {
		return personService.listStudentDTOs();
	}

	@PostMapping (path = "")
    @ResponseStatus (HttpStatus.CREATED)
	public void createStudent (@RequestBody StudentDTO dto) {
		personService.insertStudent (dto);
	}

	@PutMapping (path = "/{sid}")
    @ResponseStatus (HttpStatus.ACCEPTED)
	public void updateStudent (@PathVariable int sid, @RequestBody @NonNull StudentDTO dto) {
		dto.setId (sid);
		personService.updateStudent (dto);
	}
	
	@DeleteMapping (path = "/{sid}")
    @ResponseStatus (HttpStatus.OK)
	public void deleteStudent (@PathVariable int sid) {
		personService.deleteStudent (sid);
	}
	
	@GetMapping (path = "/{sid}/courses")
    @ResponseStatus (HttpStatus.OK)
	public ResponseEntity <List<CourseDTO>> listCoursesForStudent (@PathVariable int sid) {
		return ResponseEntity.of (personService.listCourseDTOsForStudent (sid));
	}

	@PostMapping (path = "/{sid}/courses/{cid}")
    @ResponseStatus (HttpStatus.OK)
	public void joinCourse (@PathVariable("sid") int studentId, @PathVariable("cid") int courseId) {
		personService.joinCourse (courseId, studentId);
	}

	@DeleteMapping (path = "/{studentId}/courses/{courseId}")
    @ResponseStatus (HttpStatus.OK)
	public void leaveCourse (
			@PathVariable("studentId") int studentId, 
			@PathVariable("courseId") int courseId) {
		personService.leaveCourse (courseId, studentId);
	}
	
	@GetMapping (path = "/{sid}/group")
    @ResponseStatus (HttpStatus.OK)
	public ResponseEntity<GroupDTO> loadGroup (@PathVariable int sid) {
		return ResponseEntity.of (personService.loadStudentGroupDTO (sid));
	}
	@PostMapping (path = "/{sid}/group/{gid}")
    @ResponseStatus (HttpStatus.ACCEPTED)
	public void joinGroup (@PathVariable int sid, @PathVariable int gid) {
		personService.joinStudentGroup (sid, gid);  //can throw
	}
	
	//---------------------------
	
	@ExceptionHandler
    public ResponseEntity<String> handle (Exception ex) {
		//  TODO: improve exception handling
        return ResponseEntity.status (HttpStatus.INTERNAL_SERVER_ERROR).body ("Error :(");
    }
}

