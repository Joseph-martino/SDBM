package com.sdbm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.sdbm.metier.Marque;

public class MarqueDAO extends DAO<Marque> {
	public MarqueDAO(Connection connexion) {
		super(connexion);
	}

	@Override
	public Marque getByID(Object object) {// On passe un objet en argument car l'id n'est pas obligatoirement un int
		ResultSet resultSet = null; // On initialise à null pour pouvoir l'utiliser alors qu'il sera initialisé plus tard
		PreparedStatement preparedStatement = null;
		Marque marque = null;
		int id;
		if(!(object instanceof Integer)) {
			return null;
		} 

		id = (int) object;
		try {
			String query = "SELECT ID_MARQUE, NOM_MARQUE FROM Marque WHERE ID_MARQUE = ?";
			preparedStatement = getConnexion().prepareStatement(query);
			//set des paramètres
			preparedStatement.setInt(1, id);
			//Mettre le résultat dans le resultSet
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				marque = new Marque(resultSet.getInt(1), resultSet.getString(2));
			}	
		} catch(SQLException e) {
			e.printStackTrace();
			
		} finally {
			closeStatement(resultSet, preparedStatement);
		}
		return marque;
	}

	@Override
	public ArrayList<Marque> getAll() {
		ResultSet resultSet = null; //Le curseur qui permet de récupérer les lignes de résultats
		PreparedStatement preparedStatement = null;
		ArrayList<Marque> listeNomsMarque = new ArrayList<Marque>();
		try {
			//I. Ecrire la requête
			String query = "SELECT ID_MARQUE, NOM_MARQUE FROM Marque ORDER BY NOM_MARQUE";
			//II. Création du statement, récupération de la connexion et preparation de la requete dans le statement
			preparedStatement = getConnexion().prepareStatement(query);
			//III. Excecuter la requete grâce au Statement et mettre le résultat dans le resultSet
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				listeNomsMarque.add(new Marque(resultSet.getInt(1), resultSet.getString(2)));
			}
				
		} catch(SQLException e) {
			listeNomsMarque = null;
		} finally {
			closeStatement(resultSet, preparedStatement);
		}
		return listeNomsMarque;
	}

	@Override
	public boolean insert(Marque Marque) {
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		try {
			String query = "INSERT INTO Marque VALUES(?)";
			preparedStatement = getConnexion().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);//=> on récupère les clefs primaires générées, ici l'id
			//set les paramètres
			preparedStatement.setString(1, Marque.getNomMarque());
			//execution de la requete (un update car un insert into) via le prepareStatement ou statement
			preparedStatement.executeUpdate();
			//On ajoute les résultats de la récupération des clefs primaires insérées (l'id de la ligne insérée) que l'on met dans resultSet
			resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()){
				Marque.setId(resultSet.getInt(1));
			}
			return true;

		}catch(SQLException e) {
			return false;
			
		} finally {
			closeStatement(resultSet, preparedStatement);
		}
	}

	@Override
	public boolean update(Marque Marque) {
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		try {
			String query = "UPDATE Marque SET NOM_MARQUE = ? WHERE ID_MARQUE = ?";
			preparedStatement = getConnexion().prepareCall(query);
			//set les paramètres
			preparedStatement.setString(1, Marque.getNomMarque());
			preparedStatement.setInt(2, Marque.getId());
			// Nombre de lignes mise à jour
			int rowUpdated = preparedStatement.executeUpdate();
			if(rowUpdated == 0) {
				Marque.setId(0);
			}
			return true;
		}catch(SQLException e) {
			return false;
		}finally {
			closeStatement(resultSet, preparedStatement);	
		}
	}

	@Override
	public boolean delete(Marque Marque) {
		boolean success = true;
		PreparedStatement preparedStatement = null;
		try {
			String query = "DELETE FROM Marque WHERE ID_MARQUE = ?";
			preparedStatement = getConnexion().prepareStatement(query);
			//set des parametres
			preparedStatement.setInt(1, Marque.getId());
			//nombre de lignes supprimées
			int rowDelated = preparedStatement.executeUpdate();
			if(rowDelated == 0) {
				Marque.setId(0);
			}
		}catch(SQLException e) {
			success = false;
			
		}finally {
			try {
				if(preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
		
		return success;
	}
	
	private void closeStatement(ResultSet resultSet, PreparedStatement preparedStatement) {
		try {
			if(resultSet != null && !resultSet.isClosed()) {
				resultSet.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(preparedStatement != null && !preparedStatement.isClosed()) {
				preparedStatement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
