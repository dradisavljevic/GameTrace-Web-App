package project.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import org.hibernate.annotations.NamedNativeQuery;



@NamedNativeQuery(
	    name = "get_top_players",
	    query = "{ ? = call eventpkg.get_top_players( ? ) }",
	    callable = true,
	    resultSetMapping = "top_players"
	)
	@SqlResultSetMapping(
	    name = "top_players",
	    entities = {
	        @EntityResult(
	            entityClass = Player.class,
	            fields = {
	                @FieldResult( 
	                    name = "username", 
	                    column = "username"
	                ),
	                @FieldResult( 
		                    name = "game", 
		                    column = "game"
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
		                    name = "avatar", 
		                    column = "avatar"
		                )
	            }
	        )
	    }
	)
@Entity
@Table(name="PLAYER")
@NamedQuery(name="Player.findAll", query="SELECT p FROM Player p")
public class Player {
	
	@Id
	private String username;
	
	private BigDecimal game;
	
	private BigDecimal ptday;

	private BigDecimal pthour;

	private BigDecimal ptmin;

	private BigDecimal ptsec;
	
	
	private String avatar;
	
	public Player() {
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public BigDecimal getGame() {
		return game;
	}

	public void setGame(BigDecimal game) {
		this.game = game;
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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	

}
