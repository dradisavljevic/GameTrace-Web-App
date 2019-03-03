package project.domain.dto;

import java.util.ArrayList;

public class StreamerDTO {
	
	private String strname;


	private String strurl;

	
	ArrayList<String> games;
	
	String strid;


	protected StreamerDTO() {
	}


	public String getStrname() {
		return strname;
	}


	public void setStrname(String strname) {
		this.strname = strname;
	}


	public String getStrurl() {
		return strurl;
	}


	public void setStrurl(String strurl) {
		this.strurl = strurl;
	}


	public ArrayList<String> getGames() {
		return games;
	}


	public void setGames(ArrayList<String> games) {
		this.games = games;
	}


	public String getStrid() {
		return strid;
	}


	public void setStrid(String strid) {
		this.strid = strid;
	}
	
	
	
	
}
