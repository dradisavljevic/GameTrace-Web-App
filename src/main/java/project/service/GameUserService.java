package project.service;

import java.util.Date;
import java.util.List;

import project.domain.GameUser;

public interface GameUserService {
	
	List<GameUser> findAll();
	
	GameUser save(GameUser g);
	
	List<GameUser> getAllByName(String name);
	
	GameUser getGameUserByName(String name);
	
	void removeGameUserByName(String name);
	
	void updateGameUserRealName(String uname, String rname);
	
	void updateGameUserCountry(String uname, String country);
	
	void updateGameUserBiography(String uname, String ubio);
	
	void updateGameUserRealNameAndCountry(String uname, String rname, String country);
	
	void updateGameUserRealNameAndBiography(String uname, String rname, String ubio);
	
	void updateGameUserBiographyAndCountry(String uname, String ubio, String country);

	void updateGameUserRealNameAndCountryAndBiography(String uname, String rname, String country, String ubio);
	
	void updateGameUserDOB(String uname, Date dob);

}
