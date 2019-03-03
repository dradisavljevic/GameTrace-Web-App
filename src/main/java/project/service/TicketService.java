package project.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import project.domain.Ticket;

public interface TicketService {
	
	List<Ticket> findAll();
	
	Ticket save(Ticket t);
	
	List<Ticket> getAllBySubmitter(String name);
	
	List<Ticket> getAllUnanswered(String status);
	
	Ticket getTicketById(long id);
	
	void removeTicketById(Long id);
	
	void updateTicket(Long id, String desc, String name);
	
	List<Ticket> getAllByTitle(String title);
	
	List<Ticket> getAllByUser(String name);
	
	List<Ticket> getAllByStatus(String status);
	
	List<Ticket> getAllByUserUnanswered(String name, String status);
	
	List<Ticket> getAllByTitleUnanswered(String title, String status);

}
