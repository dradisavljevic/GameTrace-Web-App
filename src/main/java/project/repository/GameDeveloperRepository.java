package project.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import project.domain.GameDeveloper;

public interface GameDeveloperRepository extends Repository<GameDeveloper, Long> {
	
	public List<GameDeveloper> findAll();
	
	@Query("select g from GameDeveloper g where g.gdcount != 0")
	public List<GameDeveloper> getAll();
	
	public GameDeveloper save(GameDeveloper g);
	
	@Query("select g from GameDeveloper g where UPPER(g.gdname) LIKE UPPER( ?1 ) AND g.gdcount != 0")
	public List<GameDeveloper> getAllByName(String name);
	
	@Query("select g from GameDeveloper g where g.gdcount = ?1 AND g.gdcount != 0")
	public List<GameDeveloper> getAllByCount(BigDecimal count);
	
	@Query("select g from GameDeveloper g where g.gdcount >= ?1 AND g.gdcount != 0")
	public List<GameDeveloper> getAllByGreaterCount(BigDecimal count);
	
	@Query("select g from GameDeveloper g where g.gdcount <= ?1 AND g.gdcount != 0")
	public List<GameDeveloper> getAllByLowerCount(BigDecimal count);
	
	@Query("select g from GameDeveloper g where g.gdid = ?1")
	public GameDeveloper getGameDeveloperById(Long id);
	
	@Query("select g from GameDeveloper g where UPPER(g.gdname) = UPPER( ?1 )")
	public GameDeveloper getGameDeveloperByName(String name);
	
	@Modifying
	@Query("delete from GameDeveloper g where g.gdid = ?1")
	public void removeGameDeveloperById(Long id);
	
	@Modifying
	@Query("update GameDeveloper g set g.gdname = ?2 where g.gdid = ?1 ")
	public void updateGameDeveloperName(Long id, String name);
}
