package com.sdbm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.sdbm.metier.Pays;


public class PaysDAO extends DAO<Pays> {
	public PaysDAO(Connection connexion) {
		super(connexion);
	}

	@Override
	public Pays getByID(Object object) {// On passe un objet en argument car l'id n'est pas obligatoirement un int
		ResultSet resultSet = null; // On initialise à null pour pouvoir l'utiliser alors qu'il sera initialisé plus tard
		PreparedStatement preparedStatement = null;
		Pays Pays = null;
		int id;
		if(!(object instanceof Integer)) {
			return null;
		} 

		id = (int) object;
		try {
			String query = "SELECT ID_PAYS, NOM_PAYS FROM Pays WHERE ID_PAYS = ?";
			preparedStatement = getConnexion().prepareStatement(query);
			//set des paramètres
			preparedStatement.setInt(1, id);
			//Mettre le résultat dans le resultSet
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				Pays = new Pays(resultSet.getInt(1), resultSet.getString(2));
			}	
		} catch(SQLException e) {
			e.printStackTrace();
			
		} finally {
			closeStatement(resultSet, preparedStatement);
		}
		return Pays;
	}

	@Override
	public ArrayList<Pays> getAll() {
		ResultSet resultSet = null; //Le curseur qui permet de récupérer les lignes de résultats
		PreparedStatement preparedStatement = null;
		ArrayList<Pays> listeNomsPays = new ArrayList<Pays>();
		try {
			//I. Ecrire la requête
			String query = "SELECT ID_PAYS, NOM_PAYS FROM Pays ORDER BY NOM_PAYS";
			//II. Création du statement, récupération de la connexion et preparation de la requete dans le statement
			preparedStatement = getConnexion().prepareStatement(query);
			//III. Excecuter la requete grâce au Statement et mettre le résultat dans le resultSet
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				listeNomsPays.add(new Pays(resultSet.getInt(1), resultSet.getString(2)));
			}
				
		} catch(SQLException e) {
			listeNomsPays = null;
		} finally {
			closeStatement(resultSet, preparedStatement);
		}
		return listeNomsPays;
	}

	@Override
	public boolean insert(Pays Pays) {
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		try {
			String query = "INSERT INTO Pays VALUES(?)";
			preparedStatement = getConnexion().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);//=> on récupère les clefs primaires générées, ici l'id
			//set les paramètres
			preparedStatement.setString(1, Pays.getNomPays());
			//execution de la requete (un update car un insert into) via le prepareStatement ou statement
			preparedStatement.executeUpdate();
			//On ajoute les résultats de la récupération des clefs primaires insérées (l'id de la ligne insérée) que l'on met dans resultSet
			resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()){
				Pays.setId(resultSet.getInt(1));
			}
			return true;

		}catch(SQLException e) {
			return false;
			
		} finally {
			closeStatement(resultSet, preparedStatement);
		}
	}

	@Override
	public boolean update(Pays Pays) {
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		try {
			String query = "UPDATE Pays SET NOM_PAYS = ? WHERE ID_PAYS = ?";
			preparedStatement = getConnexion().prepareCall(query);
			//set les paramètres
			preparedStatement.setString(1, Pays.getNomPays());
			preparedStatement.setInt(2, Pays.getId());
			// Nombre de lignes mise à jour
			int rowUpdated = preparedStatement.executeUpdate();
			if(rowUpdated == 0) {
				Pays.setId(0);
			}
			return true;
		}catch(SQLException e) {
			return false;
		}finally {
			closeStatement(resultSet, preparedStatement);	
		}
	}

	@Override
	public boolean delete(Pays Pays) {
		boolean success = true;
		PreparedStatement preparedStatement = null;
		try {
			String query = "DELETE FROM Pays WHERE ID_PAYS = ?";
			preparedStatement = getConnexion().prepareStatement(query);
			//set des parametres
			preparedStatement.setInt(1, Pays.getId());
			//nombre de lignes supprimées
			int rowDelated = preparedStatement.executeUpdate();
			if(rowDelated == 0) {
				Pays.setId(0);
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
