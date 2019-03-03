package project.service;

import java.util.List;

import project.domain.Topic;

public interface TopicService {

	List<Topic> findAll();
	
	Topic save(Topic t);
	
	List<Topic> getAllByName(String name);
	
	Topic getTopicById(Long id);
	
	List<Topic> getAllByUser(String name);
	
	void removeTopicById(Long id);
	
	void updateTopicName(Long id, String title);

}
