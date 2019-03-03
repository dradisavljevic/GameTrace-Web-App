package project.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import project.domain.Active;

public interface ActiveService {

	Page<Active> findAll(Pageable page);
	
	Active findActiveById(Long id);
	
	Active findActiveByUsername(String uname);
	
	void removeActiveById(Long id);
	
	void removeActiveByUsername(String uname);
	
	Active save(Active active);
	
	List<Active> findAllActivebyUsername(String uname);
}
