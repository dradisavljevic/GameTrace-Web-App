package project.domain;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the RANK database table.
 * 
 */
@Entity
@NamedQuery(name="Rank.findAll", query="SELECT r FROM Rank r")
public class Rank implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RANK_RANKID_GENERATOR", sequenceName="RANK_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RANK_RANKID_GENERATOR")
	private long rankid;

	private BigDecimal rankh;

	private String rankname;

	//bi-directional many-to-one association to Ranks
	@OneToMany(mappedBy="rank")
	@JsonIgnore
	private List<Ranks> ranks;

	public Rank() {
	}

	public long getRankid() {
		return this.rankid;
	}

	public void setRankid(long rankid) {
		this.rankid = rankid;
	}

	public BigDecimal getRankh() {
		return this.rankh;
	}

	public void setRankh(BigDecimal rankh) {
		this.rankh = rankh;
	}

	public String getRankname() {
		return this.rankname;
	}

	public void setRankname(String rankname) {
		this.rankname = rankname;
	}

	public List<Ranks> getRanks() {
		return this.ranks;
	}

	public void setRanks(List<Ranks> ranks) {
		this.ranks = ranks;
	}

	public Ranks addRank(Ranks rank) {
		getRanks().add(rank);
		rank.setRank(this);

		return rank;
	}

	public Ranks removeRank(Ranks rank) {
		getRanks().remove(rank);
		rank.setRank(null);

		return rank;
	}

}