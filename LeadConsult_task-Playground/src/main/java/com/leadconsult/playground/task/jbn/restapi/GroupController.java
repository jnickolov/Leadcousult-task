package com.leadconsult.playground.task.jbn.restapi;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.leadconsult.playground.task.jbn.dto.GroupDTO;
import com.leadconsult.playground.task.jbn.dto.StudentDTO;
import com.leadconsult.playground.task.jbn.entities.Group;
import com.leadconsult.playground.task.jbn.servces.GroupService;
import com.leadconsult.playground.task.jbn.servces.PersonService;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
	@Autowired
	private GroupService groupService;
	@Autowired
	private PersonService personService;

	@GetMapping (path = "/{sid}")
	public ResponseEntity<GroupDTO> loadGroup (@PathVariable int sid) {
		Optional<Group> ogr = groupService.loadGroup (sid);
		if (ogr.isPresent()) {
			return ResponseEntity.ok (GroupDTO.fromEntity (ogr.get()));
		} else {
			return ResponseEntity.status (HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping (path = "/{grId}/students")
	public ResponseEntity<List<StudentDTO>> studentsInGroup (@PathVariable int grId) {
		Optional<Group> ogr = groupService.loadGroup (grId);
		if (ogr.isPresent()) {
			return ResponseEntity.ok (
				personService.listStudentsInGroup(grId).stream()
					.map (StudentDTO::fromEntity)
					.collect (Collectors.toList())
			);
			
		} else {
			return ResponseEntity.status (HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping (path = "")
    @ResponseStatus (HttpStatus.OK)
	public List<GroupDTO> listGroups () {
		return groupService.listGroups().stream()
				.map (GroupDTO::fromEntity)
				.collect (Collectors.toList());
	}

	@PostMapping (path = "")
    @ResponseStatus (HttpStatus.CREATED)
	public void createGroup (@RequestBody @NonNull GroupDTO dto) {
		dto.setId (null);
		groupService.saveGroup (dto);
	}

	@PutMapping (path = "/{gid}")
    @ResponseStatus (HttpStatus.ACCEPTED)
	public void updateGroup (@PathVariable int gid, @RequestBody @NonNull GroupDTO dto) {
		dto.setId (gid);
		groupService.saveGroup (dto);
	}

}
