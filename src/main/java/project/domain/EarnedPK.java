package project.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the EARNED database table.
 * 
 */
@Embeddable
public class EarnedPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ACHIEVEMENT_ACHID", insertable=false, updatable=false)
	private long achievementAchid;

	@Column(name="GAME_USER_UNAME", insertable=false, updatable=false)
	private String gameUserUname;

	public EarnedPK() {
	}
	public long getAchievementAchid() {
		return this.achievementAchid;
	}
	public void setAchievementAchid(long achievementAchid) {
		this.achievementAchid = achievementAchid;
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
		if (!(other instanceof EarnedPK)) {
			return false;
		}
		EarnedPK castOther = (EarnedPK)other;
		return 
			(this.achievementAchid == castOther.achievementAchid)
			&& this.gameUserUname.equals(castOther.gameUserUname);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.achievementAchid ^ (this.achievementAchid >>> 32)));
		hash = hash * prime + this.gameUserUname.hashCode();
		
		return hash;
	}
}