package project.repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;


import project.domain.Play;
import project.domain.PlayPK;

public interface PlayRepository extends Repository<Play, PlayPK> {

	public List<Play> findAll();
	
	public Play save(Play p);
	
	@Query("select p from Play p where p.id.gameUserUname= ?1 AND p.id.gameGameid = ?2")
	public List<Play> getPlaysByGameAndUser(String user, Long game);
	
	@Query("select p from Play p where p.id.gameUserUname = ?1 AND p.id.gameGameid = ?2 AND p.id.playdate = ?3 AND p.id.ptime = ?4")
	public Play getPlayByKey(String name, Long id, Date date, Timestamp time);
	
	
	@Modifying
	@Query("delete from Play p where p.id.gameUserUname = ?1 AND p.id.gameGameid = ?2 AND p.id.playdate = ?3 AND p.id.ptime = ?4")
	public void removePlayByKey(String name, Long id, Date date, Timestamp time);
	
	@Modifying
	@Query("delete from Play p where p.id.gameUserUname = ?1")
	public void removePlayByUser(String name);
	
	@Modifying
	@Query("delete from Play p where p.id.gameGameid = ?14")
	public void removePlayByGame(Long id);
}
