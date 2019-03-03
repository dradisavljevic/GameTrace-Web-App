package project.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import project.domain.Game;
import project.domain.Review;
import project.domain.ReviewPK;

public interface ReviewRepository extends Repository<Review, ReviewPK> {

	public List<Review> findAll();
	
	public Review save(Review r);
	
	@Query("select r from Review r where r.game.id = ?1 ")
	public List<Review> getAllByGameId(Long id);
	
	@Query("select r from Review r where r.revid = ?1 AND r.gameGameid = ?2 AND r.userUname = ?3")
	public Review getReviewByKey(Long id, Long gameId, String user);
	
	@Query("select r from Review r where r.gameGameid = ?1 AND r.userUname = ?2")
	public Review getReviewByUserGame(Long gameId, String user);
	
	@Modifying
	@Query("delete from Review r where r.revid = ?1 AND r.gameGameid = ?2 AND r.userUname = ?3")
	public void removeReviewByKey(Long id, Long gameId, String user);
	
	@Modifying
	@Query("update Review r set r.revcont = ?4 where r.revid = ?1 AND r.gameGameid = ?2 AND r.userUname = ?3 ")
	public void updateReviewContent(Long id, Long gameId, String user, String cont);
	
	@Modifying
	@Query("update Review r set r.revtitle = ?4 where r.revid = ?1 AND r.gameGameid = ?2 AND r.userUname = ?3 ")
	public void updateReviewTitle(Long id, Long gameId, String user, String title);
	
	@Modifying
	@Query("update Review r set r.revcont = ?4, r.revtitle = ?5 where r.revid = ?1 AND r.gameGameid = ?2 AND r.userUname = ?3 ")
	public void updateReview(Long id, Long gameId, String user, String cont, String title);
	
	
	
}
