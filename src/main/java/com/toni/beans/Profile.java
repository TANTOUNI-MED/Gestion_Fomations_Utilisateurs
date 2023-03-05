package com.toni.beans;

public class Profile {
	private Integer profileId;
	private String profileCode;
	private String profileLabel;
	private Typology typology = new Typology();
	
	public Integer getProfileId() {
		return profileId;
	}
	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}
	public String getProfileCode() {
		return profileCode;
	}
	public void setProfileCode(String profileCode) {
		this.profileCode = profileCode;
	}
	public String getProfileLabel() {
		return profileLabel;
	}
	public void setProfileLabel(String profileLabel) {
		this.profileLabel = profileLabel;
	}
	public Typology getTypology() {
		return typology;
	}
	public void setTypology(Typology typology) {
		this.typology = typology;
	}
}
