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
	    name = "get_friends_in_game",
	    query = "{ ? = call eventpkg.GET_FRIENDS_IN_GAME( ? ) }",
	    callable = true,
	    resultSetMapping = "friends_in_game"
	)
	@SqlResultSetMapping(
	    name = "friends_in_game",
	    entities = {
	        @EntityResult(
	            entityClass = FriendsInGame.class,
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
		                    name = "uname", 
		                    column = "uname"
		                ),
	                @FieldResult( 
		                    name = "uavat", 
		                    column = "uavat"
		                )
	            }
	        )
	    }
	)
@Entity
@Table(name="FRIENDS_IN_GAME")
@NamedQuery(name="FriendsInGame.findAll", query="SELECT f FROM FriendsInGame f")
public class FriendsInGame {
	
	private BigDecimal gameid;

	private String gamename;

	private BigDecimal gamery;
	
	@Id
	private String uname;

	private String uavat;

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

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUavat() {
		return uavat;
	}

	public void setUavat(String uavat) {
		this.uavat = uavat;
	}
	
	

}
