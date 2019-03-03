package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import project.domain.Topic;
import project.repository.StreamerRepository;
import project.repository.TopicRepository;

@Service
public class TopicServiceImpl implements TopicService {
	
	@Autowired
	private TopicRepository topicRepository;

	@Override
	public List<Topic> findAll() {
		return topicRepository.findAll();
	}

	@Override
	public Topic save(Topic t) {
		Assert.notNull(t);
		return topicRepository.save(t);
	}

	@Override
	public List<Topic> getAllByName(String name) {
		Assert.notNull(name);
		return topicRepository.getAllByName(name);
	}

	@Override
	public Topic getTopicById(Long id) {
		Assert.notNull(id);
		return topicRepository.getTopicById(id);
	}

	@Override
	public List<Topic> getAllByUser(String name) {
		Assert.notNull(name);
		return topicRepository.getAllByUser(name);
	}

	@Override
	public void removeTopicById(Long id) {
		Assert.notNull(id);
		topicRepository.removeTopicById(id);
	}

	@Override
	public void updateTopicName(Long id, String title) {
		Assert.notNull(id);
		Assert.notNull(title);
		topicRepository.updateTopicName(id, title);
	}

}
