package com.leadconsult.playground.task.jbn.servces;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadconsult.playground.task.jbn.dto.GroupDTO;
import com.leadconsult.playground.task.jbn.entities.Group;
import com.leadconsult.playground.task.jbn.repositories.GroupRepo;

@Service
public class GroupService {
	@Autowired
	private GroupRepo groupRepo;
	
	public List<Group> listGroups() {
		return groupRepo.findAll();
	}
	
	public Optional<Group> loadGroup (int id) {
		return groupRepo.findById (id);
	}
	
	public Group saveGroup (Group gr) {
		return groupRepo.save (gr);
	}
	
	public void deleteGroup (int id) {
		groupRepo.deleteById (id);
	}

	public void saveGroup (GroupDTO dto) {
		Group gr;
		
		if (dto.getId() == null) {
			gr = new Group();
		} else {
			gr = groupRepo.findById (dto.getId()).get();  // can throw exception
		}
		
		GroupDTO.fillEntity (dto, gr);
		groupRepo.save (gr);
	}
}
