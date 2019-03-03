package project.domain;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.sql.Date;


/**
 * The persistent class for the REVIEW database table.
 * 
 */
@Entity
@NamedQuery(name="Review.findAll", query="SELECT r FROM Review r")
public class Review implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="REVIEW_REVID_GENERATOR", sequenceName="REVIEW_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="REVIEW_REVID_GENERATOR")
	private long revid;

	@Column(name="GAME_GAMEID")
	private long gameGameid;

	@Column(name="USER_UNAME")
	private String userUname;

	private String revcont;

	@Column(name="REVDATE", insertable = false, updatable = false)
	private Date revdate;
	
	private String revtitle;

	//bi-directional many-to-one association to Game
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GAME_GAMEID", insertable = false, updatable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonIgnore
	private Game game;

	//bi-directional many-to-one association to GtUser
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_UNAME", insertable=false, updatable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private GtUser gtUser;

	public Review() {
	}

	

	public long getRevid() {
		return revid;
	}



	public void setRevid(long revid) {
		this.revid = revid;
	}



	public long getGameGameid() {
		return gameGameid;
	}



	public void setGameGameid(long gameGameid) {
		this.gameGameid = gameGameid;
	}



	public String getUserUname() {
		return userUname;
	}



	public void setUserUname(String userUname) {
		this.userUname = userUname;
	}



	public String getRevcont() {
		return this.revcont;
	}

	public void setRevcont(String revcont) {
		this.revcont = revcont;
	}

	public Date getRevdate() {
		return this.revdate;
	}

	public void setRevdate(Date revdate) {
		this.revdate = revdate;
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

	public String getRevtitle() {
		return revtitle;
	}

	public void setRevtitle(String revtitle) {
		this.revtitle = revtitle;
	}
	
	

}