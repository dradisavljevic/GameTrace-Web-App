package project.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the GAME database table.
 * 
 */
@NamedNativeQueries({
@NamedNativeQuery(
	    name = "get_developed_games",
	    query = "{ ? = call developPkg.GET_DEVELOPED_GAMES( ? ) }",
	    callable = true,
	    resultSetMapping = "developed_games"
	),
@NamedNativeQuery(
	    name = "get_recommendation",
	    query = "{ ? = call recommendPkg.GET_RECOMMENDED( ? ) }",
	    callable = true,
	    resultSetMapping = "developed_games"
	)
})
	@SqlResultSetMapping(
	    name = "developed_games",
	    entities = {
	        @EntityResult(
	            entityClass = Game.class,
	            fields = {
	                @FieldResult( 
	                    name = "gameid", 
	                    column = "gameid"
	                ),
	                @FieldResult( 
		                    name = "gamedesc", 
		                    column = "gamedesc"
		                ),
	                @FieldResult( 
	                    name = "gamedr", 
	                    column = "gamedr"
	                ),
	                @FieldResult( 
		                    name = "gamename", 
		                    column = "gamename"
		                ),
	                @FieldResult( 
	                    name = "gamepc", 
	                    column = "gamepc"
	                ),
	                @FieldResult( 
		                    name = "gamepday", 
		                    column = "gamepday"
		                ),
	                @FieldResult( 
		                    name = "gamephour", 
		                    column = "gamephour"
		                ),
	                @FieldResult( 
		                    name = "gamepmin", 
		                    column = "gamepmin"
		                ),
	                @FieldResult( 
		                    name = "gamepsec", 
		                    column = "gamepsec"
		                ),
	                @FieldResult( 
		                    name = "gamery", 
		                    column = "gamery"
		                ),
	                @FieldResult( 
		                    name = "gamepn", 
		                    column = "gamepn"
		                ),
	                @FieldResult( 
		                    name = "gimg", 
		                    column = "gimg"
		                ),
	                @FieldResult( 
		                    name = "gameicon", 
		                    column = "gameicon"
		                ),
	            }
	        )
	    }
	)
