package project.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import project.domain.Administrator;

public interface AdministratorService {
	
	List<Administrator> findAll();
	
	Administrator save(Administrator a);
	
	Administrator getAdministratorByName(String name);

}
