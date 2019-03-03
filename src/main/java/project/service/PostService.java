package project.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import project.domain.Post;

public interface PostService {

	List<Post> findAll();
	
	Post save(Post p);
	
	List<Post> getAllByContent(String content);
	
	Post getPostByKey(String poster, Long topic, Date date, Timestamp time);
	
	void removePostByKey(String poster, Long topic, Date date, Timestamp time);
	
	void updatePostContent(String poster, Long topic, Date date, Timestamp time, String content);

	void removePostByPoster(String poster);
	
	void removePostByTopic(Long topic);
}
