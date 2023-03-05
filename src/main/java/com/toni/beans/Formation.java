package com.toni.beans;

public class Formation {
	private Integer formationId;
	private String formationCode;
	private String formationLabel;
	private String formationDescription;
	private Integer formationDuration;
	private String formationUrl;
	private String statusLabel;
	private String statusColor;
	private boolean showButton;
	private String buttonLabel;
	private FormationType formationType = new FormationType();
	
	public Integer getFormationId() {
		return formationId;
	}
	public void setFormationId(Integer formationId) {
		this.formationId = formationId;
	}
	public String getFormationCode() {
		return formationCode;
	}
	public void setFormationCode(String formationCode) {
		this.formationCode = formationCode;
	}
	public String getFormationLabel() {
		return formationLabel;
	}
	public void setFormationLabel(String formationLabel) {
		this.formationLabel = formationLabel;
	}
	public String getFormationDescription() {
		return formationDescription;
	}
	public void setFormationDescription(String formationDescription) {
		this.formationDescription = formationDescription;
	}
	public Integer getFormationDuration() {
		return formationDuration;
	}
	public void setFormationDuration(Integer formationDuration) {
		this.formationDuration = formationDuration;
	}
	public String getFormationUrl() {
		return formationUrl;
	}
	public void setFormationUrl(String formationUrl) {
		this.formationUrl = formationUrl;
	}
	public String getStatusLabel() {
		return statusLabel;
	}
	public void setStatusLabel(String statusLabel) {
		this.statusLabel = statusLabel;
	}
	public String getStatusColor() {
		return statusColor;
	}
	public void setStatusColor(String statusColor) {
		this.statusColor = statusColor;
	}
	public boolean isShowButton() {
		return showButton;
	}
	public void setShowButton(boolean showButton) {
		this.showButton = showButton;
	}
	public String getButtonLabel() {
		return buttonLabel;
	}
	public void setButtonLabel(String buttonLabel) {
		this.buttonLabel = buttonLabel;
	}
	public FormationType getFormationType() {
		return formationType;
	}
	public void setFormationType(FormationType formationType) {
		this.formationType = formationType;
	}
}
