package project.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the GAME_DEVELOPER database table.
 * 
 */
@Entity
@Table(name="GAME_DEVELOPER")
@NamedQuery(name="GameDeveloper.findAll", query="SELECT g FROM GameDeveloper g")
public class GameDeveloper implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GAME_DEVELOPER_GDID_GENERATOR", sequenceName="DEVELOPER_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GAME_DEVELOPER_GDID_GENERATOR")
	private long gdid;

	private BigDecimal gdcount;

	private String gdname;

	//bi-directional many-to-many association to Game
	@ManyToMany
	@JoinTable(
		name="DEVELOPS"
		, joinColumns={
			@JoinColumn(name="GAME_DEVELOPER_GDID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="GAME_GAMEID")
			}
		)
	@JsonIgnore
	private List<Game> games;
	
	@ManyToMany
	@JoinTable(
		name="DEVELOPS_REQUEST"
		, joinColumns={
			@JoinColumn(name="GAME_DEVELOPER_GDID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="REQUEST_REQID")
			}
		)
	@JsonIgnore
	private List<Request> requests;

	public GameDeveloper() {
	}

	public long getGdid() {
		return this.gdid;
	}

	public void setGdid(long gdid) {
		this.gdid = gdid;
	}

	public BigDecimal getGdcount() {
		return this.gdcount;
	}

	public void setGdcount(BigDecimal gdcount) {
		this.gdcount = gdcount;
	}

	public String getGdname() {
		return this.gdname;
	}

	public void setGdname(String gdname) {
		this.gdname = gdname;
	}

	public List<Game> getGames() {
		return this.games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

	public List<Request> getRequests() {
		return requests;
	}

	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}

}