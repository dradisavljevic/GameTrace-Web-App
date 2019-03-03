package project.domain.dto;

import java.math.BigDecimal;

import javax.persistence.Lob;

public class ItemDTO {
	
	private String itemcurr;

	private String itemim;

	private String itemname;

	private String itemprice;
	
	private String id;
	
	private String uname;
	
	private String gameId;
	
	private String itemq;

	public String getItemcurr() {
		return itemcurr;
	}

	public void setItemcurr(String itemcurr) {
		this.itemcurr = itemcurr;
	}

	public String getItemim() {
		return itemim;
	}

	public void setItemim(String itemim) {
		this.itemim = itemim;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public String getItemprice() {
		return itemprice;
	}

	public void setItemprice(String itemprice) {
		this.itemprice = itemprice;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getItemq() {
		return itemq;
	}

	public void setItemq(String itemq) {
		this.itemq = itemq;
	}
	
	
	
	

}
