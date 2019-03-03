package project.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the REVIEW database table.
 * 
 */
@Embeddable
public class ReviewPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;


	private long revid;

	@Column(name="GAME_GAMEID", insertable=false, updatable=false)
	private long gameGameid;

	@Column(name="USER_UNAME", insertable=false, updatable=false)
	private String userUname;

	public ReviewPK() {
	}
	public long getRevid() {
		return this.revid;
	}
	public void setRevid(long revid) {
		this.revid = revid;
	}
	public long getGameGameid() {
		return this.gameGameid;
	}
	public void setGameGameid(long gameGameid) {
		this.gameGameid = gameGameid;
	}
	public String getUserUname() {
		return this.userUname;
	}
	public void setUserUname(String userUname) {
		this.userUname = userUname;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ReviewPK)) {
			return false;
		}
		ReviewPK castOther = (ReviewPK)other;
		return 
			(this.revid == castOther.revid)
			&& (this.gameGameid == castOther.gameGameid)
			&& this.userUname.equals(castOther.userUname);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.revid ^ (this.revid >>> 32)));
		hash = hash * prime + ((int) (this.gameGameid ^ (this.gameGameid >>> 32)));
		hash = hash * prime + this.userUname.hashCode();
		
		return hash;
	}
}