package com.toni.beans;

import java.util.ArrayList;
import java.util.List;

public class FormationType {
	private Integer formationTypeId;
	private String formationTypeCode;
	private String formationTypeLabel;
	private List<Formation> formations = new ArrayList<Formation>(); 
	
	public Integer getFormationTypeId() {
		return formationTypeId;
	}
	public void setFormationTypeId(Integer formationTypeId) {
		this.formationTypeId = formationTypeId;
	}
	public String getFormationTypeCode() {
		return formationTypeCode;
	}
	public void setFormationTypeCode(String formationTypeCode) {
		this.formationTypeCode = formationTypeCode;
	}
	public String getFormationTypeLabel() {
		return formationTypeLabel;
	}
	public void setFormationTypeLabel(String formationTypeLabel) {
		this.formationTypeLabel = formationTypeLabel;
	}
	public List<Formation> getFormations() {
		return formations;
	}
	public void setFormations(List<Formation> formations) {
		this.formations = formations;
	}
}
