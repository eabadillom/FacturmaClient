package com.ferbo.facturama.response;

import com.google.gson.annotations.SerializedName;

public class SerieViewModel {
	
	@SerializedName(value = "Folio")
	String folio = null;
	
	@SerializedName(value = "Name")
	String name = null;
	
	@SerializedName(value = "Description")
	String description = null;

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "{\"folio\":\"" + folio + "\", \"name\":\"" + name + "\", \"description\":\"" + description + "\"}";
	}
	
}
