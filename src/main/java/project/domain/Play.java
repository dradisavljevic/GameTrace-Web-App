package project.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the PLAYS database table.
 * 
 */

@Entity
@Table(name="PLAYS")
@NamedQuery(name="Play.findAll", query="SELECT p FROM Play p")
public class Play implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PlayPK id;

	private BigDecimal ptday;

	private BigDecimal pthour;

	private BigDecimal ptmin;

	private BigDecimal ptsec;

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

	public Play() {
	}

	public PlayPK getId() {
		return this.id;
	}

	public void setId(PlayPK id) {
		this.id = id;
	}

	public BigDecimal getPtday() {
		return this.ptday;
	}

	public void setPtday(BigDecimal ptday) {
		this.ptday = ptday;
	}

	public BigDecimal getPthour() {
		return this.pthour;
	}

	public void setPthour(BigDecimal pthour) {
		this.pthour = pthour;
	}

	public BigDecimal getPtmin() {
		return this.ptmin;
	}

	public void setPtmin(BigDecimal ptmin) {
		this.ptmin = ptmin;
	}

	public BigDecimal getPtsec() {
		return this.ptsec;
	}

	public void setPtsec(BigDecimal ptsec) {
		this.ptsec = ptsec;
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