package project.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.hash.Hashing;

import project.domain.Active;
import project.domain.GtUser;
import project.domain.dto.UserDTO;
import project.service.ActiveService;
import project.service.GtUserService;


@RequestMapping("/login")
@Controller
public class LoginController {
	
	@Autowired
	private ActiveService activeService;
	
	@Autowired
	private GtUserService gtUserService;
	
	
	@Transactional
	@RequestMapping(value="/login",
			method = RequestMethod.POST,
			consumes= MediaType.APPLICATION_JSON,
			produces= MediaType.TEXT_PLAIN)
	public ResponseEntity<String> login(@Context HttpServletRequest request, @RequestBody UserDTO usr) {

		if (usr == null) {
			return new ResponseEntity<String>("Nothing sent", HttpStatus.OK);
		}
		
		if (usr.getUname() == null) {
			return new ResponseEntity<String>("No username", HttpStatus.OK);
		}
		
		if (usr.getPword() == null) {
			return new ResponseEntity<String>("No password", HttpStatus.OK);
		}
		
		HttpHeaders headers = new HttpHeaders();
		
		
		Active current = (Active) request.getSession().getAttribute("user");
		
		if (current != null) {
			if (current.getActuser().getUname().equals(usr.getUname())) {
				return new ResponseEntity<String>("Already logged in", HttpStatus.OK);
			} else {
				activeService.removeActiveById(current.getActid());
				request.getSession().setAttribute("user", null);
			}
		} 
		
		GtUser user = gtUserService.getGtUserByName(usr.getUname());
		
		String sha256hex = Hashing.sha256()
				  .hashString(usr.getPword(), StandardCharsets.UTF_8)
				  .toString();
		
		System.out.println(sha256hex);

		if (user != null) {
			if (sha256hex.equals(user.getPword())) {
				
				List<Active> actives = activeService.findAllActivebyUsername(user.getUname());
				
				for(Active aktovka : actives){
					activeService.removeActiveById(aktovka.getActid());
				}
				
				Active act = new Active();
				act.setActuser(user);
				act = activeService.save(act);
				request.getSession().setAttribute("user", act);
				
				headers.add("Location", "/index.html");
				
				return new ResponseEntity<String>("Logged in:" + user.getUserut().toString(), headers, HttpStatus.OK);
			} 
		} 
		
		return new ResponseEntity<String>("Invalid credentials", headers, HttpStatus.OK);
	}
	
	
	
	@Transactional
	@RequestMapping(value = "/logout",
			method = RequestMethod.GET,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> logout(@Context HttpServletRequest request) {
		
		Active active = (Active) request.getSession().getAttribute("user");
		
		if (active == null) {
			return new ResponseEntity<String>("Not logged in", HttpStatus.OK);
		}
		
		activeService.removeActiveById(active.getActid());
		request.getSession().invalidate();
		
		return new ResponseEntity<String>("Logged out", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/authorize",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> authorize(@Context HttpServletRequest request, @RequestBody String type) {
		
		Active online = (Active) request.getSession().getAttribute("user");
		if (type.equals("index")) {
			if (online == null) {
				
				return new ResponseEntity<String>("Not logged in", HttpStatus.OK);
			}
		}
		
		if (type.equals("login")) {
			if (online != null) {
				return new ResponseEntity<String>("Logged in", HttpStatus.OK);
			}
		}
		
		
		return new ResponseEntity<String>("Authorized", HttpStatus.OK);
	}

}
