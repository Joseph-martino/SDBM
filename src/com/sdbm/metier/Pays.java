package com.sdbm.metier;

public class Pays {
	private Integer id;
	private String nomPays;
	private Continent continent;
	
	public Pays() {
		id = 0;
	}
	
	public Pays(Integer id) {
		this.id = id;
	}
	
	public Pays(Integer id, String nomPays) {
		this.id = id;
		this.nomPays = nomPays;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public String getNomPays() {
		return this.nomPays;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setNomPays(String nomPays) {
		this.nomPays = nomPays;
	}
	
	@Override
	public String toString() {
		return nomPays;
	}

}
