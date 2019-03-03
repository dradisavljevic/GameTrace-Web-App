package project.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the ITEM database table.
 * 
 */
@Entity
@NamedQuery(name="Item.findAll", query="SELECT i FROM Item i")
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ITEM_ITEMID_GENERATOR", sequenceName="ITEM_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ITEM_ITEMID_GENERATOR")
	private long itemid;

	private String itemcurr;

	private String itemim;

	private String itemname;

	private BigDecimal itemprice;
	

	@Column(name="GAME_USER_UNAME")
	private String gameUserUname;
	
	@Column(name="GAME_GAMEID")
	private long gameGameid;
	
	private long itemq;

	private String itemstat;
	
	@Column(name="ITEMBUY")
	private String itembuy;

	//bi-directional many-to-one association to GameUser
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GAME_USER_UNAME", insertable=false, updatable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private GameUser gameUser;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GAME_GAMEID", insertable=false, updatable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Game game;
	
	//bi-directional many-to-one association to GameUser
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ITEMBUY", insertable=false, updatable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private GameUser gameUser2;

	public Item() {
	}

	public long getItemid() {
		return itemid;
	}

	public void setItemid(long itemid) {
		this.itemid = itemid;
	}

	public String getGameUserUname() {
		return gameUserUname;
	}

	public void setGameUserUname(String gameUserUname) {
		this.gameUserUname = gameUserUname;
	}
	

	public String getItemcurr() {
		return this.itemcurr;
	}

	public void setItemcurr(String itemcurr) {
		this.itemcurr = itemcurr;
	}

	public String getItemim() {
		return this.itemim;
	}

	public void setItemim(String itemim) {
		this.itemim = itemim;
	}

	public String getItemname() {
		return this.itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public BigDecimal getItemprice() {
		return this.itemprice;
	}

	public void setItemprice(BigDecimal itemprice) {
		this.itemprice = itemprice;
	}

	public GameUser getGameUser() {
		return this.gameUser;
	}

	public void setGameUser(GameUser gameUser) {
		this.gameUser = gameUser;
	}

	public long getGameGameid() {
		return gameGameid;
	}

	public void setGameGameid(long gameGameid) {
		this.gameGameid = gameGameid;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public long getItemq() {
		return itemq;
	}

	public void setItemq(long itemq) {
		this.itemq = itemq;
	}

	public String getItemstat() {
		return itemstat;
	}

	public void setItemstat(String itemstat) {
		this.itemstat = itemstat;
	}

	public String getItembuy() {
		return itembuy;
	}

	public void setItembuy(String itembuy) {
		this.itembuy = itembuy;
	}

	public GameUser getGameUser2() {
		return gameUser2;
	}

	public void setGameUser2(GameUser gameUser2) {
		this.gameUser2 = gameUser2;
	}
	
	
	

}