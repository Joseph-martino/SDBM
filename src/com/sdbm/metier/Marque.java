package com.sdbm.metier;

import java.util.ArrayList;

public class Marque {
	private Integer id;
	private String nomMarque;
	private Fabricant fabricant;
	private Pays pays;
	private ArrayList<Article> articles;
	
	public Marque() {
		id = 0;
		pays = new Pays();
	}
	
	public Marque(Integer id) {
		this.id = id;
	}
	
	public Marque(Integer id, String nomMarque) {
		this.id = id;
		this.nomMarque = nomMarque;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public String getNomMarque() {
		return this.nomMarque;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setNomMarque(String nomMarque) {
		this.nomMarque = nomMarque;
	}
	
	@Override
	public String toString() {
		return nomMarque;
	}
	

}
