package com.sdbm.metier;

import java.util.ArrayList;

public class Fabricant {
	private Integer id;
	private String nomFabricant;
	private ArrayList<Marque> listMarques;
	
	public Fabricant() {
		id = 0;
	}
	
	public Fabricant(Integer id) {
		this.id = id;
	}
	
	public Fabricant(Integer id, String nomFabricant) {
		this.id = id;
		this.nomFabricant = nomFabricant;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public String getNomFabricant() {
		return this.nomFabricant;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setNomFabricant(String nomFabricant) {
		this.nomFabricant = nomFabricant;
	}
	
	@Override
	public String toString() {
		return nomFabricant;
	}

}
