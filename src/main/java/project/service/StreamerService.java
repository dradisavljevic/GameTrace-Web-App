package project.service;

import java.util.List;

import project.domain.Streamer;

public interface StreamerService {

	List<Streamer> findAll();
	
	Streamer save(Streamer s);
	
	List<Streamer> getAllByName(String name);
	
	
	Streamer getStreamerById(Long id);
	
	void removeStreamerById(Long id);
	
	void updateStreamerName(Long id, String name);
	
	void updateStreamer(Long id, String name, String url);
	
	void updateStreamerUrl(Long id, String url);
	
	Streamer getStreamerByNameAndUrl(String name, String url);

}
