package project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import project.domain.Administrator;

public interface AdministratorRepository extends Repository<Administrator, Long> {
	
	public List<Administrator> findAll();
	
	public Administrator save(Administrator a);
	
	@Query("select a from Administrator a where a.uname = ?1")
	public Administrator getAdministratorByName(String name);

}
