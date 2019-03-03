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

import project.domain.Game;
import project.domain.GameDeveloper;
import project.domain.Player;
import project.domain.dto.DeveloperDTO;
import project.domain.dto.GameDTO;
import project.domain.dto.GameUpdateDTO;
import project.domain.dto.ItemKeyDTO;
import project.service.GameDeveloperService;

@RequestMapping("/developers")
@Controller
public class DevelopersController {
	
	@Autowired
	private GameDeveloperService gamedevService;
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getDevelopers",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<GameDeveloper>> getDevelopers(@Context HttpServletRequest request) {
		
			List<GameDeveloper> developers = gamedevService.findAll();
			
			Collections.sort(developers, new Comparator() {
	            @Override
	            public int compare(Object devOne, Object devTwo) {
	                //use instanceof to verify the references are indeed of the type in question
	                return ((GameDeveloper)devOne).getGdname().toUpperCase()
	                        .compareTo(((GameDeveloper)devTwo).getGdname().toUpperCase());
	            }
	        }); 

			
			return new ResponseEntity<List<GameDeveloper>>(developers, HttpStatus.OK);
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getDevelopersZeroless",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<GameDeveloper>> getDevelopersWithoutZero(@Context HttpServletRequest request) {
		
			List<GameDeveloper> developers = gamedevService.getAll();
			
			Collections.sort(developers, new Comparator() {
	            @Override
	            public int compare(Object devOne, Object devTwo) {
	                //use instanceof to verify the references are indeed of the type in question
	                return ((GameDeveloper)devOne).getGdname().toUpperCase()
	                        .compareTo(((GameDeveloper)devTwo).getGdname().toUpperCase());
	            }
	        }); 

			
			return new ResponseEntity<List<GameDeveloper>>(developers, HttpStatus.OK);
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	@RequestMapping(value = "/filterName",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<GameDeveloper>> filterName(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";

			List<GameDeveloper> developers = gamedevService.getAllByName(newData);
			
			Collections.sort(developers, new Comparator() {
	            @Override
	            public int compare(Object devOne, Object devTwo) {
	                //use instanceof to verify the references are indeed of the type in question
	                return ((GameDeveloper)devOne).getGdname().toUpperCase()
	                        .compareTo(((GameDeveloper)devTwo).getGdname().toUpperCase());
	            }
	        }); 
		
				return new ResponseEntity<List<GameDeveloper>>(developers, HttpStatus.OK);

		}
		List<GameDeveloper> developers = gamedevService.getAll();
		return new ResponseEntity<List<GameDeveloper>>(developers, HttpStatus.OK);
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/filterCount",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<GameDeveloper>> filterCount(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			BigDecimal count = new BigDecimal(data);
			List<GameDeveloper> developers = gamedevService.getAllByGreaterCount(count);
			
			Collections.sort(developers, new Comparator() {
	            @Override
	            public int compare(Object devOne, Object devTwo) {
	                //use instanceof to verify the references are indeed of the type in question
	                return ((GameDeveloper)devOne).getGdname().toUpperCase()
	                        .compareTo(((GameDeveloper)devTwo).getGdname().toUpperCase());
	            }
	        }); 


				return new ResponseEntity<List<GameDeveloper>>(developers, HttpStatus.OK);

		}
		List<GameDeveloper> developers = gamedevService.getAll();
		return new ResponseEntity<List<GameDeveloper>>(developers, HttpStatus.OK);
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/getDeveloper",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<GameDeveloper> getDeveloper(@Context HttpServletRequest request, @RequestBody String data) {
		
		if (data != null) {
			Long id = new Long(data);
			GameDeveloper developer = gamedevService.getGameDeveloperById(id);

			return new ResponseEntity<GameDeveloper>(developer, HttpStatus.OK);

		}
		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/save",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> save(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			if(data == "" || data==null){
				return new ResponseEntity<String>("Please, fill in the developer name field.", HttpStatus.OK);
			}
			
			if(gamedevService.getGameDeveloperByName(data)!=null){
				return new ResponseEntity<String>("Game developer with the desired name already exists.", HttpStatus.OK);
			}
			
			GameDeveloper developer = new GameDeveloper();
			developer.setGdcount(new BigDecimal(0));
			developer.setGdname(data);
			
			gamedevService.save(developer);


			return new ResponseEntity<String>("Game developer successfully added", HttpStatus.OK);

		}
		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/update",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> update(@Context HttpServletRequest request, @RequestBody DeveloperDTO data) {
		if (data != null) {
			
			if(data.getName() == "" || data.getName()==null){
				return new ResponseEntity<String>("Please, fill in the developer name field.", HttpStatus.OK);
			}
			
			
			
			GameDeveloper developer = gamedevService.getGameDeveloperById(data.getId());
			
			if(gamedevService.getGameDeveloperByName(data.getName())!=null && !developer.getGdname().equals(data.getName())){
				return new ResponseEntity<String>("Game developer with the desired name already exists.", HttpStatus.OK);
			}
			
			developer.setGdname(data.getName());

			gamedevService.updateGameDeveloperName(data.getId(), data.getName());
			
			return new ResponseEntity<String>("Game developer successfully updated", HttpStatus.OK);

		}
		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/removeDeveloper",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<GameDeveloper> removeDeveloper(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			Long id = new Long(data);
			GameDeveloper developer = gamedevService.getGameDeveloperById(id);
			if(developer.getGames().isEmpty()){
				gamedevService.removeGameDeveloperById(id);
			}



				return new ResponseEntity<GameDeveloper>(developer, HttpStatus.OK);

		}

		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/getDevelopedGames",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Game>> getDevelopedGames(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			Long id = new Long(data);
			List<Game> games = gamedevService.getDevelopedGames(id);


				return new ResponseEntity<List<Game>>(games, HttpStatus.OK);

		}
		return null;
		
	}
	

}
