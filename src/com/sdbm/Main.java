package com.sdbm;

import java.util.ArrayList;

import com.sdbm.dao.DaoFactory;
import com.sdbm.metier.Continent;
import com.sdbm.metier.Couleur;
import com.sdbm.metier.Fabricant;
import com.sdbm.metier.Marque;
import com.sdbm.metier.Pays;
import com.sdbm.metier.TypeBiere;

public class Main {

	public static void main(String[] args) {

//	afficherCouleurs();
//	Couleur couleur = new Couleur();
//	couleur.setLibelle("Bleu");
//	DaoFactory.getCouleurDAO().insert(couleur);
//	afficherCouleur(couleur);
		// afficherTypes();

		// TypeBiere typeBiere1 = DaoFactory.getTypeBiereDAO().getByID(6);
		// afficherTypeBiere(typeBiere1);
//	TypeBiere typeBiere2 = new TypeBiere();
//	typeBiere2.setNomType("Biere test");
//	DaoFactory.getTypeBiereDAO().insert(typeBiere2);
//	afficherTypes();
//	System.out.println("--------------------------------");
//	DaoFactory.getTypeBiereDAO().delete(typeBiere2);
//	afficherTypes();
//
//	afficherMarques();
//	afficherFabricants();
	afficherPaysList();

	}

	// Couleurs

	private static void afficherCouleurs() {
		ArrayList<Couleur> couleurs = DaoFactory.getCouleurDAO().getAll();
		for (Couleur couleur : couleurs) {
			afficherCouleur(couleur);
		}
		System.out.println();
	}

	private static void afficherCouleur(Couleur couleur) {
		System.out.printf("Id : %d, Libellé : %s%n", couleur.getId(), couleur.getLibelle());
	}

	// TypeBiere
	private static void afficherTypes() {
		ArrayList<TypeBiere> typesBieres = DaoFactory.getTypeBiereDAO().getAll();
		for (TypeBiere typeBiere : typesBieres) {
			afficherTypeBiere(typeBiere);
		}
	}

	private static void afficherTypeBiere(TypeBiere typeBiere) {
		System.out.printf("Id: %d, Nom type: %s%n", typeBiere.getId(), typeBiere.getNomBiere());
	}

	// Continents

	private static void afficherContinent(Continent continent) {
		System.out.printf("Id: %d, Nom type: %s%n", continent.getId(), continent.getnomContinent());
	}

	private static void afficherContinents() {
		ArrayList<Continent> continents = DaoFactory.getContinentDAO().getAll();
		for (Continent continent : continents) {
			afficherContinent(continent);
		}
	}

	// Marques

	private static void afficherMarque(Marque marque) {
		System.out.printf("Id: %d, Nom type: %s%n", marque.getId(), marque.getNomMarque());
	}

	private static void afficherMarques() {
		ArrayList<Marque> marques = DaoFactory.getMarqueDAO().getAll();
		for (Marque marque : marques) {
			afficherMarque(marque);
		}
	}

	// Fabricants

	private static void afficherFabricant(Fabricant fabricant) {
		System.out.printf("Id: %d, Nom type: %s%n", fabricant.getId(), fabricant.getNomFabricant());
	}

	private static void afficherFabricants() {
		ArrayList<Fabricant> fabricants = DaoFactory.getFabricantDAO().getAll();
		for (Fabricant fabricant : fabricants) {
			afficherFabricant(fabricant);
		}
	}
	
	// Pays

		private static void afficherPays(Pays pays) {
			System.out.printf("Id: %d, Nom type: %s%n", pays.getId(), pays.getNomPays());
		}

		private static void afficherPaysList() {
			ArrayList<Pays> paysList = DaoFactory.getPaysDAO().getAll();
			for (Pays pays : paysList) {
				afficherPays(pays);
			}
		}
}
