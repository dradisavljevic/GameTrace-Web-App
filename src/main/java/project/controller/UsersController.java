package project.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.Timestamp;
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

import com.google.common.hash.Hashing;

import project.domain.Achievement;
import project.domain.Active;
import project.domain.Administrator;
import project.domain.FriendsInGame;
import project.domain.Game;
import project.domain.GameUser;
import project.domain.GtUser;
import project.domain.Message;
import project.domain.MessagePK;
import project.domain.PlayedGame;
import project.domain.TopGame;
import project.domain.dto.GTUserDTO;
import project.domain.dto.GameUserDTO;
import project.domain.dto.MessageDTO;
import project.domain.dto.SendMessageDTO;
import project.messaging.ChatMessenger;
import project.service.AchievementService;
import project.service.AdministratorService;
import project.service.GameService;
import project.service.GameUserService;
import project.service.GtUserService;
import project.service.MessageService;
import project.service.PlayService;

@RequestMapping("/users")
@Controller
public class UsersController {
	
	@Autowired
	private GtUserService gtUserService;
	
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private GameUserService gameUserService;
	
	@Autowired
	private PlayService playService;
	
	@Autowired
	private AchievementService achievementService;
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private MessageService msgService;
	
	@Autowired
	private ChatMessenger chatMessenger;

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getUsers",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<GameUser>> getUsers(@Context HttpServletRequest request) {
		
			List<GameUser> users = gameUserService.findAll();
			
