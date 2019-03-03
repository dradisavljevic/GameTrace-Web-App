package project.service;

import java.util.List;

import project.domain.Guide;

public interface GuideService {
	
	List<Guide> findAll();
	
	Guide save(Guide g);
	
	List<Guide> getAllByName(String name);
	
	List<Guide> getAllByGame(Long id);
	
	List<Guide> getAllByGameName(String name);
	
	List<Guide> getAllByAuthor(String name);
	
	Guide getGuideByKey(Long id, Long gameId, String uname);
	
	void removeGuideByKey(Long id, Long gameId, String uname);
	
	void updateGuideContent(Long id, Long gameId, String uname, String cont);
	
	void updateGuideName(Long id, Long gameId, String uname, String name);
	
	void updateGuideContentAndName(Long id, Long gameId, String uname, String cont, String name);

}
