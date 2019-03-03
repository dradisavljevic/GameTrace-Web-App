package project.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the GAME_GROUP database table.
 * 
 */
@Entity
@Table(name="GAME_GROUP")
@NamedQuery(name="GameGroup.findAll", query="SELECT g FROM GameGroup g")

public class GameGroup implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="GAMEGROUP_GRID_GENERATOR", sequenceName="GROUP_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GAMEGROUP_GRID_GENERATOR")
	private long grid;
	
	private String grname;


	@Column(name="GRGM")
	private String grgm;

	//bi-directional many-to-one association to GtUser
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GRGM", insertable=false, updatable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonIgnore
	private GtUser gtUser;

	//bi-directional many-to-many association to GameUser
	@ManyToMany(mappedBy="gameGroups")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private List<GameUser> gameUsers;
	
	//bi-directional many-to-one association to FriendRequest
	@OneToMany(mappedBy="gameGroup")
	@JsonIgnore
	private List<GroupInvite> groupInvites;

	public GameGroup() {
	}

	

	public long getGrid() {
		return grid;
	}



	public void setGrid(long grid) {
		this.grid = grid;
	}



	public String getGrgm() {
		return grgm;
	}



	public void setGrgm(String grgm) {
		this.grgm = grgm;
	}



	public String getGrname() {
		return this.grname;
	}

	public void setGrname(String grname) {
		this.grname = grname;
	}

	public GtUser getGtUser() {
		return this.gtUser;
	}

	public void setGtUser(GtUser gtUser) {
		this.gtUser = gtUser;
	}

	public List<GameUser> getGameUsers() {
		return this.gameUsers;
	}

	public void setGameUsers(List<GameUser> gameUsers) {
		this.gameUsers = gameUsers;
	}



	public List<GroupInvite> getGroupInvites() {
		return groupInvites;
	}



	public void setGroupInvites(List<GroupInvite> groupInvites) {
		this.groupInvites = groupInvites;
	}
	
	

}