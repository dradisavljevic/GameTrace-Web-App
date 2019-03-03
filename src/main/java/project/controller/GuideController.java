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
import project.domain.Game;
import project.domain.GameUser;
import project.domain.Guide;
import project.domain.Play;
import project.domain.dto.GuideDTO;
import project.domain.dto.GuideKeyDTO;
import project.service.GameService;
import project.service.GameUserService;
import project.service.GuideService;
import project.service.PlayService;

@RequestMapping("/guides")
@Controller
public class GuideController {
	
	@Autowired
	private GuideService guideService;
	
	@Autowired
	private GameUserService gameUserService;
	
	@Autowired
	private GameService gamesService;
	
	@Autowired
	private PlayService playService;
	
	@RequestMapping(value = "/getGuides",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Guide>> getGuides(@Context HttpServletRequest request) {
		
			List<Guide> guides = guideService.findAll();
			

			
			return new ResponseEntity<List<Guide>>(guides, HttpStatus.OK);
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/addGuide",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> addGuide(@Context HttpServletRequest request, @RequestBody Guide data) {
		if (data != null) {
			
			if(data.getGuname() == "" || data.getGuname()==null){
				return new ResponseEntity<String>("Please, fill in the guide name field.", HttpStatus.OK);
			} else if(data.getGucont() == "" || data.getGucont()==null){
				return new ResponseEntity<String>("Please, fill in the guide content field.", HttpStatus.OK);
			}
			
		

			Active current = (Active) request.getSession().getAttribute("user");
			
			
			GameUser user = gameUserService.getGameUserByName(current.getActuser().getUname());
			
			List<Play> playovi = playService.getPlaysByGameAndUser(user.getUname(), data.getGameGameid());
			
			if(playovi==null || playovi.isEmpty()){
				return new ResponseEntity<String>("You must play game atleast once to be able to post a guide for it.", HttpStatus.OK);
			}
			
			data.setGameUser(user);
			data.setGameUserUname(current.getActuser().getUname());

			
			Game game = gamesService.getGameById(data.getGameGameid());
			data.setGame(game);
			
			guideService.save(data);
			


			return new ResponseEntity<String>("Guide successfully added", HttpStatus.OK);

		}
		return null;
		
	}
	
	@Transactional
	@RequestMapping(value = "/filterTitle",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Guide>> filterTitle(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";
			List<Guide> guides = guideService.getAllByName(newData);
			
			Collections.sort(guides, new Comparator() {
	            @Override
	            public int compare(Object guideOne, Object guideTwo) {
	                //use instanceof to verify the references are indeed of the type in question
	                return ((Guide)guideOne).getGuname().toUpperCase()
	                        .compareTo(((Guide)guideTwo).getGuname().toUpperCase());
	            }
	        }); 


				return new ResponseEntity<List<Guide>>(guides, HttpStatus.OK);

		}
		List<Guide> guides = guideService.findAll();
		return new ResponseEntity<List<Guide>>(guides, HttpStatus.OK);
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	@RequestMapping(value = "/getGuidesByGame",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Guide>> getGuidesByGame(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			Long newData = new Long(data);
			List<Guide> guides = guideService.getAllByGame(newData);
			
			Collections.sort(guides, new Comparator() {
	            @Override
	            public int compare(Object guideOne, Object guideTwo) {
	                //use instanceof to verify the references are indeed of the type in question
	                return ((Guide)guideOne).getGuname().toUpperCase()
	                        .compareTo(((Guide)guideTwo).getGuname().toUpperCase());
	            }
	        }); 


			return new ResponseEntity<List<Guide>>(guides, HttpStatus.OK);

		}
		List<Guide> guides = guideService.findAll();
		return new ResponseEntity<List<Guide>>(guides, HttpStatus.OK);
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/filterGame",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Guide>> filterGame(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";
			List<Guide> guides = guideService.getAllByGameName(newData);


				return new ResponseEntity<List<Guide>>(guides, HttpStatus.OK);

		}
		List<Guide> guides = guideService.findAll();
		return new ResponseEntity<List<Guide>>(guides, HttpStatus.OK);
		
	}
	
	@Transactional
	@RequestMapping(value = "/filterUser",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Guide>> filterUser(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";
			List<Guide> guides = guideService.getAllByAuthor(newData);


				return new ResponseEntity<List<Guide>>(guides, HttpStatus.OK);

		}
		List<Guide> guides = guideService.findAll();
		return new ResponseEntity<List<Guide>>(guides, HttpStatus.OK);
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/getGuide",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<Guide> getGuide(@Context HttpServletRequest request, @RequestBody GuideKeyDTO data) {
		
		if (data != null) {
			Long id = new Long(data.getId());
			Long gameId = new Long(data.getGameId());
			String uname = data.getUname();

			Guide guide = guideService.getGuideByKey(id, gameId, uname);

			return new ResponseEntity<Guide>(guide, HttpStatus.OK);

		}
		return null;
		
	}
	
	@Transactional
	@RequestMapping(value = "/update",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> update(@Context HttpServletRequest request, @RequestBody GuideDTO data) {
		if (data != null) {
			
			if(data.getGuname() == "" || data.getGuname()==null){
				return new ResponseEntity<String>("Please, fill in the guide name field.", HttpStatus.OK);
			} else if(data.getGucont() == "" || data.getGucont()==null){
				return new ResponseEntity<String>("Please, fill in the guide content field.", HttpStatus.OK);
			}

			Guide guide = guideService.getGuideByKey(new Long (data.getGuid()), new Long(data.getGameGameid()), data.getGameUserUname());
			
			
			guide.setGucont(data.getGucont());
			
			guide.setGuname(data.getGuname());

			
			guideService.updateGuideContentAndName(guide.getGuid(), guide.getGameGameid(), guide.getGameUserUname(), guide.getGucont(), guide.getGuname());
			


			return new ResponseEntity<String>("Guide successfully updated", HttpStatus.OK);

		}
		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/removeGuide",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<Guide> removeGuide(@Context HttpServletRequest request, @RequestBody GuideKeyDTO data) {

		if (data != null) {
			Long id = new Long(data.getId());
			Long gameId = new Long(data.getGameId());
			Guide guide = guideService.getGuideByKey(id, gameId, data.getUname());
			guideService.removeGuideByKey(id, gameId, data.getUname());
		
				return new ResponseEntity<Guide>(guide, HttpStatus.OK);

		}

		return null;
		
	}
	
	

}
