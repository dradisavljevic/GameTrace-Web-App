package project.domain.dto;

import java.math.BigDecimal;

public class CompactGameDTO {
	
	private Long gameid;
	
	private String gamename;
	
	private BigDecimal gamery;

	public CompactGameDTO() {
	}
	
	

	public CompactGameDTO(String gamename, BigDecimal gamery, long gameid) {

		this.gameid = gameid;
		this.gamename = gamename;
		this.gamery = gamery;
	}



	public Long getGameid() {
		return gameid;
	}

	public void setGameid(Long gameid) {
		this.gameid = gameid;
	}

	public String getGamename() {
		return gamename;
	}

	public void setGamename(String gamename) {
		this.gamename = gamename;
	}

	public BigDecimal getGamery() {
		return gamery;
	}

	public void setGamery(BigDecimal gamery) {
		this.gamery = gamery;
	}
	
	
	

}
