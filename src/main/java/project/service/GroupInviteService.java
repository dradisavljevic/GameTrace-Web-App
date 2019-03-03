package project.service;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import project.domain.GroupInvite;

public interface GroupInviteService {
	
	List<GroupInvite> findAll();
	
	GroupInvite save(GroupInvite g);
	
	GroupInvite getInviteById(Long id);
	
	void removeGroupInviteById(Long id);
	
	List<GroupInvite> getAllByReceiver(String name, String stat);
	
	GroupInvite getRequestBySenderReceiver(String rec, Long id, String send, String stat);

}
