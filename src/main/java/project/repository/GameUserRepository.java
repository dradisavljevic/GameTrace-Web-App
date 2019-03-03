package project.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import project.domain.GameUser;

public interface GameUserRepository extends Repository<GameUser, String> {
	
	public List<GameUser> findAll();
	
	public GameUser save(GameUser g);
	
	@Query("select g from GameUser g where UPPER(g.uname) LIKE UPPER( ?1 )")
	public List<GameUser> getAllByName(String name);
	
	@Query("select g from GameUser g where g.uname = ?1")
	public GameUser getGameUserByName(String name);
	
	@Modifying
	@Query("delete from GameUser g where g.uname = ?1")
	public void removeGameUserByName(String name);
	
	@Modifying
	@Query("update GameUser g set g.rname = ?2 where g.uname = ?1 ")
	public void updateGameUserRealName(String uname, String rname);
	
	@Modifying
	@Query("update GameUser g set g.ucountry = ?2 where g.uname = ?1 ")
	public void updateGameUserCountry(String uname, String country);
	
	@Modifying
	@Query("update GameUser g set g.ubio = ?2 where g.uname = ?1 ")
	public void updateGameUserBiography(String uname, String ubio);
	
	@Modifying
	@Query("update GameUser g set g.rname = ?2 , g.ucountry = ?3 where g.uname = ?1 ")
	public void updateGameUserRealNameAndCountry(String uname, String rname, String country);
	
	@Modifying
	@Query("update GameUser g set g.rname = ?2 , g.ubio = ?3 where g.uname = ?1 ")
	public void updateGameUserRealNameAndBiography(String uname, String rname, String ubio);
	
	@Modifying
	@Query("update GameUser g set g.ubio = ?2 , g.ucountry = ?3 where g.uname = ?1 ")
	public void updateGameUserBiographyAndCountry(String uname, String ubio, String country);

	@Modifying
	@Query("update GameUser g set g.rname = ?2 , g.ucountry = ?3 , g.ubio = ?4 where g.uname = ?1 ")
	public void updateGameUserRealNameAndCountryAndBiography(String uname, String rname, String country, String ubio);
	
	@Modifying
	@Query("update GameUser g set g.udob = ?2 where g.uname = ?1 ")
	public void updateGameUserDOB(String uname, Date dob);
}
