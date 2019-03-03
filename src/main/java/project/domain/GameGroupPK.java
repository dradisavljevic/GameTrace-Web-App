package project.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the GAME_GROUP database table.
 * 
 */

public class GameGroupPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long grid;
	


	@Column(name="GRGM")
	private String grgm;
	

	public GameGroupPK() {
	}
	public long getGrid() {
		return this.grid;
	}
	public void setGrid(long grid) {
		this.grid = grid;
	}
	public String getGrgm() {
		return this.grgm;
	}
	public void setGrgm(String grgm) {
		this.grgm = grgm;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GameGroupPK)) {
			return false;
		}
		GameGroupPK castOther = (GameGroupPK)other;
		return 
			(this.grid == castOther.grid)
			&& this.grgm.equals(castOther.grgm);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.grid ^ (this.grid >>> 32)));
		hash = hash * prime + this.grgm.hashCode();
		
		return hash;
	}
}