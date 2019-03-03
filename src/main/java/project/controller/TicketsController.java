package project.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import project.domain.Active;
import project.domain.Administrator;
import project.domain.Game;
import project.domain.GameUser;
import project.domain.Guide;
import project.domain.Ticket;
import project.domain.TicketAnswer;
import project.domain.dto.AnswerDTO;
import project.domain.dto.GuideKeyDTO;
import project.domain.dto.TicketDTO;
import project.service.AdministratorService;
import project.service.GameUserService;
import project.service.TicketAnswerService;
import project.service.TicketService;

@RequestMapping("/tickets")
@Controller
public class TicketsController {
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private TicketAnswerService answerService;
	
	@Autowired
	private GameUserService gameUserService;
	
	@Autowired
	private AdministratorService administratorService;
	
	

	@RequestMapping(value = "/getMyTickets",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Ticket>> getMyTickets(@Context HttpServletRequest request) {
		
			Active current = (Active) request.getSession().getAttribute("user");
		
		
			List<Ticket> tickets = ticketService.getAllBySubmitter(current.getActuser().getUname());
			
			
			
			
			return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/getAllTickets",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Ticket>> getAllTickets(@Context HttpServletRequest request) {
		
		
			List<Ticket> tickets = ticketService.findAll();
			
			
			
			
			return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/getAllUnanswered",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Ticket>> getAllUnanswered(@Context HttpServletRequest request) {
		
		
			List<Ticket> tickets = ticketService.getAllUnanswered("SUBMITTED");
			
			
			
			
			return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/removeTicket",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<Ticket> removeTicket(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			Long id = new Long(data);
			
			Ticket ticket = ticketService.getTicketById(id);
			
			List<TicketAnswer> answers = answerService.getAllByTicket(id);
			
			for(TicketAnswer answer : answers){
				Administrator admin = administratorService.getAdministratorByName(answer.getTansresp());
				admin.getTicketAnswers().remove(answer);
				answerService.removeTicketAnswerById(answer.getTansid());
			}
			
			GameUser gu = ticket.getGameUser();
			gu.getTickets().remove(ticket);
			gameUserService.save(gu);

			ticketService.removeTicketById(id);
		
				return new ResponseEntity<Ticket>(ticket, HttpStatus.OK);

		}

		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/getTicket",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<Ticket> getTicket(@Context HttpServletRequest request, @RequestBody String data) {
		
		if (data != null) {
			Long id = new Long(data);

			Ticket ticket = ticketService.getTicketById(id);

			return new ResponseEntity<Ticket>(ticket, HttpStatus.OK);

		}
		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/addTicket",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> addTicket(@Context HttpServletRequest request, @RequestBody TicketDTO data) {
		if (data != null) {
			
			if(data.getName() == "" || data.getName()==null){
				return new ResponseEntity<String>("Please, fill in the ticket title field.", HttpStatus.OK);
			} else if(data.getDescription() == "" || data.getDescription()==null){
				return new ResponseEntity<String>("Please, fill in the ticket description field.", HttpStatus.OK);
			}

			Active current = (Active) request.getSession().getAttribute("user");
			
			
			GameUser user = gameUserService.getGameUserByName(current.getActuser().getUname());
			Ticket ticket = new Ticket();
			ticket.setTickdesc(data.getDescription());
			ticket.setTickt(data.getName());
			ticket.setTickstat("SUBMITTED");
			ticket.setGameUser(user);
			ticket.setTicksub(user.getUname());

			
			
			ticketService.save(ticket);
			


			return new ResponseEntity<String>("Ticket successfully submitted", HttpStatus.OK);

		}
		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/update",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> update(@Context HttpServletRequest request, @RequestBody TicketDTO data) {
		if (data != null) {
			
			if(data.getName() == "" || data.getName()==null){
				return new ResponseEntity<String>("Please, fill in the ticket title field.", HttpStatus.OK);
			} else if(data.getDescription() == "" || data.getDescription()==null){
				return new ResponseEntity<String>("Please, fill in the ticket description field.", HttpStatus.OK);
			}
			
			
			Ticket ticket = ticketService.getTicketById(new Long(data.getId()));
			ticket.setTickdesc(data.getDescription());
			ticket.setTickt(data.getName());
			
			
			ticketService.save(ticket);
			


			return new ResponseEntity<String>("Ticket successfully updated", HttpStatus.OK);

		}
		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/addAnswer",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> addAnswer(@Context HttpServletRequest request, @RequestBody AnswerDTO data) {
		if (data != null) {
			
			if(data.getContent() == "" || data.getContent()==null){
				return new ResponseEntity<String>("Please, fill in the answer content field.", HttpStatus.OK);
			} 

			Long id = new Long(data.getId());
			
			Ticket ticket = ticketService.getTicketById(id);
			
			Active current = (Active) request.getSession().getAttribute("user");
			
			Administrator admin = administratorService.getAdministratorByName(current.getActuser().getUname());
			
			TicketAnswer answer = new TicketAnswer();
			
			answer.setAdministrator(admin);
			answer.setTanscont(data.getContent());
			answer.setTansresp(admin.getUname());
			answer.setTanstick(id);
			answer.setTicket(ticket);
			
			ticket.getTicketAnswers().add(answer);
			ticket.setTickstat("ANSWERED");
			
			answerService.save(answer);
			ticketService.save(ticket);
			


			return new ResponseEntity<String>("Ticket successfully answered", HttpStatus.OK);

		}
		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/getAnswer",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<TicketAnswer> getAnswer(@Context HttpServletRequest request, @RequestBody String data) {
		
		if (data != null) {
			Long id = new Long(data);

			TicketAnswer answer = answerService.getTicketAnswerById(id);

			return new ResponseEntity<TicketAnswer>(answer, HttpStatus.OK);

		}
		return null;
		
	}
	
