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
import project.domain.GameUser;
import project.domain.GtUser;
import project.domain.Item;
import project.domain.dto.GameDTO;
import project.domain.dto.GroupDTO;
import project.domain.dto.ItemDTO;
import project.domain.dto.ItemKeyDTO;
import project.service.GameGroupService;
import project.service.GameService;
import project.service.GameUserService;
import project.service.GtUserService;

@RequestMapping("/groups")
@Controller
public class GroupController {
	
	@Autowired
	private GameGroupService gameGroupService;
	
	@Autowired
	private GtUserService gtUserService;
	
	@Autowired
	private GameUserService gameUserService;
	
	
	@RequestMapping(value = "/getGroups",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<GameGroup>> getGroups(@Context HttpServletRequest request) {
		
			List<GameGroup> groups = gameGroupService.findAll();
			

			
			return new ResponseEntity<List<GameGroup>>(groups, HttpStatus.OK);
		
	}
	
	@Transactional
	@RequestMapping(value = "/getGroup",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<GameGroup> getGroup(@Context HttpServletRequest request, @RequestBody ItemKeyDTO data) {
		
		if (data != null) {
			Long id = new Long(data.getId());
			String uname = data.getUname();
			GameGroup group = gameGroupService.getGameGroupByKey(id, uname);



			return new ResponseEntity<GameGroup>(group, HttpStatus.OK);

		}
		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/addGroup",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> addGroup(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			
			if(data == "" || data==null){
				return new ResponseEntity<String>("Please, fill in the group name field.", HttpStatus.OK);
			}
			
			if(gameGroupService.getGameGroupByName(data)!=null){
				return new ResponseEntity<String>("Game group with the desired name already exists.", HttpStatus.OK);
			}
			
			GameGroup gameGroup = new GameGroup();
			
			Active current = (Active) request.getSession().getAttribute("user");
			
			
			GtUser user = gtUserService.getGtUserByName(current.getActuser().getUname());
			
			gameGroup.setGrgm(current.getActuser().getUname());
			
			gameGroup.setGtUser(user);
			
			
			gameGroup.setGrname(data);
			
			gameGroupService.save(gameGroup);


			return new ResponseEntity<String>("Group successfully added", HttpStatus.OK);

		}
		return null;
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@RequestMapping(value = "/filterName",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<GameGroup>> filterName(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";
			List<GameGroup> groups = gameGroupService.getAllByName(newData);
			
			Collections.sort(groups, new Comparator() {
	            @Override
	            public int compare(Object groupOne, Object groupTwo) {
	                //use instanceof to verify the references are indeed of the type in question
	                return ((GameGroup)groupOne).getGrname().toUpperCase()
	                        .compareTo(((GameGroup)groupTwo).getGrname().toUpperCase());
	            }
	        }); 
			


				return new ResponseEntity<List<GameGroup>>(groups, HttpStatus.OK);

		}
		List<GameGroup> groups = gameGroupService.findAll();
		return new ResponseEntity<List<GameGroup>>(groups, HttpStatus.OK);
		
	}
	
	@Transactional
	@RequestMapping(value = "/filterMaster",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<GameGroup>> filterMaster(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";
			List<GameGroup> groups = gameGroupService.getAllByMaster(newData);


				return new ResponseEntity<List<GameGroup>>(groups, HttpStatus.OK);

		}
		List<GameGroup> groups = gameGroupService.findAll();
		return new ResponseEntity<List<GameGroup>>(groups, HttpStatus.OK);
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/update",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> update(@Context HttpServletRequest request, @RequestBody GroupDTO data) {
		if (data != null) {
			
			if(data.getGrname() == "" || data.getGrname()==null){
				return new ResponseEntity<String>("Please, fill in the group name field.", HttpStatus.OK);
			}
			
			
			
			GameGroup grp = gameGroupService.getGameGroupByKey(new Long(data.getId()), data.getUname());
			
			if(gameGroupService.getGameGroupByName(data.getGrname())!=null && !grp.getGrname().equals(data.getGrname())){
				return new ResponseEntity<String>("Game group with the desired name already exists.", HttpStatus.OK);
			}
			
			grp.setGrname(data.getGrname());
			
			gameGroupService.updateGameGroupName(new Long(data.getId()), data.getUname(), grp.getGrname());
			
			
			return new ResponseEntity<String>("Group successfully updated", HttpStatus.OK);

		}
		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/removeGroup",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<GameGroup> removeGroup(@Context HttpServletRequest request, @RequestBody ItemKeyDTO data) {

		if (data != null) {
			Long id = new Long(data.getId());
			GameGroup group = gameGroupService.getGameGroupByKey(id, data.getUname());
			gameGroupService.removeGameGroupByKey(id, data.getUname());
		
				return new ResponseEntity<GameGroup>(group, HttpStatus.OK);

		}

		return null;
		
	}
	
	@RequestMapping(value = "/getGroupsByGM",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<GameGroup>> getGroupsByGM(@Context HttpServletRequest request) {
		
		Active current = (Active) request.getSession().getAttribute("user");
		
		GtUser active = current.getActuser();
		
		GameUser master = gameUserService.getGameUserByName(active.getUname());
		
		List<GameGroup> groups = gameGroupService.getAllByMaster(master.getUname());
			

			
		return new ResponseEntity<List<GameGroup>>(groups, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/getGroupsByGMAndMember",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<GameGroup>> getGroupsByGMAndMember(@Context HttpServletRequest request, @RequestBody String data) {
		
		if(data!=null){
		Active current = (Active) request.getSession().getAttribute("user");
		
		GtUser active = current.getActuser();
		
		GameUser master = gameUserService.getGameUserByName(active.getUname());
		
		List<GameGroup> groups = gameGroupService.getAllByMaster(master.getUname());
		
		GameUser receiver = gameUserService.getGameUserByName(data);
		
		List<GameGroup> grupe = new ArrayList<GameGroup>();
		
		for(GameGroup grp : groups){
			if(!grp.getGameUsers().contains(receiver)){
				grupe.add(grp);
			}
		}
		
		return new ResponseEntity<List<GameGroup>>(grupe, HttpStatus.OK);
		}
			

			return null;
		
		
	}
	

}
