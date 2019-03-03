package project.domain;

import java.io.Serializable;
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

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the STREAMER database table.
 * 
 */
@Entity
@NamedQuery(name="Streamer.findAll", query="SELECT s FROM Streamer s")
public class Streamer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="STREAMER_STRID_GENERATOR", sequenceName="STREAM_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="STREAMER_STRID_GENERATOR")
	private long strid;

	private String strname;


	private String strurl;

	//bi-directional many-to-many association to Game
	@ManyToMany
	@JoinTable(
		name="STREAMS"
		, joinColumns={
			@JoinColumn(name="STREAMER_STRID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="GAME_GAMEID")
			}
		)
	@JsonIgnore
	private List<Game> games;

	public Streamer() {
	}

	public long getStrid() {
		return this.strid;
	}

	public void setStrid(long strid) {
		this.strid = strid;
	}

	public String getStrname() {
		return this.strname;
	}

	public void setStrname(String strname) {
		this.strname = strname;
	}


	public String getStrurl() {
		return this.strurl;
	}

	public void setStrurl(String strurl) {
		this.strurl = strurl;
	}

	public List<Game> getGames() {
		return this.games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

}