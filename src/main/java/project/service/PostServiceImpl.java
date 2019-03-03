package project.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import project.domain.Post;
import project.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepository postRepository;

	@Override
	public List<Post> findAll() {
		return postRepository.findAll();
	}

	@Override
	public Post save(Post p) {
		Assert.notNull(p);
		return postRepository.save(p);
	}

	@Override
	public List<Post> getAllByContent(String content) {
		Assert.notNull(content);
		return postRepository.getAllByContent(content);
	}

	@Override
	public Post getPostByKey(String poster, Long topic, Date date, Timestamp time) {
		Assert.notNull(poster);
		Assert.notNull(topic);
		Assert.notNull(date);
		Assert.notNull(time);
		return postRepository.getPostByKey(poster, topic, date, time);
	}

	@Override
	public void removePostByKey(String poster, Long topic, Date date, Timestamp time) {
		Assert.notNull(poster);
		Assert.notNull(topic);
		Assert.notNull(date);
		Assert.notNull(time);
		postRepository.removePostByKey(poster, topic, date, time);
	}

	@Override
	public void updatePostContent(String poster, Long topic, Date date, Timestamp time, String content) {
		Assert.notNull(poster);
		Assert.notNull(topic);
		Assert.notNull(date);
		Assert.notNull(time);
		Assert.notNull(content);
		postRepository.updatePostContent(poster, topic, date, time, content);
	}

	@Override
	public void removePostByPoster(String poster) {
		Assert.notNull(poster);
		postRepository.removePostByPoster(poster);
	}

	@Override
	public void removePostByTopic(Long topic) {
		Assert.notNull(topic);
		postRepository.removePostByTopic(topic);
	}

}
