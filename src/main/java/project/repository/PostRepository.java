package project.repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import project.domain.Game;
import project.domain.Post;
import project.domain.PostPK;

public interface PostRepository extends Repository<Post, PostPK> {
	
	public List<Post> findAll();
	
	public Post save(Post p);
	
	@Query("select p from Post p where UPPER(p.postcont) LIKE UPPER( ?1 )")
	public List<Post> getAllByContent(String content);
	
	@Query("select p from Post p where p.id.postpb = ?1 AND p.id.posttid = ?2 AND p.id.postdate = ?3 AND p.id.posttime = ?4")
	public Post getPostByKey(String poster, Long topic, Date date, Timestamp time);
	
	@Modifying
	@Query("delete from Post p where p.id.postpb = ?1 AND p.id.posttid = ?2 AND p.id.postdate = ?3 AND p.id.posttime = ?4")
	public void removePostByKey(String poster, Long topic, Date date, Timestamp time);
	
	@Modifying
	@Query("update Post p set p.postcont = ?5 where p.id.postpb = ?1 AND p.id.posttid = ?2 AND p.id.postdate = ?3 AND p.id.posttime = ?4 ")
	public void updatePostContent(String poster, Long topic, Date date, Timestamp time, String content);

	@Modifying
	@Query("delete from Post p where p.id.postpb = ?1")
	public void removePostByPoster(String poster);
	
	@Modifying
	@Query("delete from Post p where p.id.posttid = ?1 ")
	public void removePostByTopic(Long topic);
}
