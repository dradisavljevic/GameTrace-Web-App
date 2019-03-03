package project.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import project.domain.GameUser;

@Entity
@Table(name="GROUP_INVITE")
@NamedQuery(name="GroupInvite.findAll", query="SELECT g FROM GroupInvite g")
public class GroupInvite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GROUP_INVITE_GIID_GENERATOR", sequenceName="GRINVITE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GROUP_INVITE_GIID_GENERATOR")
	private long giid;

	@Column(name="GROUP_ID")
	private long groupId;
	
	@Column(name="GROUP_MASTER")
	private String groupMaster;
	
	@Column(name="GIREC")
	private String girec;

	@Column(name = "gidate", insertable=false)
	private Date gidate;

	private String gistat;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="GROUP_ID", referencedColumnName="GRID", insertable=false, updatable=false),
		@JoinColumn(name="GROUP_MASTER", referencedColumnName="GRGM", insertable=false, updatable=false)
		})
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private GameGroup gameGroup;

	//bi-directional many-to-one association to GameUser
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GIREC", insertable=false, updatable=false)
	@JsonIgnore
	private GameUser gameUser;

	public GroupInvite() {
	}

	public Date getGidate() {
		return this.gidate;
	}

	public void setGidate(Date gidate) {
		this.gidate = gidate;
	}

	public String getGistat() {
		return this.gistat;
	}

	public void setGistat(String gistat) {
		this.gistat = gistat;
	}

	public GameGroup getGameGroup() {
		return this.gameGroup;
	}

	public void setGameGroup(GameGroup gameGroup) {
		this.gameGroup = gameGroup;
	}

	public GameUser getGameUser() {
		return this.gameUser;
	}

	public void setGameUser(GameUser gameUser) {
		this.gameUser = gameUser;
	}

	public long getGiid() {
		return giid;
	}

	public void setGiid(long giid) {
		this.giid = giid;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getGirec() {
		return girec;
	}

	public void setGirec(String girec) {
		this.girec = girec;
	}

	public String getGroupMaster() {
		return groupMaster;
	}

	public void setGroupMaster(String groupMaster) {
		this.groupMaster = groupMaster;
	}
	
	


}
