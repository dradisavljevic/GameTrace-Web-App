package project.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import project.domain.Active;
import project.domain.Administrator;
import project.domain.Game;
import project.domain.GameDeveloper;
import project.domain.GameUser;
import project.domain.GtUser;
import project.domain.Request;
import project.domain.dto.GameDTO;
import project.domain.dto.RequestUpdateDTO;
import project.service.AdministratorService;
import project.service.GameDeveloperService;
import project.service.GameService;
import project.service.GameUserService;
import project.service.GtUserService;
import project.service.RequestService;

@RequestMapping("/requests")
@Controller
public class RequestsController {
	
	@Autowired
	private RequestService requestService;
	
	@Autowired
	private GameDeveloperService gamedevService;
	
	@Autowired
	private GameUserService gameUserService;
	
	@Autowired
	private GtUserService gtUserService;
	
	@Autowired
	private GameService gamesService;
	
	@Autowired
	private AdministratorService adminService;
	
	@RequestMapping(value = "/getRequests",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Request>> getAllRequests(@Context HttpServletRequest request) {
		
			List<Request> gamerequests = requestService.findAll();
	
			
			return new ResponseEntity<List<Request>>(gamerequests, HttpStatus.OK);
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	@RequestMapping(value = "/filterName",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Request>> filterGameName(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";
			List<Request> requests = requestService.getAllByName(newData);

			Collections.sort(requests, new Comparator() {
	            @Override
	            public int compare(Object reqOne, Object reqTwo) {

	                return ((Request)reqOne).getReqgname().toUpperCase()
	                        .compareTo(((Request)reqTwo).getReqgname().toUpperCase());
	            }
	        }); 
			

				return new ResponseEntity<List<Request>>(requests, HttpStatus.OK);

		}
		List<Request> requests = requestService.findAll();
		return new ResponseEntity<List<Request>>(requests, HttpStatus.OK);
		
	}
	
	@Transactional
	@RequestMapping(value = "/filterYear",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Request>> filterYear(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			int year = Integer.parseInt(data);
			BigDecimal godina = new BigDecimal(year);
			List<Request> requests = requestService.getAllByYear(godina);


				return new ResponseEntity<List<Request>>(requests, HttpStatus.OK);

		}
		List<Request> requests = requestService.findAll();
		return new ResponseEntity<List<Request>>(requests, HttpStatus.OK);
		
	}
	
	@Transactional
	@RequestMapping(value = "/filterUser",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Request>> filterUser(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";
			List<Request> requests = requestService.getAllBySubmitter(newData);


				return new ResponseEntity<List<Request>>(requests, HttpStatus.OK);

		}
		List<Request> requests = requestService.findAll();
		return new ResponseEntity<List<Request>>(requests, HttpStatus.OK);
		
	}
	
	@Transactional
	@RequestMapping(value = "/filterStatus",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Request>> filterStatus(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";
			List<Request> requests = requestService.getAllByStatus(newData);


				return new ResponseEntity<List<Request>>(requests, HttpStatus.OK);

		}
		List<Request> requests = requestService.findAll();
		return new ResponseEntity<List<Request>>(requests, HttpStatus.OK);
		
	}
	
	
	
	@Transactional
	@RequestMapping(value = "/save",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> save(@Context HttpServletRequest request, @RequestBody GameDTO data) {
		if (data != null) {
			
			if(data.getGamename() == "" || data.getGamename()==null){
				return new ResponseEntity<String>("Please, fill in the requested game name field.", HttpStatus.OK);
			} else if ( data.getGamedesc()=="" || data.getGamedesc()==null){
				return new ResponseEntity<String>("Please, fill in the requested game description.", HttpStatus.OK);
			} else if (data.getGameDevelopers().isEmpty()){
				return new ResponseEntity<String>("Please, select at least one developer.", HttpStatus.OK);
			} else if (data.getGamery()=="" || data.getGamery()== null){
				return new ResponseEntity<String>("Please, properly select the release year.", HttpStatus.OK);
			} else if (data.getGamedr() == "" || data.getGamedr() == null){
				return new ResponseEntity<String>("Please, fill in the requested game detection rule.", HttpStatus.OK);
			} else if( data.getGimg() == "" || data.getGimg() == null){
				return new ResponseEntity<String>("Please, select the requested game image.", HttpStatus.OK);
			}
			
			
			if(!data.getGamedr().contains("<game>")){
				return new ResponseEntity<String>("Detection rule not formatted correctly. Missing <game> opening tag.", HttpStatus.OK);
			} else if(!data.getGamedr().contains("</game>")){
				return new ResponseEntity<String>("Detection rule not formatted correctly. Missing </game> closing tag.", HttpStatus.OK);
			} else if(!data.getGamedr().contains("<rule>")){
				return new ResponseEntity<String>("Detection rule not formatted correctly. Missing <rule> opening tag.", HttpStatus.OK);
			} else if(!data.getGamedr().contains("</rule>")){
				return new ResponseEntity<String>("Detection rule not formatted correctly. Missing </rule> closing tag.", HttpStatus.OK);
			} else if(!data.getGamedr().contains("<proc>")){
				return new ResponseEntity<String>("Detection rule not formatted correctly. Missing <proc> opening tag.", HttpStatus.OK);
			} else if(!data.getGamedr().contains("</proc>")){
				return new ResponseEntity<String>("Detection rule not formatted correctly. Missing </proc> closing tag.", HttpStatus.OK);
			} else if(!data.getGamedr().contains("<file>")){
				return new ResponseEntity<String>("Detection rule not formatted correctly. Missing <file> opening tag.", HttpStatus.OK);
			} else if(!data.getGamedr().contains("</file>")){
				return new ResponseEntity<String>("Detection rule not formatted correctly. Missing </file> closing tag.", HttpStatus.OK);
			}
			
			try {
			    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			    DocumentBuilder builder = factory.newDocumentBuilder();
			    InputSource is = new InputSource(new StringReader(data.getGamedr()));
			    Document doc = builder.parse(is);
				} catch (Exception e) {
					return new ResponseEntity<String>("Detection rule formated incorrectly. Make sure all opening tags have matching closing ones.", HttpStatus.OK);
				}
			
			
			
			String sub = data.getGamedr().substring(data.getGamedr().indexOf("</game>")+7).trim();
			if(!sub.equals("")){
				return new ResponseEntity<String>("There shouldn't be anything after the </game> closing tag.", HttpStatus.OK);
			}

			
			
			if(gamesService.getGameByNameAndYear(data.getGamename(), new BigDecimal(data.getGamery()))!=null){
				return new ResponseEntity<String>("Game with the desired name and release year already exists.", HttpStatus.OK);
			}
			
			Request gameRequest = new Request();
			gameRequest.setReqdesc(data.getGamedesc());
			ArrayList<GameDeveloper> developers = new ArrayList<GameDeveloper>();
			ArrayList<String> devs = data.getGameDevelopers();
			for (String temp : devs) {
				GameDeveloper developer = gamedevService.getGameDeveloperByName(temp);
				developers.add(developer);
			}
			
			gameRequest.setGameDevelopers(developers);
			gameRequest.setReqdr(data.getGamedr());
			gameRequest.setReqgname(data.getGamename());
			
			Active current = (Active) request.getSession().getAttribute("user");
			
			
			GameUser user = gameUserService.getGameUserByName(current.getActuser().getUname());
			
			if(requestService.getAlreadySubmitted(data.getGamename(), new BigDecimal(data.getGamery()), user.getUname())!=null){
				return new ResponseEntity<String>("You've already submitted request for this game. Consider updating your previous one.", HttpStatus.OK);
			}
			
			gameRequest.setGameUser(user);
			gameRequest.setReqgrd(new BigDecimal(data.getGamery()));
			
			

			gameRequest.setReqstatus("PENDING");
			
			
			for (GameDeveloper developer : gameRequest.getGameDevelopers()) {
				if(!developer.getRequests().contains(gameRequest)){
					developer.getRequests().add(gameRequest);
				}
			}
			requestService.save(gameRequest);
			
			if(data.getGimg()!=null && data.getGimg()!=""){
				String base64Image = data.getGimg().split(",")[1];
				byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
				final String dir = System.getProperty("user.dir");
	
				Long sequence = gameRequest.getReqid();
				String imagePath = "../../data/requests/" + sequence + ".jpg";
				gameRequest.setReqimg(imagePath);
				String savePath = dir + "\\src\\main\\resources\\static\\data\\requests\\"+sequence+".jpg";
				String savePath2 = dir + "\\target\\classes\\static\\data\\requests\\"+sequence+".jpg";
				OutputStream out = null;
				OutputStream out2 = null;
	
				try {
				    out = new BufferedOutputStream(new FileOutputStream(savePath));
				    out2 = new BufferedOutputStream(new FileOutputStream(savePath2));
				    out.write(imageBytes);
				    out2.write(imageBytes);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				finally {
					try {
						if (out != null) out.close();
						if (out2 != null) out2.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}


			return new ResponseEntity<String>("Request successfully added", HttpStatus.OK);

		}
		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/getRequest",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<Request> getRequest(@Context HttpServletRequest request, @RequestBody String data) {
		
		if (data != null) {
			Long id = new Long(data);
			Request req = requestService.getRequestById(id);

			return new ResponseEntity<Request>(req, HttpStatus.OK);

		}
		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/update",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> update(@Context HttpServletRequest request, @RequestBody RequestUpdateDTO data) {

		if (data != null) {

			if(data.getGamename() == "" || data.getGamename()==null){
				return new ResponseEntity<String>("Please, fill in the requested game name field.", HttpStatus.OK);
			} else if ( data.getGamedesc()=="" || data.getGamedesc()==null){
				return new ResponseEntity<String>("Please, fill in the requested game description.", HttpStatus.OK);
			} else if (data.getGameDevelopers().isEmpty()){
				return new ResponseEntity<String>("Please, select at least one developer.", HttpStatus.OK);
			} else if (data.getGamery()=="" || data.getGamery()== null){
				return new ResponseEntity<String>("Please, properly select the release year.", HttpStatus.OK);
			} else if (data.getGamedr() == "" || data.getGamedr() == null){
				return new ResponseEntity<String>("Please, fill in the requested game detection rule.", HttpStatus.OK);
			} else if( data.getGimg() == "" || data.getGimg() == null){
				return new ResponseEntity<String>("Please, select the requested game image.", HttpStatus.OK);
			}
			
			
			if(!data.getGamedr().contains("<game>")){
				return new ResponseEntity<String>("Detection rule not formatted correctly. Missing <game> opening tag.", HttpStatus.OK);
			} else if(!data.getGamedr().contains("</game>")){
				return new ResponseEntity<String>("Detection rule not formatted correctly. Missing </game> closing tag.", HttpStatus.OK);
			} else if(!data.getGamedr().contains("<rule>")){
				return new ResponseEntity<String>("Detection rule not formatted correctly. Missing <rule> opening tag.", HttpStatus.OK);
			} else if(!data.getGamedr().contains("</rule>")){
				return new ResponseEntity<String>("Detection rule not formatted correctly. Missing </rule> closing tag.", HttpStatus.OK);
			} else if(!data.getGamedr().contains("<proc>")){
				return new ResponseEntity<String>("Detection rule not formatted correctly. Missing <proc> opening tag.", HttpStatus.OK);
			} else if(!data.getGamedr().contains("</proc>")){
				return new ResponseEntity<String>("Detection rule not formatted correctly. Missing </proc> closing tag.", HttpStatus.OK);
			} else if(!data.getGamedr().contains("<file>")){
				return new ResponseEntity<String>("Detection rule not formatted correctly. Missing <file> opening tag.", HttpStatus.OK);
			} else if(!data.getGamedr().contains("</file>")){
				return new ResponseEntity<String>("Detection rule not formatted correctly. Missing </file> closing tag.", HttpStatus.OK);
			}
			
			try {
			    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			    DocumentBuilder builder = factory.newDocumentBuilder();
			    InputSource is = new InputSource(new StringReader(data.getGamedr()));
			    Document doc = builder.parse(is);
				} catch (Exception e) {
					return new ResponseEntity<String>("Detection rule formated incorrectly. Make sure all opening tags have matching closing ones.", HttpStatus.OK);
				}
			
			
			
			String sub = data.getGamedr().substring(data.getGamedr().indexOf("</game>")+7).trim();
			if(!sub.equals("")){
				return new ResponseEntity<String>("There shouldn't be anything after the </game> closing tag.", HttpStatus.OK);
			}

			
			
			if(data.getReqstatus()=="ACCEPTED" || data.getReqstatus()=="PENDING"){
				if(gamesService.getGameByNameAndYear(data.getGamename(), new BigDecimal(data.getGamery()))!=null){
					return new ResponseEntity<String>("Game with the desired name and release year already exists.", HttpStatus.OK);
				}
			}
			
			Request req = requestService.getRequestById(new Long(data.getReqid()));
			
			Active current = (Active) request.getSession().getAttribute("user");
			
			
			GtUser user = gtUserService.getGtUserByName(current.getActuser().getUname());
		
			if((requestService.getAlreadySubmitted(data.getGamename(), new BigDecimal(data.getGamery()), req.getGameUser().getUname())!=null) && ( !data.getGamename().equals(req.getReqgname()) || !req.getReqgrd().equals(new BigDecimal(data.getGamery())))){
				if(user.getUserut().equals(new BigDecimal(1))){
					return new ResponseEntity<String>("You've already submitted request for this game. Consider updating your previous one.", HttpStatus.OK);
				} else {
					return new ResponseEntity<String>("User already has one request for this game. Consider updating that one instead.", HttpStatus.OK);
				}
			}
			
			req.setReqdesc(data.getGamedesc());
			req.getGameDevelopers().clear();
			ArrayList<String> devs = data.getGameDevelopers();
			for (String temp : devs) {
				GameDeveloper developer = gamedevService.getGameDeveloperByName(temp);
				req.getGameDevelopers().add(developer);
			}
			
			req.setReqdr(data.getGamedr());
			req.setReqgname(data.getGamename());
			final String dir = System.getProperty("user.dir");
			if(req.getReqimg()!=null){
				if(!data.getGimg().toString().equals(req.getReqimg())){
					String base64Image = data.getGimg().split(",")[1];
					byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
					Long sequence = req.getReqid();
					String imagePath = "../../data/requests/" + sequence + ".jpg";
					req.setReqimg(imagePath);
					String savePath = dir + "\\src\\main\\resources\\static\\data\\requests\\"+sequence+".jpg";
					String savePath2 = dir + "\\target\\classes\\static\\data\\requests\\"+sequence+".jpg";
					OutputStream out = null;
					OutputStream out2 = null;
		
					try {
					    out = new BufferedOutputStream(new FileOutputStream(savePath));
					    out2 = new BufferedOutputStream(new FileOutputStream(savePath2));
					    out.write(imageBytes);
					    out2.write(imageBytes);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					finally {
						try {
							if (out != null) out.close();
							if (out2 != null) out2.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				
				if(data.getGimg()!=null && data.getGimg()!=""){
					String base64Image = data.getGimg().split(",")[1];
					byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
		
					Long sequence = req.getReqid();
					String imagePath = "../../data/requests/" + sequence + ".jpg";
					req.setReqimg(imagePath);
					String savePath = dir + "\\src\\main\\resources\\static\\data\\requests\\"+sequence+".jpg";
					String savePath2 = dir + "\\target\\classes\\static\\data\\requests\\"+sequence+".jpg";
					OutputStream out = null;
					OutputStream out2 = null;
		
					try {
					    out = new BufferedOutputStream(new FileOutputStream(savePath));
					    out2 = new BufferedOutputStream(new FileOutputStream(savePath2));
					    out.write(imageBytes);
					    out2.write(imageBytes);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					finally {
						try {
							if (out != null) out.close();
							if (out2 != null) out2.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			req.setReqgrd(new BigDecimal(data.getGamery()));
			List<GameDeveloper> allDevelopers = gamedevService.findAll();
			for (GameDeveloper developer : allDevelopers) {
				if(developer.getRequests().contains(req)){
					developer.getRequests().remove(req);
				}
			}
			for (GameDeveloper developer : req.getGameDevelopers()) {
				if(!developer.getRequests().contains(req)){
					developer.getRequests().add(req);
				}
			}
			
			if(req.getReqstatus().equals("DECLINED")){
				req.setReqstatus("PENDING");
			} else {
				req.setReqstatus(data.getReqstatus());
			}
			
			
			requestService.updateRequest(req.getReqid(),req.getReqgname(), req.getReqdesc(), req.getReqimg(), req.getReqgrd(), req.getReqdr(), req.getReqstatus());
			if(data.getReqstatus().equals("ACCEPTED")){
				Game game = gamesService.getGameByNameAndYear(req.getReqgname(), req.getReqgrd());
				
				Long sequence = req.getReqid();
				String savePath = dir + "\\src\\main\\resources\\static\\data\\requests\\"+sequence+".jpg";
				String savePath2 = dir + "\\target\\classes\\static\\data\\requests\\"+sequence+".jpg";
				OutputStream out = null;
				OutputStream out2 = null;
				try {
				File imgPath = new File(savePath);
				
				 byte[] bajtovi = Files.readAllBytes(imgPath.toPath());
				 sequence = game.getGameid();
				 savePath = dir + "\\src\\main\\resources\\static\\data\\games\\"+sequence+".jpg";
				 savePath2 = dir + "\\target\\classes\\static\\data\\games\\"+sequence+".jpg";
				 
				 
				 out = new BufferedOutputStream(new FileOutputStream(savePath));
				 out2 = new BufferedOutputStream(new FileOutputStream(savePath2));
				 out.write(bajtovi);
				 out2.write(bajtovi);
					
					
					
				 
				} catch (IOException e){
					e.printStackTrace();
				} finally {
					try {
						if (out != null) out.close();
						if (out2 != null) out2.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				for(GameDeveloper dev : req.getGameDevelopers()){
					game.getGameDevelopers().add(dev);
					if(!dev.getGames().contains(game)){
						dev.getGames().add(game);
					}
				}
				req.setGame(game);
				game.getRequests().add(req);
				gamesService.save(game);
				
				
				
				
				if(user.getUserut()==new BigDecimal(0)){
					Administrator admin = adminService.getAdministratorByName(user.getUname());
					req.setAdministrator(admin);
					admin.addRequest(req);
					adminService.save(admin);
				}
				requestService.save(req);
				
				return new ResponseEntity<String>("Request successfully accepted", HttpStatus.OK);
			} else if(data.getReqstatus().equals("DECLINED")){
				return new ResponseEntity<String>("Request successfully declined", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Request successfully updated", HttpStatus.OK);
			}

		}
		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/removeRequest",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<Request> removeRequest(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			Long id = new Long(data);
			Request req = requestService.getRequestById(id);
			
			
			
			requestService.removeRequestById(id);
			
			final String dir = System.getProperty("user.dir");
			Long sequence = req.getReqid();
			String savePath = dir + "\\src\\main\\resources\\static\\data\\requests\\"+sequence+".jpg";
			String savePath2 = dir + "\\target\\classes\\static\\data\\requests\\"+sequence+".jpg";
			File file = new File(savePath);
			File file2 = new File(savePath2);
			file.delete();
			file2.delete();
		
				return new ResponseEntity<Request>(req, HttpStatus.OK);

		}

		return null;
		
	}
	

}
