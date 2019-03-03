package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import project.domain.Streamer;
import project.repository.StreamerRepository;

@Service
public class StreamerServiceImpl implements StreamerService {
	
	@Autowired
	private StreamerRepository streamerRepository;

	@Override
	public List<Streamer> findAll() {
		return streamerRepository.findAll();
	}

	@Override
	public Streamer save(Streamer s) {
		Assert.notNull(s);
		return streamerRepository.save(s);
	}

	@Override
	public List<Streamer> getAllByName(String name) {
		Assert.notNull(name);
		return streamerRepository.getAllByName(name);
	}


	@Override
	public Streamer getStreamerById(Long id) {
		Assert.notNull(id);
		return streamerRepository.getStreamerById(id);
	}

	@Override
	public void removeStreamerById(Long id) {
		Assert.notNull(id);
		streamerRepository.removeStreamerById(id);
	}

	@Override
	public void updateStreamerName(Long id, String name) {
		Assert.notNull(id);
		Assert.notNull(name);
		streamerRepository.updateStreamerName(id, name);
	}

	@Override
	public void updateStreamer(Long id, String name, String url) {
		Assert.notNull(id);
		Assert.notNull(name);
		Assert.notNull(url);
		streamerRepository.updateStreamer(id, name, url);
	}

	@Override
	public void updateStreamerUrl(Long id, String url) {
		Assert.notNull(id);
		Assert.notNull(url);
		streamerRepository.updateStreamerUrl(id, url);
	}

	@Override
	public Streamer getStreamerByNameAndUrl(String name, String url) {
		Assert.notNull(name);
		Assert.notNull(url);
		return streamerRepository.getStreamerByNameAndUrl(name, url);
	}

}
