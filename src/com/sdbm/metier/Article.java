package com.sdbm.metier;

public class Article {
	int id;
	String nomArticle;
	int volume;
	float titrage;
	float prixAchat;
	Marque marque;
	Couleur couleur;
	TypeBiere typeBiere;
	
	public Article() {
		
	}
	
	public Article(Integer id, String nomArticle) {
		this.id  = id;
		this.nomArticle = nomArticle;
		marque = new Marque();
		couleur = new Couleur();
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public String getNomArticle() {
		return this.nomArticle;
	}
	
	public int getVolume() {
		return this.volume;
	}
	
	public float getTitrage() {
		return this.titrage;
	}
	
	public float getPrixAchat() {
		return this.prixAchat;
	}
	
	public Marque getMarque() {
		return this.marque;
	}
	
	public Couleur getCouleur() {
		return this.couleur;
	}
	
	public TypeBiere getNomTypeBiere() {
		return this.typeBiere;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}
	
	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	public void setTitrage(float titrage) {
		this.titrage = titrage;
	}
	
	public void setMarque(Marque marque) {
		this.marque = marque;
	}
	
	public void setCouleur(Couleur couleur) {
		this.couleur = couleur;
	}
	
	public void setTypeBiere(TypeBiere typeBiere) {
		this.typeBiere = typeBiere;
	}
	
	public void setPrixAchat(float prixAchat) {
		this.prixAchat = prixAchat;
	}
	
	@Override
	public String toString() {
		return nomArticle;
	}
}
