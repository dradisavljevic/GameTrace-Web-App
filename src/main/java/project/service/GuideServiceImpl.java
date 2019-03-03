package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import project.domain.Guide;
import project.repository.GuideRepository;

@Service
public class GuideServiceImpl implements GuideService{
	
	@Autowired
	private GuideRepository guideRepository;

	@Override
	public List<Guide> findAll() {
		return guideRepository.findAll();
	}

	@Override
	public Guide save(Guide g) {
		Assert.notNull(g);
		return guideRepository.save(g);
	}

	@Override
	public List<Guide> getAllByName(String name) {
		Assert.notNull(name);
		return guideRepository.getAllByName(name);
	}

	@Override
	public Guide getGuideByKey(Long id, Long gameId, String uname) {
		Assert.notNull(gameId);
		Assert.notNull(uname);
		Assert.notNull(id);
		return guideRepository.getGuideByKey(id, gameId, uname);
	}

	@Override
	public void removeGuideByKey(Long id, Long gameId, String uname) {
		Assert.notNull(gameId);
		Assert.notNull(uname);
		Assert.notNull(id);
		guideRepository.removeGuideByKey(id, gameId, uname);
	}

	@Override
	public void updateGuideContent(Long id, Long gameId, String uname, String cont) {
		Assert.notNull(gameId);
		Assert.notNull(uname);
		Assert.notNull(cont);
		Assert.notNull(id);
		guideRepository.updateGuideContent(id, gameId, uname, cont);
	}

	@Override
	public void updateGuideName(Long id, Long gameId, String uname, String name) {
		Assert.notNull(gameId);
		Assert.notNull(uname);
		Assert.notNull(name);
		Assert.notNull(id);
		guideRepository.updateGuideName(id, gameId, uname, name);
	}

	@Override
	public void updateGuideContentAndName(Long id, Long gameId, String uname, String cont, String name) {
		Assert.notNull(gameId);
		Assert.notNull(uname);
		Assert.notNull(cont);
		Assert.notNull(id);
		Assert.notNull(name);
		guideRepository.updateGuideContentAndName(id, gameId, uname, cont, name);
	}

	@Override
	public List<Guide> getAllByGame(Long id) {
		Assert.notNull(id);
		return guideRepository.getAllByGame(id);
	}

	@Override
	public List<Guide> getAllByAuthor(String name) {
		Assert.notNull(name);
		return guideRepository.getAllByAuthor(name);
	}

	@Override
	public List<Guide> getAllByGameName(String name) {
		Assert.notNull(name);
		return guideRepository.getAllByGameName(name);
	}

}
