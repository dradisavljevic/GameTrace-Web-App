package project.service;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import project.domain.FriendRequest;

public interface FriendRequestService {
	
	List<FriendRequest> findAll();
	
	FriendRequest save(FriendRequest f);
	
	FriendRequest getRequestById(Long id);
	
	List<FriendRequest> getAllByReceiver(String name, String stat);
	
	void removeFriendRequestById(Long id);
	
	FriendRequest getRequestBySenderReceiver(String rec, String send, String stat);

}
