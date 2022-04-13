package com.sdbm.metier;


public class Couleur {
    private Integer id;
    private String libelle;

    public Couleur() {
        id = 0;
    }

    public Couleur(Integer id) {
        this.id = id;
    }

    public Couleur(Integer id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	@Override
    public String toString() {
        return libelle;
    }

}
