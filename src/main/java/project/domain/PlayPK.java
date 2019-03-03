package project.domain;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.*;

/**
 * The primary key class for the PLAYS database table.
 * 
 */
@Embeddable
public class PlayPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="GAME_USER_UNAME", insertable=false, updatable=false)
	private String gameUserUname;

	@Column(name="GAME_GAMEID", insertable=false, updatable=false)
	private long gameGameid;


	private Date playdate;


	private Timestamp ptime;

	public PlayPK() {
	}
	public String getGameUserUname() {
		return this.gameUserUname;
	}
	public void setGameUserUname(String gameUserUname) {
		this.gameUserUname = gameUserUname;
	}
	public long getGameGameid() {
		return this.gameGameid;
	}
	public void setGameGameid(long gameGameid) {
		this.gameGameid = gameGameid;
	}
	public Date getPlaydate() {
		return this.playdate;
	}
	public void setPlaydate(Date playdate) {
		this.playdate = playdate;
	}
	public Timestamp getPtime() {
		return this.ptime;
	}
	public void setPtime(Timestamp ptime) {
		this.ptime = ptime;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PlayPK)) {
			return false;
		}
		PlayPK castOther = (PlayPK)other;
		return 
			this.gameUserUname.equals(castOther.gameUserUname)
			&& (this.gameGameid == castOther.gameGameid)
			&& this.playdate.equals(castOther.playdate)
			&& this.ptime.equals(castOther.ptime);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.gameUserUname.hashCode();
		hash = hash * prime + ((int) (this.gameGameid ^ (this.gameGameid >>> 32)));
		hash = hash * prime + this.playdate.hashCode();
		hash = hash * prime + this.ptime.hashCode();
		
		return hash;
	}
}