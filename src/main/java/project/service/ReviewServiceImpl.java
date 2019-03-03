package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import project.domain.Review;
import project.repository.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService{
	
	@Autowired
	private ReviewRepository reviewRepository;

	@Override
	public List<Review> findAll() {
		return reviewRepository.findAll();
	}

	@Override
	public Review save(Review r) {
		Assert.notNull(r);
		return reviewRepository.save(r);
	}

	@Override
	public List<Review> getAllByGameId(Long id) {
		Assert.notNull(id);
		return reviewRepository.getAllByGameId(id);
	}

	@Override
	public Review getReviewByKey(Long id, Long gameId, String user) {
		Assert.notNull(id);
		Assert.notNull(gameId);
		Assert.notNull(user);
		return reviewRepository.getReviewByKey(id, gameId, user);
	}

	@Override
	public void removeReviewByKey(Long id, Long gameId, String user) {
		Assert.notNull(id);
		Assert.notNull(gameId);
		Assert.notNull(user);
		reviewRepository.removeReviewByKey(id, gameId, user);
	}

	@Override
	public void updateReviewContent(Long id, Long gameId, String user, String cont) {
		Assert.notNull(id);
		Assert.notNull(gameId);
		Assert.notNull(user);
		Assert.notNull(cont);
		reviewRepository.updateReviewContent(id, gameId, user, cont);
	}

	@Override
	public void updateReviewTitle(Long id, Long gameId, String user, String title) {
		Assert.notNull(id);
		Assert.notNull(gameId);
		Assert.notNull(user);
		Assert.notNull(title);
		reviewRepository.updateReviewTitle(id, gameId, user, title);
	}

	@Override
	public void updateReview(Long id, Long gameId, String user, String cont, String title) {
		Assert.notNull(id);
		Assert.notNull(gameId);
		Assert.notNull(user);
		Assert.notNull(cont);
		Assert.notNull(title);
		reviewRepository.updateReview(id, gameId, user, cont, title);
	}

	@Override
	public Review getReviewByUserGame(Long gameId, String user) {
		Assert.notNull(gameId);
		Assert.notNull(user);
		return reviewRepository.getReviewByUserGame(gameId, user);
	}

	

}
