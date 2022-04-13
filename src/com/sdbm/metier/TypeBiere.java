package com.sdbm.metier;

public class TypeBiere {
	private Integer id;
	private String nomBiere;
	
	public TypeBiere() {
		id = 0;
	}
	
	public TypeBiere(Integer id) {
		this.id = id;
	}
	
	public TypeBiere(Integer id, String nomBiere) {
		this.id = id;
		this.nomBiere = nomBiere;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public String getNomBiere() {
		return this.nomBiere;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setNomType(String nomBiere) {
		this.nomBiere = nomBiere;
	}
	
	@Override
	public String toString() {
		return nomBiere;
	}

}
