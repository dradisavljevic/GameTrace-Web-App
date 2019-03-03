package project.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the GENRE database table.
 * 
 */
@Entity
@NamedQuery(name="Genre.findAll", query="SELECT g FROM Genre g")
public class Genre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GENRE_GNRID_GENERATOR", sequenceName="GENRE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GENRE_GNRID_GENERATOR")
	private long gnrid;

	private String gnrname;

	//bi-directional many-to-many association to Game
	@ManyToMany(mappedBy="genres")
	@JsonIgnore
	private List<Game> games;

	public Genre() {
	}

	public long getGnrid() {
		return this.gnrid;
	}

	public void setGnrid(long gnrid) {
		this.gnrid = gnrid;
	}

	public String getGnrname() {
		return this.gnrname;
	}

	public void setGnrname(String gnrname) {
		this.gnrname = gnrname;
	}

	public List<Game> getGames() {
		return this.games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

}