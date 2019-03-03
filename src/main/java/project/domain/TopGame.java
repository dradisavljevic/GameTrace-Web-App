package project.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;

import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

@NamedNativeQueries({
@NamedNativeQuery(
	    name = "get_top_games",
	    query = "{ ? = call eventpkg.get_top_games( ? ) }",
	    callable = true,
	    resultSetMapping = "top_games"
	)
})

	@SqlResultSetMapping(
	    name = "top_games",
	    entities = {
	        @EntityResult(
	            entityClass = TopGame.class,
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
		                )
	            }
	        )
	    }
	)
@Entity
@Table(name="TOP_GAME")
@NamedQuery(name="TopGame.findAll", query="SELECT t FROM TopGame t")
public class TopGame {
	
	
	
	@Id
	private BigDecimal gameid;
	
	private String gamename;
	
	private BigDecimal gamery;
	
	private BigDecimal ptday;

	private BigDecimal pthour;

	private BigDecimal ptmin;

	private BigDecimal ptsec;
	
	
	private String gimage;
	
	public TopGame() {
		
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
	
	

}
