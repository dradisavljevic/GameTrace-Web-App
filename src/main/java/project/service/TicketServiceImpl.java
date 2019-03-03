package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import project.domain.Ticket;
import project.repository.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService {
	
	@Autowired
	private TicketRepository ticketRepository;

	@Override
	public List<Ticket> findAll() {

		return ticketRepository.findAll();
	}

	@Override
	public Ticket save(Ticket t) {
		Assert.notNull(t);
		return ticketRepository.save(t);
	}

	@Override
	public List<Ticket> getAllBySubmitter(String name) {
		Assert.notNull(name);
		return ticketRepository.getAllBySubmitter(name);
	}

	@Override
	public List<Ticket> getAllUnanswered(String status) {
		Assert.notNull(status);
		return ticketRepository.getAllUnanswered(status);
	}

	@Override
	public Ticket getTicketById(long id) {
		Assert.notNull(id);
		return ticketRepository.getTicketById(id);
	}

	@Override
	public void removeTicketById(Long id) {
		Assert.notNull(id);
		ticketRepository.removeTicketById(id);
	}

	@Override
	public void updateTicket(Long id, String desc, String name) {
		Assert.notNull(id);
		Assert.notNull(desc);
		Assert.notNull(name);
		ticketRepository.updateTicket(id, desc, name);
	}

	@Override
	public List<Ticket> getAllByTitle(String title) {
		Assert.notNull(title);
		return ticketRepository.getAllByTitle(title);
	}

	@Override
	public List<Ticket> getAllByUser(String name) {
		Assert.notNull(name);
		return ticketRepository.getAllByUser(name);
	}

	@Override
	public List<Ticket> getAllByStatus(String status) {
		Assert.notNull(status);
		return ticketRepository.getAllByStatus(status);
	}

	@Override
	public List<Ticket> getAllByUserUnanswered(String name, String status) {
		Assert.notNull(name);
		Assert.notNull(status);
		return ticketRepository.getAllByUserUnanswered(name, status);
	}

	@Override
	public List<Ticket> getAllByTitleUnanswered(String title, String status) {
		Assert.notNull(title);
		Assert.notNull(status);
		return ticketRepository.getAllByTitleUnanswered(title, status);
	}

}
