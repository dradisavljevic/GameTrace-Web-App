package project.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.hibernate.JDBCException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import project.domain.Game;
import project.domain.GameDeveloper;
import project.domain.Player;
import project.domain.Review;
import project.domain.dto.CompactGameDTO;
import project.domain.dto.GameDTO;
import project.domain.dto.GameUpdateDTO;
import project.service.GameDeveloperService;
import project.service.GameService;
import project.service.PlayService;
import project.service.ReviewService;

@RequestMapping("/games")
@Controller
public class GamesController {
	
	@Autowired
	private GameService gamesService;
	
	@Autowired
	private GameDeveloperService gamedevService;
	
	@Autowired
	private PlayService playService;
	
	@Autowired
	private ReviewService reviewService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getGames",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Game>> getAllGames(@Context HttpServletRequest request) {
		
			List<Game> games = gamesService.getAll();
			
			Collections.sort(games, new Comparator() {
	            @Override
	            public int compare(Object gameOne, Object gameTwo) {
	                //use instanceof to verify the references are indeed of the type in question
	                return ((Game)gameOne).getGamename().toUpperCase()
	                        .compareTo(((Game)gameTwo).getGamename().toUpperCase());
	            }
	        }); 
			
			
			
			return new ResponseEntity<List<Game>>(games, HttpStatus.OK);
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getGamesCompact",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<CompactGameDTO>> getGamesCompact(@Context HttpServletRequest request) {
		
			List<CompactGameDTO> games = gamesService.fillGameList();
			
