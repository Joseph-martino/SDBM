package com.sdbm.dao;

import java.sql.Connection;

public class DaoFactory {
	private static final Connection connexion = MsSQLConnect.getConnexion();

	public static ContinentDAO getContinentDAO() {
		return new ContinentDAO(connexion);
	}

	public static CouleurDAO getCouleurDAO() {
		return new CouleurDAO(connexion);
	}

	public static TypeBiereDAO getTypeBiereDAO() {
		return new TypeBiereDAO(connexion);
	}

	public static PaysDAO getPaysDAO() {
		return new PaysDAO(connexion);
	}

	public static MarqueDAO getMarqueDAO() {
		return new MarqueDAO(connexion);
	}
	
	public static FabricantDAO getFabricantDAO() {
		return new FabricantDAO(connexion);
	}

	public static ArticleDAO getArticleDAO() {
		return new ArticleDAO(connexion);
	}
}
