package project.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the REQUEST database table.
 * 
 */
@Entity
@NamedQuery(name="Request.findAll", query="SELECT r FROM Request r")
public class Request implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="REQUEST_REQID_GENERATOR", sequenceName="REQUEST_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="REQUEST_REQID_GENERATOR")
	private long reqid;

	private String reqdesc;

	private String reqdr;

	private String reqgname;

	private BigDecimal reqgrd;

	private String reqimg;

	private String reqstatus;

	//bi-directional many-to-one association to Administrator
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REQAPP", insertable=false, updatable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Administrator administrator;

	//bi-directional many-to-one association to Game
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GAME_GAMEID", insertable = false, updatable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Game game;

	//bi-directional many-to-one association to GameUser
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REQSUB")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private GameUser gameUser;
	
	//bi-directional many-to-many association to GameDeveloper
	@ManyToMany(mappedBy="requests", cascade={CascadeType.ALL})
	private List<GameDeveloper> gameDevelopers;

	public List<GameDeveloper> getGameDevelopers() {
		return gameDevelopers;
	}

	public void setGameDevelopers(List<GameDeveloper> gameDevelopers) {
		this.gameDevelopers = gameDevelopers;
	}

	public Request() {
	}

	public long getReqid() {
		return this.reqid;
	}

	public void setReqid(long reqid) {
		this.reqid = reqid;
	}

	public String getReqdesc() {
		return this.reqdesc;
	}

	public void setReqdesc(String reqdesc) {
		this.reqdesc = reqdesc;
	}

	public String getReqdr() {
		return this.reqdr;
	}

	public void setReqdr(String reqdr) {
		this.reqdr = reqdr;
	}

	public String getReqgname() {
		return this.reqgname;
	}

	public void setReqgname(String reqgname) {
		this.reqgname = reqgname;
	}

	public BigDecimal getReqgrd() {
		return this.reqgrd;
	}

	public void setReqgrd(BigDecimal reqgrd) {
		this.reqgrd = reqgrd;
	}

	public String getReqimg() {
		return this.reqimg;
	}

	public void setReqimg(String reqimg) {
		this.reqimg = reqimg;
	}

	public String getReqstatus() {
		return this.reqstatus;
	}

	public void setReqstatus(String reqstatus) {
		this.reqstatus = reqstatus;
	}

	public Administrator getAdministrator() {
		return this.administrator;
	}

	public void setAdministrator(Administrator administrator) {
		this.administrator = administrator;
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