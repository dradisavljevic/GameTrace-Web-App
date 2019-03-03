package project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import project.domain.TicketAnswer;

public interface TicketAnswerRepository extends Repository<TicketAnswer, Long> {
	
	public List<TicketAnswer> findAll();
	
	public TicketAnswer save(TicketAnswer t);
	
	@Query("select t from TicketAnswer t where t.tanstick = ?1 ")
	public List<TicketAnswer> getAllByTicket(Long id);
	
	@Query("select t from TicketAnswer t where t.tansresp = ?1 ")
	public List<TicketAnswer> getAllByAdmin(String name);
	
	@Query("select t from TicketAnswer t where t.tansid = ?1 ")
	public TicketAnswer getTicketAnswerById(Long id);
	
	@Modifying
	@Query("delete from TicketAnswer t where t.tansid = ?1")
	public void removeTicketAnswerById(Long id);
	
	@Modifying
	@Query("update TicketAnswer t set t.tanscont = ?2 where t.tansid = ?1")
	public void updateTicketAnswer(Long id, String desc);

}
