package project.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ITEM database table.
 * 
 */
@Embeddable
public class ItemPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long itemid;

	@Column(name="GAME_USER_UNAME", insertable=false, updatable=false)
	private String gameUserUname;

	public ItemPK() {
	}
	public long getItemid() {
		return this.itemid;
	}
	public void setItemid(long itemid) {
		this.itemid = itemid;
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
		if (!(other instanceof ItemPK)) {
			return false;
		}
		ItemPK castOther = (ItemPK)other;
		return 
			(this.itemid == castOther.itemid)
			&& this.gameUserUname.equals(castOther.gameUserUname);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.itemid ^ (this.itemid >>> 32)));
		hash = hash * prime + this.gameUserUname.hashCode();
		
		return hash;
	}
}