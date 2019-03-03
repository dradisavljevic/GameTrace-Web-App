package project.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;




@NamedNativeQuery(
	    name = "get_played_games",
	    query = "{ ? = call eventpkg.get_played_games( ? ) }",
	    callable = true,
	    resultSetMapping = "played_games"
	)


	@SqlResultSetMapping(
	    name = "played_games",
	    entities = {
	        @EntityResult(
	            entityClass = PlayedGame.class,
	            fields = {
	                @FieldResult( 
	                    name = "gameid", 
	                    column = "gameid"
	                ),
	                @FieldResult( 
		                    name = "gamename", 
		                    column = "gamename"
		                ),
	                @FieldResult( 
		                    name = "gamery", 
		                    column = "gamery"
		                ),
	                @FieldResult( 
	                    name = "ptday", 
	                    column = "play_day"
	                ),
	                @FieldResult( 
	                    name = "pthour", 
	                    column = "play_hour"
	                ),
	                @FieldResult( 
		                    name = "ptmin", 
		                    column = "play_min"
		                ),
	                @FieldResult( 
		                    name = "ptsec", 
		                    column = "play_sec"
		                ),
	                @FieldResult( 
		                    name = "gimage", 
		                    column = "gimage"
		                ),
	                @FieldResult( 
		                    name = "rankname", 
		                    column = "rankname"
		                )
	            }
	        )
	    }
	)
@Entity
@Table(name="PLAYED_GAME")
@NamedQuery(name="PlayedGame.findAll", query="SELECT p FROM PlayedGame p")
public class PlayedGame {
	
	
	
	@Id
	private BigDecimal gameid;
	
	private String gamename;
	
	private BigDecimal gamery;
	
	private BigDecimal ptday;

	private BigDecimal pthour;

	private BigDecimal ptmin;

	private BigDecimal ptsec;
	
	private String rankname;
	
	private String gimage;
	
	public PlayedGame() {
		
	}

	public BigDecimal getGameid() {
		return gameid;
	}

	public void setGameid(BigDecimal gameid) {
		this.gameid = gameid;
	}

	public String getGamename() {
		return gamename;
	}

	public void setGamename(String gamename) {
		this.gamename = gamename;
	}

	public BigDecimal getGamery() {
		return gamery;
	}

	public void setGamery(BigDecimal gamery) {
		this.gamery = gamery;
	}

	public BigDecimal getPtday() {
		return ptday;
	}

	public void setPtday(BigDecimal ptday) {
		this.ptday = ptday;
	}

	public BigDecimal getPthour() {
		return pthour;
	}

	public void setPthour(BigDecimal pthour) {
		this.pthour = pthour;
	}

	public BigDecimal getPtmin() {
		return ptmin;
	}

	public void setPtmin(BigDecimal ptmin) {
		this.ptmin = ptmin;
	}

	public BigDecimal getPtsec() {
		return ptsec;
	}

	public void setPtsec(BigDecimal ptsec) {
		this.ptsec = ptsec;
	}

	public String getGimage() {
		return gimage;
	}

	public void setGimage(String gimage) {
		this.gimage = gimage;
	}

	public String getRankname() {
		return rankname;
	}

	public void setRankname(String rankname) {
		this.rankname = rankname;
	}
	

}
