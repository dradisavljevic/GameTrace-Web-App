package project.domain;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the EARNED database table.
 * 
 */
@Entity
@NamedQuery(name="Earned.findAll", query="SELECT e FROM Earned e")
public class Earned implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EarnedPK id;


	private Date earndate;

	private Timestamp earntime;

	//bi-directional many-to-one association to Achievement
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ACHIEVEMENT_ACHID", insertable = false, updatable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Achievement achievement;

	//bi-directional many-to-one association to GameUser
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GAME_USER_UNAME", insertable=false, updatable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private GameUser gameUser;

	public Earned() {
	}

	public EarnedPK getId() {
		return this.id;
	}

	public void setId(EarnedPK id) {
		this.id = id;
	}

	public Date getEarndate() {
		return this.earndate;
	}

	public void setEarndate(Date earndate) {
		this.earndate = earndate;
	}

	public Timestamp getEarntime() {
		return this.earntime;
	}

	public void setEarntime(Timestamp earntime) {
		this.earntime = earntime;
	}

	public Achievement getAchievement() {
		return this.achievement;
	}

	public void setAchievement(Achievement achievement) {
		this.achievement = achievement;
	}

	public GameUser getGameUser() {
		return this.gameUser;
	}

	public void setGameUser(GameUser gameUser) {
		this.gameUser = gameUser;
	}

}