@Entity
@NamedQuery(name="Game.findAll", query="SELECT g FROM Game g")
public class Game implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GAME_GAMEID_GENERATOR", sequenceName="GAME_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GAME_GAMEID_GENERATOR")
	private long gameid;

	private String gamedesc;


	private String gamedr;

	private String gamename;

	private BigDecimal gamepc;

	private BigDecimal gamepday;

	private BigDecimal gamephour;

	private BigDecimal gamepmin;

	private BigDecimal gamepsec;

	private BigDecimal gamery;
	
	@Column(name = "gamepn", insertable=false)
	private BigDecimal gamepn;
	
	private String gimg;
	
	private String gameicon;
	
	//bi-directional many-to-one association to GameUser
	@OneToMany(mappedBy="game")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private List<GameUser> gameUsers;

	//bi-directional many-to-many association to GameDeveloper
	@ManyToMany(mappedBy="games", cascade={CascadeType.ALL})
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private List<GameDeveloper> gameDevelopers;

	//bi-directional many-to-one association to Guide
	@OneToMany(mappedBy="game")
	@JsonIgnore
	private List<Guide> guides;

	//bi-directional many-to-one association to Play
	@OneToMany(mappedBy="game")
	@JsonIgnore
	private List<Play> plays;

	//bi-directional many-to-one association to Ranks
	@OneToMany(mappedBy="game")
	@JsonIgnore
	private List<Ranks> ranks;

	//bi-directional many-to-one association to Request
	@OneToMany(mappedBy="game")
	@JsonIgnore
	private List<Request> requests;

	//bi-directional many-to-one association to Review
	@OneToMany(mappedBy="game")

	private List<Review> reviews;

	//bi-directional many-to-many association to Streamer
	@ManyToMany(mappedBy="games")

	private List<Streamer> streamers;
	
	@OneToMany(mappedBy="gameGameid")
	@JsonIgnore
	private List<Item> items;
	
	//bi-directional many-to-many association to Genre
		@ManyToMany
		@JoinTable(
			name="GAME_GENRE"
			, joinColumns={
				@JoinColumn(name="GAME_GAMEID")
				}
			, inverseJoinColumns={
				@JoinColumn(name="GENRE_GNRID")
				}
			)
		private List<Genre> genres;
	

	public Game() {
	}
	
	

	public List<Genre> getGenres() {
		return genres;
	}



	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}



	public long getGameid() {
		return this.gameid;
	}

	public void setGameid(long gameid) {
		this.gameid = gameid;
	}

	public String getGamedesc() {
		return this.gamedesc;
	}

	public void setGamedesc(String gamedesc) {
		this.gamedesc = gamedesc;
	}

	public String getGamedr() {
		return this.gamedr;
	}

	public void setGamedr(String gamedr) {
		this.gamedr = gamedr;
	}

	public String getGamename() {
		return this.gamename;
	}

	public void setGamename(String gamename) {
		this.gamename = gamename;
	}

	public BigDecimal getGamepc() {
		return this.gamepc;
	}

	public void setGamepc(BigDecimal gamepc) {
		this.gamepc = gamepc;
	}

	public BigDecimal getGamepday() {
		return this.gamepday;
	}

	public void setGamepday(BigDecimal gamepday) {
		this.gamepday = gamepday;
	}

	public BigDecimal getGamephour() {
		return this.gamephour;
	}

	public void setGamephour(BigDecimal gamephour) {
		this.gamephour = gamephour;
	}

	public BigDecimal getGamepmin() {
		return this.gamepmin;
	}

	public void setGamepmin(BigDecimal gamepmin) {
		this.gamepmin = gamepmin;
	}

	public BigDecimal getGamepsec() {
		return this.gamepsec;
	}

	public void setGamepsec(BigDecimal gamepsec) {
		this.gamepsec = gamepsec;
	}

	public BigDecimal getGamery() {
		return this.gamery;
	}

	public void setGamery(BigDecimal gamery) {
		this.gamery = gamery;
	}

	public String getGimg() {
		return this.gimg;
	}

	public void setGimg(String gimg) {
		this.gimg = gimg;
	}

	public List<GameDeveloper> getGameDevelopers() {
		return this.gameDevelopers;
	}

	public void setGameDevelopers(List<GameDeveloper> gameDevelopers) {
		this.gameDevelopers = gameDevelopers;
	}

	public List<Guide> getGuides() {
		return this.guides;
	}

	public void setGuides(List<Guide> guides) {
		this.guides = guides;
	}

	public Guide addGuide(Guide guide) {
		getGuides().add(guide);
		guide.setGame(this);

		return guide;
	}

	public Guide removeGuide(Guide guide) {
		getGuides().remove(guide);
		guide.setGame(null);

		return guide;
	}

	public List<Play> getPlays() {
		return this.plays;
	}

	public void setPlays(List<Play> plays) {
		this.plays = plays;
	}

	public Play addPlay(Play play) {
		getPlays().add(play);
		play.setGame(this);

		return play;
	}

	public Play removePlay(Play play) {
		getPlays().remove(play);
		play.setGame(null);

		return play;
	}

	public List<Ranks> getRanks() {
		return this.ranks;
	}

	public void setRanks(List<Ranks> ranks) {
		this.ranks = ranks;
	}

	public Ranks addRank(Ranks rank) {
		getRanks().add(rank);
		rank.setGame(this);

		return rank;
	}

	public Ranks removeRank(Ranks rank) {
		getRanks().remove(rank);
		rank.setGame(null);

		return rank;
	}

	public List<Request> getRequests() {
		return this.requests;
	}

	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}

	public Request addRequest(Request request) {
		getRequests().add(request);
		request.setGame(this);

		return request;
	}

	public Request removeRequest(Request request) {
		getRequests().remove(request);
		request.setGame(null);

		return request;
	}

	public List<Review> getReviews() {
		return this.reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public Review addReview(Review review) {
		getReviews().add(review);
		review.setGame(this);

		return review;
	}

	public Review removeReview(Review review) {
		getReviews().remove(review);
		review.setGame(null);

		return review;
	}

	public List<Streamer> getStreamers() {
		return this.streamers;
	}

	public void setStreamers(List<Streamer> streamers) {
		this.streamers = streamers;
	}

	public BigDecimal getGamepn() {
		return gamepn;
	}

	public void setGamepn(BigDecimal gamepn) {
		this.gamepn = gamepn;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public List<GameUser> getGameUsers() {
		return gameUsers;
	}

	public void setGameUsers(List<GameUser> gameUsers) {
		this.gameUsers = gameUsers;
	}



	public String getGameicon() {
		return gameicon;
	}



	public void setGameicon(String gameicon) {
		this.gameicon = gameicon;
	}
	
	

	
	
	

}