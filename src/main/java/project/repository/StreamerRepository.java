package project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import project.domain.Game;
import project.domain.Streamer;

public interface StreamerRepository extends Repository<Streamer, Long> {
	
	public List<Streamer> findAll();
	
	public Streamer save(Streamer s);
	
	@Query("select s from Streamer s where UPPER(s.strname) LIKE UPPER( ?1 )")
	public List<Streamer> getAllByName(String name);
	
	
	@Query("select s from Streamer s where s.strid = ?1 ")
	public Streamer getStreamerById(Long id);
	
	@Query("select s from Streamer s where UPPER( s.strname ) = UPPER( ?1 ) AND UPPER( s.strurl ) = UPPER( ?2 )")
	public Streamer getStreamerByNameAndUrl(String name, String url);
	
	@Modifying
	@Query("delete from Streamer s where s.strid = ?1 ")
	public void removeStreamerById(Long id);
	
	@Modifying
	@Query("update Streamer s set s.strname = ?2 where s.strid = ?1 ")
	public void updateStreamerName(Long id, String name);
	
	@Modifying
	@Query("update Streamer s set s.strname = ?2 ,  s.strurl = ?3 where s.strid = ?1 ")
	public void updateStreamer(Long id, String name, String url);
	
	@Modifying
	@Query("update Streamer s set s.strurl = ?2 where s.strid = ?1 ")
	public void updateStreamerUrl(Long id,  String url);

}
