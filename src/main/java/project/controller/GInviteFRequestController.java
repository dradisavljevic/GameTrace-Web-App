package project.controller;

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
import project.domain.FriendRequest;
import project.domain.GameGroup;
import project.domain.GameUser;
import project.domain.GroupInvite;
import project.domain.GtUser;
import project.domain.dto.GroupDTO;
import project.service.FriendRequestService;
import project.service.GameGroupService;
import project.service.GameUserService;
import project.service.GroupInviteService;
import project.service.GtUserService;

@RequestMapping("/invreq")
@Controller
public class GInviteFRequestController {
	
	@Autowired
	private FriendRequestService friendRequestService;
	
	@Autowired
	private GroupInviteService groupInviteService;
	
	@Autowired
	private GtUserService gtUserService;
	
	@Autowired
	private GameUserService gameUserService;
	
	@Autowired
	private GameGroupService gameGroupService;
	
	
	@RequestMapping(value = "/getFriendRequests",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<FriendRequest>> getFriendRequests(@Context HttpServletRequest request) {
		
		Active current = (Active) request.getSession().getAttribute("user");
		
		GtUser active = current.getActuser();
		
		List<FriendRequest> req = friendRequestService.getAllByReceiver(active.getUname(), "PENDING");
			
			

			
		return new ResponseEntity<List<FriendRequest>>(req, HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value = "/getGroupInvites",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<GroupInvite>> getGroupInvites(@Context HttpServletRequest request) {
		
		Active current = (Active) request.getSession().getAttribute("user");
		
		GtUser active = current.getActuser();
		
		List<GroupInvite> inv = groupInviteService.getAllByReceiver(active.getUname(), "PENDING");
			
			

			
		return new ResponseEntity<List<GroupInvite>>(inv, HttpStatus.OK);
		
	}
	
	@Transactional
	@RequestMapping(value = "/alreadySent",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> alreadySent(@Context HttpServletRequest request, @RequestBody GroupDTO data) {
		if(data!=null){
			if (groupInviteService.getRequestBySenderReceiver(data.getUname(), new Long(data.getId()), data.getGrname(), "PENDING")!=null)
			{
				return new ResponseEntity<String>("true", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("false", HttpStatus.OK);
			}
			
			
		}
		return null;
	}
	
	
	
	
	@Transactional
	@RequestMapping(value = "/acceptFriendRequest",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> acceptFriendRequest(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			FriendRequest fr = friendRequestService.getRequestById(new Long(data));
			
			GameUser receiver = gameUserService.getGameUserByName(fr.getReceiver());
			
			GameUser sender = gameUserService.getGameUserByName(fr.getSender());
			
			receiver.getGameUsers1().add(sender);
			receiver.getGameUsers2().add(sender);

			
			sender.getGameUsers1().add(receiver);
			sender.getGameUsers2().add(receiver);
		
			
			gameUserService.save(sender);
			gameUserService.save(receiver);
			
			fr.setFrstat("ACCEPTED");
			friendRequestService.save(fr);


			return new ResponseEntity<String>(sender.getUname(), HttpStatus.OK);

		}
		return null;
		
	}
	
	@Transactional
	@RequestMapping(value = "/declineFriendRequest",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> declineFriendRequest(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			FriendRequest fr = friendRequestService.getRequestById(new Long(data));
			
			String sender = fr.getSender();
			
			fr.setFrstat("DECLINED");
			friendRequestService.save(fr);


			return new ResponseEntity<String>(sender, HttpStatus.OK);

		}
		return null;
		
	}
	
	@Transactional
	@RequestMapping(value = "/acceptGroupInvite",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> acceptGroupInvite(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			GroupInvite gi = groupInviteService.getInviteById(new Long(data));
			
			GameUser receiver = gameUserService.getGameUserByName(gi.getGirec());
			
			GameGroup group = gameGroupService.getGameGroupByKey(gi.getGroupId(), gi.getGroupMaster());
			
			receiver.getGameGroups().add(group);
	
			
			group.getGameUsers().add(receiver);

			
			
			
			gameGroupService.save(group);
			gameUserService.save(receiver);
			
			gi.setGistat("ACCEPTED");
			
			groupInviteService.save(gi);


			return new ResponseEntity<String>(group.getGrname(), HttpStatus.OK);

		}
		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/declineGroupInvite",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> declineGroupInvite(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			GroupInvite gi = groupInviteService.getInviteById(new Long(data));
			
			GameGroup group = gameGroupService.getGameGroupByKey(gi.getGroupId(), gi.getGroupMaster());
			
			gi.setGistat("DECLINED");
			
			groupInviteService.save(gi);


			return new ResponseEntity<String>(group.getGrname(), HttpStatus.OK);

		}
		return null;
		
	}
	
	@Transactional
	@RequestMapping(value = "/sendFRequest",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> sendFRequest(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			if(data == "" || data==null){
				return new ResponseEntity<String>("Error occured, please reload page and try again later.", HttpStatus.OK);
			}
			
			Active current = (Active) request.getSession().getAttribute("user");
			
			GtUser active = current.getActuser();
			
			GameUser sender = gameUserService.getGameUserByName(active.getUname());
			
			GameUser receiver = gameUserService.getGameUserByName(data);
			
			if(friendRequestService.getRequestBySenderReceiver(data, active.getUname(), "PENDING")!=null){
				return new ResponseEntity<String>("You've already sent friend request to this user.", HttpStatus.OK);	
			}
			
			FriendRequest fr = new FriendRequest();
			
			fr.setFrstat("PENDING");
			fr.setGameUser1(sender);
			fr.setSender(sender.getUname());
			fr.setGameUser2(receiver);
			fr.setReceiver(receiver.getUname());
			
			friendRequestService.save(fr);
			


			return new ResponseEntity<String>("Friend Request sent!", HttpStatus.OK);

		}
		return null;
		
	}
	
	@Transactional
	@RequestMapping(value = "/sendGInvite",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> sendGInvite(@Context HttpServletRequest request, @RequestBody GroupDTO data) {

		if (data != null) {
			if(data.getGrname() == "" || data==null || data.getUname() == ""){
				return new ResponseEntity<String>("Error occured, please reload page and try again later.", HttpStatus.OK);
			}
			
			System.out.println(data.getUname());
			System.out.println(data.getId());
			System.out.println(data.getGrname());
			if(groupInviteService.getRequestBySenderReceiver(data.getUname(), new Long(data.getId()), data.getGrname(), "PENDING")!=null){
				return new ResponseEntity<String>("You've already sent group invite to this user.", HttpStatus.OK);	
			}
			
			if(groupInviteService.getRequestBySenderReceiver(data.getUname(), new Long(data.getId()), data.getGrname(), "ACCEPTED")!=null){
				return new ResponseEntity<String>("User is already a member of this group.", HttpStatus.OK);	
			}
			
			Active current = (Active) request.getSession().getAttribute("user");
			
			GtUser active = current.getActuser();
			
			GameUser sender = gameUserService.getGameUserByName(active.getUname());
			
			GameUser receiver = gameUserService.getGameUserByName(data.getUname());
			
			
			
			GroupInvite gi = new GroupInvite();
			GameGroup group = gameGroupService.getGameGroupByKey(new Long(data.getId()), sender.getUname());
			
			gi.setGistat("PENDING");
			gi.setGameGroup(group);
			gi.setGameUser(receiver);
			gi.setGirec(receiver.getUname());
			gi.setGroupId(group.getGrid());
			gi.setGroupMaster(group.getGrgm());
			
			
			groupInviteService.save(gi);
			


			return new ResponseEntity<String>("Group Invite sent!", HttpStatus.OK);

		}
		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/getFriendRequestSent",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> getFriendRequestSent(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			if(data == "" || data==null){
				return new ResponseEntity<String>("Error occured, please reload page and try again later.", HttpStatus.OK);
			}
			
			Active current = (Active) request.getSession().getAttribute("user");
			
			GtUser active = current.getActuser();

			
			if(friendRequestService.getRequestBySenderReceiver(data, active.getUname(), "PENDING")!=null){
				return new ResponseEntity<String>("You've already sent friend request to this user.", HttpStatus.OK);	
			}
			


			return new ResponseEntity<String>("No Friend Request!", HttpStatus.OK);

		}
		return null;
		
	}


}
