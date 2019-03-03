package project.service;

import java.util.List;

import project.domain.GtUser;

public interface GtUserService {
	
	List<GtUser> findAll();
	
	GtUser save(GtUser g);
	
	List<GtUser> getAllByName(String name);
	
	GtUser getGtUserByName(String name);
	
	GtUser getGtUserByEmail(String email);
	
	void removeGtUserByName(String name);
	
	void updateGtUserPassword(String uname, String pword);
	
	void updateGtUserEmail(String uname, String email);
	
	 void updateGtUserAvatar(String uname, String uavat);
	
	void updateGtUserPasswordAndEmail(String uname, String pword, String email);
	
	void updateGtUserPasswordAndAvatar(String uname, String pword, String uavat);
	
	void updateGtUserEmailAndAvatar(String uname, String email, String uavat);
	
	void updateGtUserPasswordAndEmailAndAvatar(String uname, String pword, String email, String uavat);
}
