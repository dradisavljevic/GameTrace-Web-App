package project.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the GUIDE database table.
 * 
 */
@Entity
@NamedQuery(name="Guide.findAll", query="SELECT g FROM Guide g")
public class Guide implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GUIDE_GUID_GENERATOR", sequenceName="GUIDE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GUIDE_GUID_GENERATOR")
	private long guid;

	@Column(name="GAME_GAMEID")
	private long gameGameid;

	@Column(name="GAME_USER_UNAME")
	private String gameUserUname;

	private String gucont;

	private String guname;

	//bi-directional many-to-one association to Game
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GAME_GAMEID", insertable = false, updatable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Game game;

	//bi-directional many-to-one association to GameUser
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GAME_USER_UNAME", insertable=false, updatable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private GameUser gameUser;

	public Guide() {
	}

	
	public long getGuid() {
		return guid;
	}


	public void setGuid(long guid) {
		this.guid = guid;
	}


	public long getGameGameid() {
		return gameGameid;
	}


	public void setGameGameid(long gameGameid) {
		this.gameGameid = gameGameid;
	}


	public String getGameUserUname() {
		return gameUserUname;
	}


	public void setGameUserUname(String gameUserUname) {
		this.gameUserUname = gameUserUname;
	}


	public String getGucont() {
		return this.gucont;
	}

	public void setGucont(String gucont) {
		this.gucont = gucont;
	}

	public String getGuname() {
		return this.guname;
	}

	public void setGuname(String guname) {
		this.guname = guname;
	}

	public Game getGame() {
		return this.game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public GameUser getGameUser() {
		return this.gameUser;
	}

	public void setGameUser(GameUser gameUser) {
		this.gameUser = gameUser;
	}

}