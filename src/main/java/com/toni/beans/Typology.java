package com.toni.beans;

import java.util.List;

public class Typology {
	private Integer typologyId;
	private String typologyCode;
	private String typologyLabel;
	private List<Profile> profiles;
	
	public Integer getTypologyId() {
		return typologyId;
	}
	public String getTypologyCode() {
		return typologyCode;
	}
	public void setTypologyCode(String typologyCode) {
		this.typologyCode = typologyCode;
	}
	public void setTypologyId(Integer typologyId) {
		this.typologyId = typologyId;
	}
	public String getTypologyLabel() {
		return typologyLabel;
	}
	public void setTypologyLabel(String typologyLabel) {
		this.typologyLabel = typologyLabel;
	}
	public List<Profile> getProfiles() {
		return profiles;
	}
	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}
}
