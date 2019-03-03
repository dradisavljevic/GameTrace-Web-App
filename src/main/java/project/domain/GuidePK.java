package project.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the GUIDE database table.
 * 
 */
@Embeddable
public class GuidePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	
	private long guid;

	@Column(name="GAME_GAMEID", insertable=false, updatable=false)
	private long gameGameid;

	@Column(name="GAME_USER_UNAME", insertable=false, updatable=false)
	private String gameUserUname;

	public GuidePK() {
	}
	public long getGuid() {
		return this.guid;
	}
	public void setGuid(long guid) {
		this.guid = guid;
	}
	public long getGameGameid() {
		return this.gameGameid;
	}
	public void setGameGameid(long gameGameid) {
		this.gameGameid = gameGameid;
	}
	public String getGameUserUname() {
		return this.gameUserUname;
	}
	public void setGameUserUname(String gameUserUname) {
		this.gameUserUname = gameUserUname;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GuidePK)) {
			return false;
		}
		GuidePK castOther = (GuidePK)other;
		return 
			(this.guid == castOther.guid)
			&& (this.gameGameid == castOther.gameGameid)
			&& this.gameUserUname.equals(castOther.gameUserUname);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.guid ^ (this.guid >>> 32)));
		hash = hash * prime + ((int) (this.gameGameid ^ (this.gameGameid >>> 32)));
		hash = hash * prime + this.gameUserUname.hashCode();
		
		return hash;
	}
}