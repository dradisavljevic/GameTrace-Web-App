package project.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import project.domain.Game;
import project.domain.dto.CompactGameDTO;


public interface GameRepository extends Repository<Game, Long> {
	
	public List<Game> findAll();
	
	public Game save(Game g);
	
	@Query("select g from Game g where UPPER(g.gamename) LIKE UPPER( ?1 )")
	public List<Game> getAllByName(String name);
	
	@Query("select g from Game g where Upper(substr(g.gamename,1,1)) = UPPER( ?1 )")
	public List<Game> getAllByNameStarting(String name);
	
	@Query("select g from Game g where Upper(substr(g.gamename,1,1)) NOT IN ('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z')")
	public List<Game> getAllByHash();
	
	@Query("select new project.domain.dto.CompactGameDTO(g.gamename, g.gamery, g.gameid) from Game g")
	public List<CompactGameDTO> fillGameList();
	
	@Query("select g from Game g join fetch g.streamers s where s.strid = ?1")
	public List<Game> getAllByStreamer(Long id);
	
	@Query("select g from Game g where g.gamery = TO_NUMBER ( ?1 )")
	public List<Game> getAllByYear(BigDecimal year);
	
	@Query("select g from Game g where g.gameid = ?1")
	public Game getGameById(Long id);
	
	@Query("select g from Game g where UPPER(g.gamename)=UPPER(?1) AND g.gamery = ?2")
	public Game getGameByNameAndYear(String name, BigDecimal year);
	
	@Modifying
	@Query("delete from Game g where g.gameid = ?1")
	public void removeGameById(Long id);
	
	@Modifying
	@Query("update Game g set g.gamedesc = ?2 where g.gameid = ?1 ")
	public void updateGameDescription(Long id, String desc);
	
	@Modifying
	@Query("update Game g set g.gamedr = ?2 where g.gameid = ?1 ")
	public void updateGameDetectionRule(Long id, String detection);
	
	@Modifying
	@Query("update Game g set g.gimg = ?2 where g.gameid = ?1 ")
	public void updateGameImage(Long id, String gimg);
	
	@Modifying
	@Query("update Game g set g.gamery = ?2 where g.gameid = ?1 ")
	public void updateGameYear(Long id, BigDecimal year);
	
	@Modifying
	@Query("update Game g set g.gamename = ?2 where g.gameid = ?1 ")
	public void updateGameName(Long id, String name);
	
	@Modifying
	@Query("update Game g set g.gamename = ?2, g.gamedesc = ?3, g.gamedr = ?4, g.gimg = ?5, g.gamery = ?6 where g.gameid = ?1 ")
	public void updateGame(Long id, String name, String desc, String rule, String gimg, BigDecimal year);

}
