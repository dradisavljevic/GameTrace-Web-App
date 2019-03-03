package project.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;


import project.domain.GameGroup;
import project.domain.GameGroupPK;

public interface GameGroupRepository extends Repository<GameGroup, GameGroupPK> {
	
	public List<GameGroup> findAll();
	
	public GameGroup save(GameGroup g);
	
	@Query("select g from GameGroup g where UPPER(g.grname) LIKE UPPER( ?1 )")
	public List<GameGroup> getAllByName(String name);
	
	@Query("select g from GameGroup g where g.grgm =  ?1 ")
	public List<GameGroup> getAllByMaster(String name);
	
	@Query("select g from GameGroup g where g.grid = ?1 AND g.grgm = ?2")
	public GameGroup getGameGroupByKey(Long id, String name);
	
	@Query("select g from GameGroup g where UPPER( g.grname ) = UPPER( ?1 )")
	public GameGroup getGameGroupByName(String name);
	
	@Modifying
	@Query("delete from GameGroup g where g.grid = ?1 AND g.grgm = ?2")
	public void removeGameGroupByKey(Long id, String name);
	
	@Modifying
	@Query("update GameGroup g set g.grname = ?3 where g.grid = ?1 AND g.grgm = ?2")
	public void updateGameGroupName(Long id, String master, String name);

}
