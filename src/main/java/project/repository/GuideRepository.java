package project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import project.domain.Guide;
import project.domain.GuidePK;

public interface GuideRepository extends Repository<Guide, GuidePK> {
	
	public List<Guide> findAll();
	
	public Guide save(Guide g);
	
	@Query("select g from Guide g where UPPER(g.guname) LIKE UPPER( ?1 )")
	public List<Guide> getAllByName(String name);
	
	@Query("select g from Guide g where g.gameGameid = ?1 ")
	public List<Guide> getAllByGame(Long id);
	
	@Query("select g from Guide g where UPPER(g.game.gamename) LIKE UPPER( ?1 ) ")
	public List<Guide> getAllByGameName(String name);
	
	@Query("select g from Guide g where UPPER(g.gameUserUname) LIKE UPPER( ?1 )")
	public List<Guide> getAllByAuthor(String name);
	
	@Query("select g from Guide g where g.guid = ?1 AND g.gameGameid = ?2 AND g.gameUserUname = ?3")
	public Guide getGuideByKey(Long id, Long gameId, String uname);
	
	@Modifying
	@Query("delete from Guide g where g.guid = ?1 AND g.gameGameid = ?2 AND g.gameUserUname = ?3")
	public void removeGuideByKey(Long id, Long gameId, String uname);
	
	@Modifying
	@Query("update Guide g set g.gucont = ?4 where g.guid = ?1 AND g.gameGameid = ?2 AND g.gameUserUname = ?3 ")
	public void updateGuideContent(Long id, Long gameId, String uname, String cont);
	
	@Modifying
	@Query("update Guide g set g.guname = ?4 where g.guid = ?1 AND g.gameGameid = ?2 AND g.gameUserUname = ?3 ")
	public void updateGuideName(Long id, Long gameId, String uname, String name);
	
	@Modifying
	@Query("update Guide g set g.gucont = ?4 , g.guname = ?5 where g.guid = ?1 AND g.gameGameid = ?2 AND g.gameUserUname = ?3 ")
	public void updateGuideContentAndName(Long id, Long gameId, String uname, String cont, String name);

}
