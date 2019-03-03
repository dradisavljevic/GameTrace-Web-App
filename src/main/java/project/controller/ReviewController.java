package project.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import project.domain.Active;
import project.domain.Game;
import project.domain.GtUser;
import project.domain.Play;
import project.domain.Review;
import project.domain.dto.GuideKeyDTO;
import project.service.GameService;
import project.service.GtUserService;
import project.service.PlayService;
import project.service.ReviewService;

@RequestMapping("/reviews")
@Controller
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private GtUserService gtUserService;
	
	@Autowired
	private GameService gamesService;
	
	@Autowired
	private PlayService playService;
	
	@RequestMapping(value = "/getReviews",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Review>> getReviews(@Context HttpServletRequest request) {
		
			List<Review> reviews = reviewService.findAll();
			

			
			return new ResponseEntity<List<Review>>(reviews, HttpStatus.OK);
		
	}
	
	@Transactional
	@RequestMapping(value = "/getReview",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<Review> getReview(@Context HttpServletRequest request, @RequestBody GuideKeyDTO data) {
		
		if (data != null) {
			Long id = new Long(data.getId());
			Long gameId = new Long(data.getGameId());
			String uname = data.getUname();

			Review review = reviewService.getReviewByKey(id, gameId, uname);

			return new ResponseEntity<Review>(review, HttpStatus.OK);

		}
		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/addReview",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> addReview(@Context HttpServletRequest request, @RequestBody Review data) {
		if (data != null) {
			
			if(data.getRevtitle() == "" || data.getRevtitle()==null){
				return new ResponseEntity<String>("Please, fill in the review title field.", HttpStatus.OK);
			} else if (data.getRevcont() == "" || data.getRevcont()==null){
				return new ResponseEntity<String>("Please, fill in the review content field.", HttpStatus.OK);
			}
			
			

			Active current = (Active) request.getSession().getAttribute("user");
			
			List<Play> playovi = playService.getPlaysByGameAndUser(current.getActuser().getUname(), data.getGameGameid());
			
			if(playovi==null || playovi.isEmpty()){
				return new ResponseEntity<String>("You must play game atleast once to be able to review it.", HttpStatus.OK);
			}
			
			
			GtUser user = gtUserService.getGtUserByName(current.getActuser().getUname());
			data.setGtUser(user);
			data.setUserUname(current.getActuser().getUname());
			
			if(reviewService.getReviewByUserGame(data.getGameGameid(), data.getUserUname())!=null){
				return new ResponseEntity<String>("You've already reviewed this game. Consider updating your old review.", HttpStatus.OK);
			}

			
			Game game = gamesService.getGameById(data.getGameGameid());
			data.setGame(game);
			
			game.getReviews().add(data);
			user.getReviews().add(data);
			
			reviewService.save(data);
			


			return new ResponseEntity<String>("Review successfully added", HttpStatus.OK);

		}
		return null;
		
	}
	
	
	
	@Transactional
	@RequestMapping(value = "/updateReview",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> updateReview(@Context HttpServletRequest request, @RequestBody Review data) {
		if (data != null) {
			
			if(data.getRevtitle() == "" || data.getRevtitle()==null){
				return new ResponseEntity<String>("Please, fill in the review title field.", HttpStatus.OK);
			} else if (data.getRevcont() == "" || data.getRevcont()==null){
				return new ResponseEntity<String>("Please, fill in the review content field.", HttpStatus.OK);
			}
			
			

			Long gameId = new Long(data.getGameGameid());
			
			Long id = new Long(data.getRevid());
			
			Review review = reviewService.getReviewByKey(id, gameId, data.getUserUname());
			
			review.setRevtitle(data.getRevtitle());
			review.setRevcont(data.getRevcont());

			
			
			
			reviewService.save(review);
			


			return new ResponseEntity<String>("Review successfully updated", HttpStatus.OK);

		}
		return null;
		
	}
	

}
