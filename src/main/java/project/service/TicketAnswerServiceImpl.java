package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import project.domain.TicketAnswer;
import project.repository.TicketAnswerRepository;

@Service
public class TicketAnswerServiceImpl implements TicketAnswerService {
	
	@Autowired
	private TicketAnswerRepository ticketAnswerRepository;

	@Override
	public List<TicketAnswer> findAll() {
		return ticketAnswerRepository.findAll();
	}

	@Override
	public TicketAnswer save(TicketAnswer t) {
		Assert.notNull(t);
		return ticketAnswerRepository.save(t);
	}

	@Override
	public List<TicketAnswer> getAllByTicket(Long id) {
		Assert.notNull(id);
		return ticketAnswerRepository.getAllByTicket(id);
	}

	@Override
	public List<TicketAnswer> getAllByAdmin(String name) {
		Assert.notNull(name);
		return ticketAnswerRepository.getAllByAdmin(name);
	}

	@Override
	public TicketAnswer getTicketAnswerById(Long id) {
		Assert.notNull(id);
		return ticketAnswerRepository.getTicketAnswerById(id);
	}

	@Override
	public void removeTicketAnswerById(Long id) {
		Assert.notNull(id);
		ticketAnswerRepository.removeTicketAnswerById(id);
	}

	@Override
	public void updateTicketAnswer(Long id, String desc) {
		Assert.notNull(id);
		Assert.notNull(desc);
		ticketAnswerRepository.updateTicketAnswer(id, desc);
	}

}
