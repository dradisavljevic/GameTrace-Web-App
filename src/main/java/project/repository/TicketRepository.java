package project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import project.domain.Ticket;

public interface TicketRepository extends Repository<Ticket, Long>{
	
	public List<Ticket> findAll();
	
	public Ticket save(Ticket t);
	
	@Query("select t from Ticket t where t.ticksub = ?1 ")
	public List<Ticket> getAllBySubmitter(String name);
	
	@Query("select t from Ticket t where UPPER(t.tickt) LIKE UPPER( ?1 )")
	public List<Ticket> getAllByTitle(String title);
	
	@Query("select t from Ticket t where UPPER(t.ticksub) LIKE UPPER( ?1 )")
	public List<Ticket> getAllByUser(String name);
	
	@Query("select t from Ticket t where UPPER(t.ticksub) LIKE UPPER( ?1 ) AND t.tickstat = ?2")
	public List<Ticket> getAllByUserUnanswered(String name, String status);
	
	@Query("select t from Ticket t where UPPER(t.tickt) LIKE UPPER( ?1 ) AND t.tickstat = ?2")
	public List<Ticket> getAllByTitleUnanswered(String title, String status);
	
	@Query("select t from Ticket t where UPPER(t.tickstat) LIKE UPPER( ?1 )")
	public List<Ticket> getAllByStatus(String status);
	
	@Query("select t from Ticket t where t.tickstat = ?1 ")
	public List<Ticket> getAllUnanswered(String status);
	
	@Query("select t from Ticket t where t.tickid = ?1 ")
	public Ticket getTicketById(long id);
	
	@Modifying
	@Query("delete from Ticket t where t.tickid = ?1")
	public void removeTicketById(Long id);
	
	@Modifying
	@Query("update Ticket t set t.tickdesc = ?2, t.tickt = ?3 where t.tickid = ?1")
	public void updateTicket(Long id, String desc, String name);
	

}
