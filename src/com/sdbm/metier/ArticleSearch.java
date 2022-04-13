package com.sdbm.metier;

public class ArticleSearch {
	private String nom;
	private int volume;
	private float titrageMin;
	private float titrageMax;
	private int marque;
	private int fabricant;
	private int pays;
	private int continent;
	private int couleur;
	private int type;
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getCouleur() {
		return couleur;
	}
	public void setCouleur(int couleur) {
		this.couleur = couleur;
	}
	public int getContinent() {
		return continent;
	}
	public void setContinent(int continent) {
		this.continent = continent;
	}
	public int getPays() {
		return pays;
	}
	public void setPays(int pays) {
		this.pays = pays;
	}
	public int getFabricant() {
		return fabricant;
	}
	public void setFabricant(int fabricant) {
		this.fabricant = fabricant;
	}
	public int getMarque() {
		return marque;
	}
	public void setMarque(int marque) {
		this.marque = marque;
	}
	public float getTitrageMax() {
		return titrageMax;
	}
	public void setTitrageMax(float titrageMax) {
		this.titrageMax = titrageMax;
	}
	public float getTitrageMin() {
		return titrageMin;
	}
	public void setTitrageMin(float titrageMin) {
		this.titrageMin = titrageMin;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	

}
