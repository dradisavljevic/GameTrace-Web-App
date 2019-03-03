package project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import project.domain.Game;
import project.domain.Topic;

public interface TopicRepository extends Repository<Topic, Long> {
	
	public List<Topic> findAll();
	
	public Topic save(Topic t);
	
	@Query("select t from Topic t where UPPER(t.tptitle) LIKE UPPER( ?1 )")
	public List<Topic> getAllByName(String name);
	
	@Query("select t from Topic t where t.tpid = ?1")
	public Topic getTopicById(Long id);
	
	@Query("select t from Topic t where UPPER(t.gtUser.uname) LIKE UPPER( ?1 )")
	public List<Topic> getAllByUser(String name);
	
	@Modifying
	@Query("delete from Topic t where t.tpid = ?1")
	public void removeTopicById(Long id);
	
	@Modifying
	@Query("update Topic t set t.tptitle = ?2 where t.tpid = ?1 ")
	public void updateTopicName(Long id, String title);

}