			Collections.sort(users, new Comparator() {
	            @Override
	            public int compare(Object userOne, Object userTwo) {
	                //use instanceof to verify the references are indeed of the type in question
	                return ((GameUser)userOne).getUname().toUpperCase()
	                        .compareTo(((GameUser)userTwo).getUname().toUpperCase());
	            }
	        }); 

			
			return new ResponseEntity<List<GameUser>>(users, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/getAllUsers",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<GtUser>> getAllUsers(@Context HttpServletRequest request) {
		
			List<GtUser> users = gtUserService.findAll();
			
			

			
			return new ResponseEntity<List<GtUser>>(users, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/getActive",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<GameUser> getActive(@Context HttpServletRequest request) {
		
			Active current = (Active) request.getSession().getAttribute("user");
			
			GtUser active = current.getActuser();
			
			if(active.getUserut()==new BigDecimal(0))
			{
				return null;
			} 
			GameUser user = gameUserService.getGameUserByName(active.getUname());
		

			

			
			return new ResponseEntity<GameUser>(user, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/getActiveGT",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<GtUser> getActiveGT(@Context HttpServletRequest request) {
		
			Active current = (Active) request.getSession().getAttribute("user");
			
			GtUser active = current.getActuser();
			

			

			
			return new ResponseEntity<GtUser>(active, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/getActiveType",
			method = RequestMethod.GET,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> getActiveType(@Context HttpServletRequest request) {
		
			Active current = (Active) request.getSession().getAttribute("user");
			
			GtUser active = current.getActuser();
			
			String tip = active.getUserut().toString();
			

			

			
			return new ResponseEntity<String>(tip, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/getMyTopGames",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<TopGame>> getMyTopGames(@Context HttpServletRequest request) {
		
			Active current = (Active) request.getSession().getAttribute("user");
			
			GtUser active = current.getActuser();
			

			
			List<TopGame> games = playService.getTopGames(active.getUname());


			return new ResponseEntity<List<TopGame>>(games, HttpStatus.OK);
		
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getMyLibrary",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<PlayedGame>> getMyLibrary(@Context HttpServletRequest request) {
		
			Active current = (Active) request.getSession().getAttribute("user");
			
			GtUser active = current.getActuser();
			

			
			List<PlayedGame> games = playService.getPlayedGames(active.getUname());
			
			Collections.sort(games, new Comparator() {
	            @Override
	            public int compare(Object gameOne, Object gameTwo) {
	               
	                return ((PlayedGame)gameOne).getGamename().toUpperCase()
	                        .compareTo(((PlayedGame)gameTwo).getGamename().toUpperCase());
	            }
	        }); 


			return new ResponseEntity<List<PlayedGame>>(games, HttpStatus.OK);
		
	}
	
	
	
	@RequestMapping(value = "/getMyFriends",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<GameUser>> getMyFriends(@Context HttpServletRequest request) {
		
			Active current = (Active) request.getSession().getAttribute("user");
			
			GtUser active = current.getActuser();
			
			GameUser user = gameUserService.getGameUserByName(active.getUname());
			List<GameUser> users = user.getGameUsers1();
	


				return new ResponseEntity<List<GameUser>>(users, HttpStatus.OK);
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	@RequestMapping(value = "/filterUsername",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<GameUser>> filterUsername(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";
			List<GameUser> users = gameUserService.getAllByName(newData);
			
			Collections.sort(users, new Comparator() {
	            @Override
	            public int compare(Object userOne, Object userTwo) {
	                //use instanceof to verify the references are indeed of the type in question
	                return ((GameUser)userOne).getUname().toUpperCase()
	                        .compareTo(((GameUser)userTwo).getUname().toUpperCase());
	            }
	        }); 


				return new ResponseEntity<List<GameUser>>(users, HttpStatus.OK);

		}
		List<GameUser> users = gameUserService.findAll();
		return new ResponseEntity<List<GameUser>>(users, HttpStatus.OK);
		
	}
	
	@Transactional
	@RequestMapping(value = "/getUser",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<GameUser> getUser(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			GameUser user = gameUserService.getGameUserByName(data);


				return new ResponseEntity<GameUser>(user, HttpStatus.OK);

		}
		return null;
		
	}
	
	@Transactional
	@RequestMapping(value = "/getTopGames",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<TopGame>> getPlayers(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {

			List<TopGame> games = playService.getTopGames(data);


				return new ResponseEntity<List<TopGame>>(games, HttpStatus.OK);

		}
		return null;
		
	}
	
	@Transactional
	@RequestMapping(value = "/getFriends",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<GameUser>> getFriends(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			GameUser user = gameUserService.getGameUserByName(data);
			if(user!=null){
			List<GameUser> users = user.getGameUsers1();
			
			
			
			

				return new ResponseEntity<List<GameUser>>(users, HttpStatus.OK);
			}

		}
		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/addmin",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> addmin(@Context HttpServletRequest request, @RequestBody GTUserDTO data) {
		if (data != null) {
			if(data.getUname() == "" || data.getUname()==null){
				return new ResponseEntity<String>("Please, fill in the username field.", HttpStatus.OK);
			} else if(data.getPword() == "" || data.getPword()==null){
				return new ResponseEntity<String>("Please, fill in the password field.", HttpStatus.OK);
			} else if(data.getUavat() == "" || data.getUavat()==null){
				data.setUavat("data:image/jpeg;base64,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");
			} else if(data.getUemail() == "" || data.getUemail()==null){
				return new ResponseEntity<String>("Please, insert a valid email address.", HttpStatus.OK);
			}
			
			if(gtUserService.getGtUserByName(data.getUname())!=null){
				return new ResponseEntity<String>("User with the desired username already exists.", HttpStatus.OK);
			}
			
			if(gtUserService.getGtUserByEmail(data.getUemail())!=null){
				return new ResponseEntity<String>("User with the desired email address already exists.", HttpStatus.OK);
			}
			
			String sha256hex = Hashing.sha256()
					  .hashString(data.getPword(), StandardCharsets.UTF_8)
					  .toString();
			
			final String dir = System.getProperty("user.dir");
			GtUser user = new GtUser();
			user.setUname(data.getUname());
			user.setPword(sha256hex);
			user.setUserut(new BigDecimal(0));
			user.setUemail(data.getUemail());
			

			gtUserService.save(user);
			
			if(data.getUavat()!=null && data.getUavat()!=""){
				String base64Image = data.getUavat().split(",")[1];
				byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
				String sequence = user.getUname();
				String imagePath = "../../data/users/" + sequence + ".jpg";
				user.setUavat(imagePath);
				String savePath = dir + "\\src\\main\\resources\\static\\data\\users\\"+sequence+".jpg";
				String savePath2 = dir + "\\target\\classes\\static\\data\\users\\"+sequence+".jpg";
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
			
			Administrator admin = new Administrator();
			admin.setUname(user.getUname());
			administratorService.save(admin);


			return new ResponseEntity<String>("Administrator successfully added", HttpStatus.OK);

		}
		return null;
		
	}
	
	@Transactional
	@RequestMapping(value = "/removeUser",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<GtUser> removeUser(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			GtUser user = gtUserService.getGtUserByName(data);
			
			
			
			
			
			gtUserService.removeGtUserByName(data);
			
			final String dir = System.getProperty("user.dir");
			String sequence = user.getUname();
			String savePath = dir + "\\src\\main\\resources\\static\\data\\users\\"+sequence+".jpg";
			String savePath2 = dir + "\\target\\classes\\static\\data\\users\\"+sequence+".jpg";
			File file = new File(savePath);
			File file2 = new File(savePath2);
			file.delete();
			file2.delete();


				return new ResponseEntity<GtUser>(user, HttpStatus.OK);

		}

		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/updateProfile",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> updateProfile(@Context HttpServletRequest request, @RequestBody GameUserDTO data) {

		if (data != null) {
			
			if(data.getUavat() == "" || data.getUavat()==null){
				return new ResponseEntity<String>("Please, upload a valid profile image.", HttpStatus.OK);
			}
			
			final String dir = System.getProperty("user.dir");
			GameUser gameUser = gameUserService.getGameUserByName(data.getUname());
			gameUser.setRname(data.getRname());
			gameUser.setUbio(data.getUbio());
			gameUser.setUcountry(data.getUcountry());
			gameUserService.updateGameUserRealNameAndCountryAndBiography(data.getUname(), data.getRname(), data.getUcountry(), data.getUbio());
			
			GtUser gtUser = gtUserService.getGtUserByName(data.getUname());
			if(gtUser.getUavat()!=null){
				if(!data.getUavat().toString().equals(gtUser.getUavat())){
					String base64Image = data.getUavat().split(",")[1];
					byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
					String sequence = gtUser.getUname();
					String imagePath = "../../data/users/" + sequence + ".jpg";
					gtUser.setUavat(imagePath);
					String savePath = dir + "\\src\\main\\resources\\static\\data\\users\\"+sequence+".jpg";
					String savePath2 = dir + "\\target\\classes\\static\\data\\users\\"+sequence+".jpg";
					OutputStream out = null;
					OutputStream out2 = null;
					gtUserService.updateGtUserAvatar(data.getUname(), imagePath);
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
				
				if(data.getUavat()!=null && data.getUavat()!=""){
					String base64Image = data.getUavat().split(",")[1];
					byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
					String sequence = gtUser.getUname();
					String imagePath = "../../data/users/" + sequence + ".jpg";
					gtUser.setUavat(imagePath);
					String savePath = dir + "\\src\\main\\resources\\static\\data\\users\\"+sequence+".jpg";
					String savePath2 = dir + "\\target\\classes\\static\\data\\users\\"+sequence+".jpg";
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
			
			
			


				return new ResponseEntity<String>("Profile successfully updated", HttpStatus.OK);

		}

		return null;
		
	}
	
	
	
	@RequestMapping(value = "/getMyLatestAchievements",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Achievement>> getMyLatestAchievements(@Context HttpServletRequest request) {
		
			Active current = (Active) request.getSession().getAttribute("user");
			
			GtUser active = current.getActuser();
			

			
			List<Achievement> achievements = achievementService.getLatestAchievements(active.getUname());


			return new ResponseEntity<List<Achievement>>(achievements, HttpStatus.OK);
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/getUserLatestAchievements",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Achievement>> getUserLatestAchievements(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			
			List<Achievement> achievements = achievementService.getLatestAchievements(data);

			return new ResponseEntity<List<Achievement>>(achievements, HttpStatus.OK);

		}
		return null;
		
	}
	
	@RequestMapping(value = "/getRecommendation",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Game>> getRecommendation(@Context HttpServletRequest request) {
		
			Active current = (Active) request.getSession().getAttribute("user");
			
			GtUser active = current.getActuser();
			

			
			List<Game> recommendation = gameService.getRecommendation(active.getUname());


			return new ResponseEntity<List<Game>>(recommendation, HttpStatus.OK);
		
	}
	
	@Transactional
	@RequestMapping(value = "/getMessages",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<MessageDTO>> getMessages(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			
			Active current = (Active) request.getSession().getAttribute("user");
			
			GtUser active = current.getActuser();
			
			List<MessageDTO> messages = msgService.getMessages(data, active.getUname());


				return new ResponseEntity<List<MessageDTO>>(messages, HttpStatus.OK);

		}
		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/sendMessage",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<Message> sendMessage(@Context HttpServletRequest request, @RequestBody SendMessageDTO data) {

		if (data != null) {
			
			Active current = (Active) request.getSession().getAttribute("user");
			
			GtUser active = current.getActuser();
			
			Message message = new Message();
			MessagePK pk = new MessagePK();
			pk.setMsgsent(active.getUname());
			java.util.Date utilDate = new java.util.Date();
			Date sqlDate = new Date(utilDate.getTime());
			Timestamp sqlTime = new Timestamp(utilDate.getTime());
			pk.setMsgdate(sqlDate);
			pk.setMsgtime(sqlTime);
			message.setMsgcont(data.getContent());
			message.setId(pk);
			
			
			GtUser gtu = gtUserService.getGtUserByName(data.getReceiver());
			GtUser gtu2 = gtUserService.getGtUserByName(active.getUname());
			message.setGtUser(gtu2);
			List<GtUser> lista = new ArrayList<GtUser>();
			lista.add(gtu);
			message.setGtUsers(lista);
			msgService.save(message);
			gtu2.addMessages1(message);
			gtu.getMessages2().add(message);
			
			gtUserService.save(gtu);
			gtUserService.save(gtu2);
			
			SendMessageDTO notify = new SendMessageDTO();
			if(active.getUname().compareTo(data.getReceiver())==1){
				notify.setReceiver(active.getUname() + "+" + data.getReceiver());
			} else {
				notify.setReceiver(data.getReceiver() + "+" + active.getUname());
			}
			notify.setContent(data.getContent());
			chatMessenger.sendRequestTo(notify);
			chatMessenger.sendUpdateTo(notify);
			

				return new ResponseEntity<Message>(message, HttpStatus.OK);

		}
		return null;
		
	}
	
	
	@RequestMapping(value = "/getFriendsInGame",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<FriendsInGame>> getFriendsInGame(@Context HttpServletRequest request) {
		Active current = (Active) request.getSession().getAttribute("user");
		if(current!=null){
			
		
			GtUser active = current.getActuser();
			
			String username = active.getUname();
		
			List<FriendsInGame> friends = playService.getFriendsInGame(username);
			
			

			
			return new ResponseEntity<List<FriendsInGame>>(friends, HttpStatus.OK);
		}
		return null;
		
	}

}
