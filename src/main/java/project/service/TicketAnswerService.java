package project.service;

import java.util.List;

import project.domain.TicketAnswer;

public interface TicketAnswerService {
	
	List<TicketAnswer> findAll();
	
	TicketAnswer save(TicketAnswer t);
	
	List<TicketAnswer> getAllByTicket(Long id);
	
	List<TicketAnswer> getAllByAdmin(String name);
	
	TicketAnswer getTicketAnswerById(Long id);
	
	void removeTicketAnswerById(Long id);
	
	void updateTicketAnswer(Long id, String desc);

}
