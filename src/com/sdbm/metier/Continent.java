package com.sdbm.metier;

import java.util.ArrayList;

public class Continent {
	private Integer id;
	private String nomContinent;
	private ArrayList<Pays> paysList;
	
	public Continent() {
		id = 0;
	}
	
	public Continent(Integer id) {
		this.id = id;
	}
	
	public Continent(Integer id, String nomContinent) {
		this.id = id;
		this.nomContinent = nomContinent;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public String getnomContinent() {
		return this.nomContinent;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setNomContinent(String nomContinent) {
		this.nomContinent = nomContinent;
	}
	
	@Override
	public String toString() {
		return nomContinent;
	}

}
