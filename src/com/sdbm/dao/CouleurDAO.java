package com.sdbm.dao;

import com.sdbm.metier.Couleur;

import java.sql.*;
import java.util.ArrayList;

public class CouleurDAO extends DAO<Couleur> {
	public CouleurDAO(Connection connexion) {
		super(connexion);
	}

	public ArrayList<Couleur> getAll() {
		ResultSet rs;
		ArrayList<Couleur> liste = new ArrayList<>();
		try {
			Statement stmt = getConnexion().createStatement();
			String strCmd = "SELECT id_couleur ,nom_couleur from couleur order by nom_couleur";
			rs = stmt.executeQuery(strCmd);
			while (rs.next()) {
				liste.add(new Couleur(rs.getInt(1), rs.getString(2)));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			liste = null;
		}
		return liste;
	}

	@Override
	public Couleur getByID(Object object) {
		ResultSet rs;
		int id;
		Couleur couleur = null;
		if (object instanceof Integer)
			id = (int) object;
		else
			return null;
		try {

			Statement stmt = getConnexion().createStatement();
			String strCmd = "SELECT id_couleur ,nom_couleur from couleur where id_couleur = " + id;
			rs = stmt.executeQuery(strCmd);
			if (rs.next())
				couleur = new Couleur(rs.getInt(1), rs.getString(2));
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			couleur = null;
		}
		return couleur;
	}

	public boolean insert(Couleur couleur) {
		try {
			String requete = "insert into Couleur values (?)";

			PreparedStatement pStmt = getConnexion().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			pStmt.setString(1, couleur.getLibelle());
			pStmt.executeUpdate();
			ResultSet rs = pStmt.getGeneratedKeys();
			if (rs.next())
				couleur.setId(rs.getInt(1));
			rs.close();
			pStmt.close();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public boolean delete(Couleur object) {
		boolean success = true;
		try {
			PreparedStatement preparedStatement = getConnexion()
					.prepareStatement("delete FROM couleur where ID_COULEUR=?");
			preparedStatement.setInt(1, object.getId());
			int rowDeleted = preparedStatement.executeUpdate();
			if (rowDeleted == 0)
				object.setId(0);

		} catch (SQLException e) {
			success = false;
		}
		return success;
	}

	public boolean update(Couleur couleur) {
		try {
			String requete = "update Couleur set nom_couleur = ? where id_couleur = ?";

			PreparedStatement pStmt = getConnexion().prepareStatement(requete);
			pStmt.setString(1, couleur.getLibelle());
			pStmt.setInt(2, couleur.getId());
			int rowUpdated = pStmt.executeUpdate();
			if (rowUpdated == 0)
				couleur.setId(0);
			pStmt.close();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
}
