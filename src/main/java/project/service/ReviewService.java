package project.service;

import java.util.List;

import project.domain.Review;

public interface ReviewService {

	List<Review> findAll();
	
	Review save(Review r);
	
	List<Review> getAllByGameId(Long id);
	
	Review getReviewByKey(Long id, Long gameId, String user);
	
	void removeReviewByKey(Long id, Long gameId, String user);
	
	void updateReviewContent(Long id, Long gameId, String user, String cont);
	
	void updateReviewTitle(Long id, Long gameId, String user, String title);
	
	void updateReview(Long id, Long gameId, String user, String cont, String title);
	
	Review getReviewByUserGame(Long gameId, String user);

}