	@Transactional
	@RequestMapping(value = "/updateAnswer",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> updateAnswer(@Context HttpServletRequest request, @RequestBody AnswerDTO data) {
		if (data != null) {
			
			if(data.getContent() == "" || data.getContent()==null){
				return new ResponseEntity<String>("Please, fill in the answer content field.", HttpStatus.OK);
			} 

			Long id = new Long(data.getId());
			
			TicketAnswer answer = answerService.getTicketAnswerById(id);
			
			
			Ticket ticket = ticketService.getTicketById(answer.getTanstick());
			ticket.getTicketAnswers().remove(answer);
			answer.setTanscont(data.getContent());
			
			ticket.getTicketAnswers().add(answer);
			
			answerService.save(answer);
			ticketService.save(ticket);
			


			return new ResponseEntity<String>("Answer successfully updated", HttpStatus.OK);

		}
		return null;
		
	}
	
	@Transactional
	@RequestMapping(value = "/getTicketAnswerByTicket",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<TicketAnswer>> getTicketAnswerByTicket(@Context HttpServletRequest request, @RequestBody String data) {
		
		if (data != null) {
			Long id = new Long(data);

			List<TicketAnswer> answers = answerService.getAllByTicket(id);

			return new ResponseEntity<List<TicketAnswer>>(answers, HttpStatus.OK);

		}
		return null;
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	@RequestMapping(value = "/filterTitle",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Ticket>> filterTitle(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";
			List<Ticket> tickets = ticketService.getAllByTitle(newData);
			
			Collections.sort(tickets, new Comparator() {
	            @Override
	            public int compare(Object ticketOne, Object ticketTwo) {
	                //use instanceof to verify the references are indeed of the type in question
	                return ((Ticket)ticketOne).getTickt().toUpperCase()
	                        .compareTo(((Ticket)ticketTwo).getTickt().toUpperCase());
	            }
	        }); 


				return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);

		}
		List<Ticket> tickets = ticketService.findAll();
		return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
		
	}
	
	@Transactional
	@RequestMapping(value = "/filterStatus",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Ticket>> filterStatus(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";
			List<Ticket> tickets = ticketService.getAllByStatus(newData);


				return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);

		}
		List<Ticket> tickets = ticketService.findAll();
		return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
		
	}
	
	@Transactional
	@RequestMapping(value = "/filterSubmit",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Ticket>> filterSubmit(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";
			List<Ticket> tickets = ticketService.getAllByUser(newData);


				return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);

		}
		List<Ticket> tickets = ticketService.findAll();
		return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
		
	}
	
	@Transactional
	@RequestMapping(value = "/filterTitleUnanswered",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Ticket>> filterTitleUnanswered(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";
			List<Ticket> tickets = ticketService.getAllByTitleUnanswered(newData,"SUBMITTED");


				return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);

		}
		List<Ticket> tickets = ticketService.getAllUnanswered("SUBMITTED");
		return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
		
	}
	
	@Transactional
	@RequestMapping(value = "/filterSubmitUnanswered",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Ticket>> filterSubmitUnanswered(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";
			List<Ticket> tickets = ticketService.getAllByUserUnanswered(newData,"SUBMITTED");


				return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);

		}
		List<Ticket> tickets = ticketService.getAllUnanswered("SUBMITTED");
		return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
		
	}

}