			Collections.sort(games, new Comparator() {
	            @Override
	            public int compare(Object gameOne, Object gameTwo) {
	                //use instanceof to verify the references are indeed of the type in question
	                return ((CompactGameDTO)gameOne).getGamename().toUpperCase()
	                        .compareTo(((CompactGameDTO)gameTwo).getGamename().toUpperCase());
	            }
	        }); 

			
			return new ResponseEntity<List<CompactGameDTO>>(games, HttpStatus.OK);
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@RequestMapping(value = "/filterName",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Game>> filterName(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";
			List<Game> games = gamesService.getGameByName(newData);
			
			Collections.sort(games, new Comparator() {
	            @Override
	            public int compare(Object gameOne, Object gameTwo) {
	                //use instanceof to verify the references are indeed of the type in question
	                return ((Game)gameOne).getGamename().toUpperCase()
	                        .compareTo(((Game)gameTwo).getGamename().toUpperCase());
	            }
	        }); 
			


				return new ResponseEntity<List<Game>>(games, HttpStatus.OK);

		}
		List<Game> games = gamesService.getAll();
		Collections.sort(games, new Comparator() {
            @Override
            public int compare(Object gameOne, Object gameTwo) {
                //use instanceof to verify the references are indeed of the type in question
                return ((Game)gameOne).getGamename().toUpperCase()
                        .compareTo(((Game)gameTwo).getGamename().toUpperCase());
            }
        }); 
		return new ResponseEntity<List<Game>>(games, HttpStatus.OK);
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	@RequestMapping(value = "/filterYear",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Game>> filterYear(@Context HttpServletRequest request, @RequestBody String data) {
		if (data != null) {
			BigDecimal godina = new BigDecimal(data);
			List<Game> games = gamesService.getGameByYear(godina);
			
			Collections.sort(games, new Comparator() {
	            @Override
	            public int compare(Object gameOne, Object gameTwo) {
	                //use instanceof to verify the references are indeed of the type in question
	                return ((Game)gameOne).getGamename().toUpperCase()
	                        .compareTo(((Game)gameTwo).getGamename().toUpperCase());
	            }
	        }); 


				return new ResponseEntity<List<Game>>(games, HttpStatus.OK);

		}
		List<Game> games = gamesService.getAll();
		
		return new ResponseEntity<List<Game>>(games, HttpStatus.OK);
		
	}
	
	@Transactional
	@RequestMapping(value = "/getGame",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<Game> getGame(@Context HttpServletRequest request, @RequestBody String data) {
		
		if (data != null) {
			Long id = new Long(data);
			Game game = gamesService.getGameById(id);

			return new ResponseEntity<Game>(game, HttpStatus.OK);

		}
		return null;
		
	}
	

	
	
	@Transactional
	@RequestMapping(value = "/getPlayers",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Player>> getPlayers(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			Long id = new Long(data);
			List<Player> players = playService.getTopPlayers(id);


				return new ResponseEntity<List<Player>>(players, HttpStatus.OK);

		}
		return null;
		
	}
	
	@Transactional
	@RequestMapping(value = "/getReviews",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Review>> getReviews(@Context HttpServletRequest request, @RequestBody String data) {
		
		if (data != null) {
			Long id = new Long(data);
			List<Review> reviews = reviewService.getAllByGameId(id);
			
			

			return new ResponseEntity<List<Review>>(reviews, HttpStatus.OK);

		}
		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/save",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> save(@Context HttpServletRequest request, @RequestBody GameDTO data) {

		if (data != null) {
			final String dir = System.getProperty("user.dir");
			
			
			if(data.getGamename() == "" || data.getGamename()==null){
				return new ResponseEntity<String>("Please, fill in the name field.", HttpStatus.OK);
			} else if ( data.getGamedesc()=="" || data.getGamedesc()==null){
				return new ResponseEntity<String>("Please, fill in the description field.", HttpStatus.OK);
			} else if (data.getGameDevelopers().isEmpty()){
				return new ResponseEntity<String>("Please, select atleast one developer.", HttpStatus.OK);
			} else if (data.getGamery()=="" || data.getGamery()== null){
				return new ResponseEntity<String>("Please, properly select the release year.", HttpStatus.OK);
			} else if (data.getGamedr() == "" || data.getGamedr() == null){
				return new ResponseEntity<String>("Please, fill in the game detection rule field.", HttpStatus.OK);
			} else if( data.getGimg() == "" || data.getGimg() == null){
				return new ResponseEntity<String>("Please, select the game image.", HttpStatus.OK);
			}
			if(gamesService.getGameByNameAndYear(data.getGamename(), new BigDecimal(data.getGamery()))!=null){
				return new ResponseEntity<String>("Game with the desired name and release year already exists.", HttpStatus.OK);
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

			
			Game game = new Game();
			game.setGamedesc(data.getGamedesc());
			ArrayList<GameDeveloper> developers = new ArrayList<GameDeveloper>();
			ArrayList<String> devs = data.getGameDevelopers();
			for (String temp : devs) {
				GameDeveloper developer = gamedevService.getGameDeveloperByName(temp);
				developers.add(developer);
			}
			
			game.setGameDevelopers(developers);
			game.setGamedr(data.getGamedr());
			game.setGamename(data.getGamename());
			game.setGamepc(new BigDecimal(data.getGamepc()));
			game.setGamepday(new BigDecimal(data.getGamepday()));
			game.setGamephour(new BigDecimal(data.getGamephour()));
			game.setGamepmin(new BigDecimal(data.getGamepmin()));
			game.setGamepn(new BigDecimal(data.getGamepn()));
			game.setGamepsec(new BigDecimal(data.getGamepsec()));
			
			
			
			
			game.setGamery(new BigDecimal(data.getGamery()));
			for (GameDeveloper developer : game.getGameDevelopers()) {
				if(!developer.getGames().contains(game)){
					developer.getGames().add(game);
				}
			}
			
			gamesService.save(game);
		
		
			
			
			if(data.getGimg()!=null && data.getGimg()!=""){
				String base64Image = data.getGimg().split(",")[1];
				byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
				Long sequence = game.getGameid();
				String imagePath = "../../data/games/" + sequence + ".jpg";
				game.setGimg(imagePath);
				String savePath = dir + "\\src\\main\\resources\\static\\data\\games\\"+sequence+".jpg";
				String savePath2 = dir + "\\target\\classes\\static\\data\\games\\"+sequence+".jpg";
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

			return new ResponseEntity<String>("Game successfully added", HttpStatus.OK);

		}
		return null;
		
	}
	
	
	
	
	
	@SuppressWarnings("unchecked")
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
	
	
	@Transactional
	@RequestMapping(value = "/update",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> update(@Context HttpServletRequest request, @RequestBody GameUpdateDTO data) {

		if (data != null) {
			
			if(data.getGamename() == "" || data.getGamename()==null){
				return new ResponseEntity<String>("Please, fill in the name field.", HttpStatus.OK);
			} else if ( data.getGamedesc()=="" || data.getGamedesc()==null){
				return new ResponseEntity<String>("Please, fill in the description field.", HttpStatus.OK);
			} else if (data.getGameDevelopers().isEmpty()){
				return new ResponseEntity<String>("Please, select at least one developer.", HttpStatus.OK);
			} else if (data.getGamery()=="" || data.getGamery()== null){
				return new ResponseEntity<String>("Please, properly select the release year.", HttpStatus.OK);
			} else if (data.getGamedr() == "" || data.getGamedr() == null){
				return new ResponseEntity<String>("Please, fill in the game detection rule field.", HttpStatus.OK);
			} else if( data.getGimg() == "" || data.getGimg() == null){
				return new ResponseEntity<String>("Please, select the game image.", HttpStatus.OK);
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

			
			
			final String dir = System.getProperty("user.dir");
			Game game = gamesService.getGameById(new Long(data.getGameid()));
			
			if(gamesService.getGameByNameAndYear(data.getGamename(), new BigDecimal(data.getGamery()))!=null && (!data.getGamename().equals(game.getGamename()) || (!game.getGamery().equals(new BigDecimal(data.getGamery()))))){
				return new ResponseEntity<String>("Game with the desired name and release year already exists.", HttpStatus.OK);
			}
			
			game.setGamedesc(data.getGamedesc());
			game.getGameDevelopers().clear();
			ArrayList<String> devs = data.getGameDevelopers();
			for (String temp : devs) {
				GameDeveloper developer = gamedevService.getGameDeveloperByName(temp);
				game.getGameDevelopers().add(developer);
			}
			
			game.setGamedr(data.getGamedr());
			game.setGamename(data.getGamename());

			if(game.getGimg()!=null){
				if(!data.getGimg().toString().equals(game.getGimg())){
					String base64Image = data.getGimg().split(",")[1];
					byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
					Long sequence = game.getGameid();
					String imagePath = "../../data/games/" + sequence + ".jpg";
					game.setGimg(imagePath);
					String savePath = dir + "\\src\\main\\resources\\static\\data\\games\\"+sequence+".jpg";
					String savePath2 = dir + "\\target\\classes\\static\\data\\games\\"+sequence+".jpg";
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
					Long sequence = game.getGameid();
					String imagePath = "../../data/games/" + sequence + ".jpg";
					game.setGimg(imagePath);
					String savePath = dir + "\\src\\main\\resources\\static\\data\\games\\"+sequence+".jpg";
					String savePath2 = dir + "\\target\\classes\\static\\data\\games\\"+sequence+".jpg";
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
			game.setGamery(new BigDecimal(data.getGamery()));
			List<GameDeveloper> allDevelopers = gamedevService.findAll();
			for (GameDeveloper developer : allDevelopers) {
				if(developer.getGames().contains(game)){
					developer.getGames().remove(game);
				}
			}
			for (GameDeveloper developer : game.getGameDevelopers()) {
				if(!developer.getGames().contains(game)){
					developer.getGames().add(game);
				}
			}
			gamesService.updateGame(game.getGameid(),game.getGamename(),game.getGamedesc(), game.getGamedr(),game.getGimg(), game.getGamery());

			return new ResponseEntity<String>("Game information successfully updated" , HttpStatus.OK);

		}
		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/getGamesByStreamer",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Game>> getGamesByStreamer(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			Long id = new Long(data);
			List<Game> games = gamesService.getAllByStreamer(id);


				return new ResponseEntity<List<Game>>(games, HttpStatus.OK);

		}

		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/removeGame",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<Game> removeGame(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			Long id = new Long(data);
			Game game = gamesService.getGameById(id);
			
			
			
			for (GameDeveloper dev : game.getGameDevelopers()){
				dev.getGames().remove(game);
			}
			gamesService.removeGameById(game.getGameid());
			
			final String dir = System.getProperty("user.dir");
			Long sequence = game.getGameid();
			String savePath = dir + "\\src\\main\\resources\\static\\data\\games\\"+sequence+".jpg";
			String savePath2 = dir + "\\target\\classes\\static\\data\\games\\"+sequence+".jpg";
			File file = new File(savePath);
			File file2 = new File(savePath2);
			file.delete();
			file2.delete();


				return new ResponseEntity<Game>(game, HttpStatus.OK);

		}

		return null;
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	@RequestMapping(value = "/filterStarting",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Game>> filterStarting(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {

			if(data=="#"){

				List<Game> games = gamesService.getAllByHash();
				Collections.sort(games, new Comparator() {
		            @Override
		            public int compare(Object gameOne, Object gameTwo) {
		                //use instanceof to verify the references are indeed of the type in question
		                return ((Game)gameOne).getGamename().toUpperCase()
		                        .compareTo(((Game)gameTwo).getGamename().toUpperCase());
		            }
		        }); 
				return new ResponseEntity<List<Game>>(games, HttpStatus.OK);
			} else {


				List<Game> games = gamesService.getAllByNameStarting(data);
				Collections.sort(games, new Comparator() {
		            @Override
		            public int compare(Object gameOne, Object gameTwo) {
		                //use instanceof to verify the references are indeed of the type in question
		                return ((Game)gameOne).getGamename().toUpperCase()
		                        .compareTo(((Game)gameTwo).getGamename().toUpperCase());
		            }
		        }); 
				return new ResponseEntity<List<Game>>(games, HttpStatus.OK);
			}
			
		
		}
		List<Game> games = gamesService.getAll();
		Collections.sort(games, new Comparator() {
            @Override
            public int compare(Object gameOne, Object gameTwo) {
                //use instanceof to verify the references are indeed of the type in question
                return ((Game)gameOne).getGamename().toUpperCase()
                        .compareTo(((Game)gameTwo).getGamename().toUpperCase());
            }
        }); 
		return new ResponseEntity<List<Game>>(games, HttpStatus.OK);
		
	}
	
	

}
