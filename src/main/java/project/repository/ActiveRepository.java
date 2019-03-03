package project.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import project.domain.Active;


public interface ActiveRepository extends Repository<Active, Long> {

	public Page<Active> findAll(Pageable page);
	
	@Query("select a from Active a where a.actid = ?1")
	public Active findActiveById(Long id);
	
	@Query("select a from Active a where a.actuser.uname = ?1")
	public Active findActiveByUsername(String uname);
	
	@Query("select a from Active a where a.actuser.uname = ?1")
	public List<Active> findAllActivebyUsername(String uname);
	
	@Modifying
	@Query("delete from Active a where a.actid = ?1")
	public void removeActiveById(Long id);
	
	@Modifying
	@Query("delete from Active a where a.actuser.uname = ?1")
	public void removeActiveByUsername(String uname);
	
	public Active save(Active active);
}
