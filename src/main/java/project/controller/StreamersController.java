package project.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import project.domain.GameDeveloper;
import project.domain.GameGroup;
import project.domain.GtUser;
import project.domain.Item;
import project.domain.Streamer;
import project.domain.Ticket;
import project.domain.dto.ItemDTO;
import project.domain.dto.ItemKeyDTO;
import project.domain.dto.StreamerDTO;
import project.service.GameGroupService;
import project.service.GameService;
import project.service.StreamerService;

@RequestMapping("/streamers")
@Controller
public class StreamersController {
	
	@Autowired
	private StreamerService streamerService;
	
	@Autowired
	private GameService gamesService;
	
	@RequestMapping(value = "/getStreamers",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Streamer>> getStreamers(@Context HttpServletRequest request) {
		
			List<Streamer> streamers = streamerService.findAll();
			

			
			return new ResponseEntity<List<Streamer>>(streamers, HttpStatus.OK);
		
	}
	
	@Transactional
	@RequestMapping(value = "/getStreamer",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<Streamer> getStreamer(@Context HttpServletRequest request, @RequestBody String data) {
		
		if (data != null) {
			Long id = new Long(data);

			Streamer streamer = streamerService.getStreamerById(id);

			return new ResponseEntity<Streamer>(streamer, HttpStatus.OK);

		}
		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/save",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> save(@Context HttpServletRequest request, @RequestBody StreamerDTO data) {
		
		if (data != null) {
			if(data.getStrname() == "" || data.getStrname()==null){
				return new ResponseEntity<String>("Please, fill in the streamer name field.", HttpStatus.OK);
			} else if(data.getStrurl() == "" || data.getStrurl()==null){
				return new ResponseEntity<String>("Please, fill in the streamer url field.", HttpStatus.OK);
			} else if(data.getGames().isEmpty()){
				return new ResponseEntity<String>("Please, select atleast one game the desired streamer streams.", HttpStatus.OK);
			} 
			
			if(streamerService.getStreamerByNameAndUrl(data.getStrname(), data.getStrurl())!=null){
				return new ResponseEntity<String>("The desired streamer already exists within our database.", HttpStatus.OK);
			}
			
			Streamer streamer = new Streamer();
			ArrayList<Game> games = new ArrayList<Game>();
			ArrayList<String> DTOgames = data.getGames();
			for (String temp : DTOgames) {
				Game game = gamesService.getGameById(new Long(temp));
				games.add(game);
			}
			streamer.setGames(games);
			streamer.setStrname(data.getStrname());
			streamer.setStrurl(data.getStrurl());
			
			for (Game game : streamer.getGames()) {
				if(!game.getStreamers().contains(streamer)){
					game.getStreamers().add(streamer);
				}
			}
			
			streamerService.save(streamer);


			return new ResponseEntity<String>("Streamer successfully added", HttpStatus.OK);

		}
		return null;
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@RequestMapping(value = "/filterName",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Streamer>> filterName(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";
			List<Streamer> streamers = streamerService.getAllByName(newData);
			
			Collections.sort(streamers, new Comparator() {
	            @Override
	            public int compare(Object streamerOne, Object streamerTwo) {
	                //use instanceof to verify the references are indeed of the type in question
	                return ((Streamer)streamerOne).getStrname().toUpperCase()
	                        .compareTo(((Streamer)streamerTwo).getStrname().toUpperCase());
	            }
	        }); 


				return new ResponseEntity<List<Streamer>>(streamers, HttpStatus.OK);

		}
		List<Streamer> streamers = streamerService.findAll();
		return new ResponseEntity<List<Streamer>>(streamers, HttpStatus.OK);
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/update",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> update(@Context HttpServletRequest request, @RequestBody StreamerDTO data) {
		if (data != null) {
			
			if(data.getStrname() == "" || data.getStrname()==null){
				return new ResponseEntity<String>("Please, fill in the streamer name field.", HttpStatus.OK);
			} else if(data.getStrurl() == "" || data.getStrurl()==null){
				return new ResponseEntity<String>("Please, fill in the streamer url field.", HttpStatus.OK);
			} else if(data.getGames().isEmpty()){
				return new ResponseEntity<String>("Please, select atleast one game the desired streamer streams.", HttpStatus.OK);
			} 
			
			
			
			Streamer streamer = streamerService.getStreamerById(new Long(data.getStrid()));
			
			if(streamerService.getStreamerByNameAndUrl(data.getStrname(), data.getStrurl())!=null && (!data.getStrname().equals(streamer.getStrname()) || !data.getStrurl().equals(streamer.getStrurl()))){
				return new ResponseEntity<String>("The desired streamer already exists within our database.", HttpStatus.OK);
			}
			
			streamer.setStrname(data.getStrname());
			
			streamer.setStrurl(data.getStrurl());
			
			for (Game game : streamer.getGames()){
				game.getStreamers().remove(streamer);
			}
			
			streamer.getGames().clear();
			
			ArrayList<Game> games = new ArrayList<Game>();
			ArrayList<String> DTOgames = data.getGames();
			for (String temp : DTOgames) {
				Game game = gamesService.getGameById(new Long(temp));
				games.add(game);
			}
			streamer.setGames(games);
			
			for (Game game : streamer.getGames()) {
				if(!game.getStreamers().contains(streamer)){
					game.getStreamers().add(streamer);
				}
			}
			
			streamerService.updateStreamer(streamer.getStrid(), streamer.getStrname(), streamer.getStrurl());
			
			
			return new ResponseEntity<String>("Streamer successfully updated", HttpStatus.OK);

		}
		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/removeStreamer",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<Streamer> removeStreamer(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			Long id = new Long(data);
			Streamer streamer = streamerService.getStreamerById(id);
			streamerService.removeStreamerById(id);
		
				return new ResponseEntity<Streamer>(streamer, HttpStatus.OK);

		}

		return null;
		
	}
	
	

}
