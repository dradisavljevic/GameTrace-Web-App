package project.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the RANKS database table.
 * 
 */
@Embeddable
public class RanksPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="RANK_RANKID", insertable=false, updatable=false)
	private long rankRankid;

	@Column(name="GAME_GAMEID", insertable=false, updatable=false)
	private long gameGameid;

	@Column(name="GAME_USER_UNAME", insertable=false, updatable=false)
	private String gameUserUname;

	public RanksPK() {
	}
	public long getRankRankid() {
		return this.rankRankid;
	}
	public void setRankRankid(long rankRankid) {
		this.rankRankid = rankRankid;
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
		if (!(other instanceof RanksPK)) {
			return false;
		}
		RanksPK castOther = (RanksPK)other;
		return 
			(this.rankRankid == castOther.rankRankid)
			&& (this.gameGameid == castOther.gameGameid)
			&& this.gameUserUname.equals(castOther.gameUserUname);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.rankRankid ^ (this.rankRankid >>> 32)));
		hash = hash * prime + ((int) (this.gameGameid ^ (this.gameGameid >>> 32)));
		hash = hash * prime + this.gameUserUname.hashCode();
		
		return hash;
	}
}