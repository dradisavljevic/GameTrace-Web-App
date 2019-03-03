package project.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

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
