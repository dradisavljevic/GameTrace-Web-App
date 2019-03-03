package project.domain;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the RANKS database table.
 * 
 */
@Entity
@NamedQuery(name="Ranks.findAll", query="SELECT r FROM Ranks r")
public class Ranks implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RanksPK id;


	private Date rankdate;

	private Timestamp ranktime;

	//bi-directional many-to-one association to Game
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GAME_GAMEID", insertable = false, updatable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Game game;

	//bi-directional many-to-one association to GtUser
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GAME_USER_UNAME", insertable=false, updatable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private GtUser gtUser;

	//bi-directional many-to-one association to Rank
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="RANK_RANKID", insertable = false, updatable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Rank rank;

	public Ranks() {
	}

	public RanksPK getId() {
		return this.id;
	}

	public void setId(RanksPK id) {
		this.id = id;
	}

	public Date getRankdate() {
		return this.rankdate;
	}

	public void setRankdate(Date rankdate) {
		this.rankdate = rankdate;
	}

	public Timestamp getRanktime() {
		return this.ranktime;
	}

	public void setRanktime(Timestamp ranktime) {
		this.ranktime = ranktime;
	}

	public Game getGame() {
		return this.game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public GtUser getGtUser() {
		return this.gtUser;
	}

	public void setGtUser(GtUser gtUser) {
		this.gtUser = gtUser;
	}

	public Rank getRank() {
		return this.rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

}