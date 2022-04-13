package com.sdbm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.sdbm.metier.TypeBiere;

public class TypeBiereDAO extends DAO<TypeBiere> {
	public TypeBiereDAO(Connection connexion) {
		super(connexion);
	}

	@Override
	public TypeBiere getByID(Object object) {// On passe un objet en argument car l'id n'est pas obligatoirement un int
		ResultSet resultSet = null; // On initialise à null pour pouvoir l'utiliser alors qu'il sera initialisé plus tard
		PreparedStatement preparedStatement = null;
		TypeBiere typeBiere = null;
		int id;
		if(!(object instanceof Integer)) {
			return null;
		} 

		id = (int) object;
		try {
			String query = "SELECT ID_TYPE, NOM_TYPE FROM TYPEBIERE WHERE ID_TYPE = ?";
			preparedStatement = getConnexion().prepareStatement(query);
			//set des paramètres
			preparedStatement.setInt(1, id);
			//Mettre le résultat dans le resultSet
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				typeBiere = new TypeBiere(resultSet.getInt(1), resultSet.getString(2));
			}	
		} catch(SQLException e) {
			e.printStackTrace();
			
		} finally {
			closeStatement(resultSet, preparedStatement);
		}
		return typeBiere;
	}

	@Override
	public ArrayList<TypeBiere> getAll() {
		ResultSet resultSet = null; //Le curseur qui permet de récupérer les lignes de résultats
		PreparedStatement preparedStatement = null;
		ArrayList<TypeBiere> listeTypesBiere = new ArrayList<TypeBiere>();
		try {
			//I. Ecrire la requête
			String query = "SELECT ID_TYPE, NOM_TYPE FROM TYPEBIERE ORDER BY NOM_TYPE";
			//II. Création du statement, récupération de la connexion et preparation de la requete dans le statement
			preparedStatement = getConnexion().prepareStatement(query);
			//III. Excecuter la requete grâce au Statement et mettre le résultat dans le resultSet
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				listeTypesBiere.add(new TypeBiere(resultSet.getInt(1), resultSet.getString(2)));
			}
				
		} catch(SQLException e) {
			listeTypesBiere = null;
		} finally {
			closeStatement(resultSet, preparedStatement);
		}
		return listeTypesBiere;
	}

	@Override
	public boolean insert(TypeBiere typeBiere) {
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		try {
			//I. Définition de la requête
			String query = "INSERT INTO TYPEBIERE VALUES(?)";
			preparedStatement = getConnexion().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);//=> on récupère les clefs primaires générées, ici l'id
			//set les paramètres
			preparedStatement.setString(1, typeBiere.getNomBiere());
			//execution de la requete (un update car un insert into) via le prepareStatement ou statement
			preparedStatement.executeUpdate();
			//On ajoute les résultats de la récupération des clefs primaires insérées (l'id de la ligne insérée) que l'on met dans resultSet
			resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()){
				typeBiere.setId(resultSet.getInt(1));
			}
			return true;

		}catch(SQLException e) {
			return false;
			
		} finally {
			closeStatement(resultSet, preparedStatement);
		}
	}

	@Override
	public boolean update(TypeBiere typeBiere) {
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		try {
			String query = "UPDATE TYPEBIERE SET nom_type = ? WHERE id_type = ?";
			preparedStatement = getConnexion().prepareCall(query);
			//set les paramètres
			preparedStatement.setString(1, typeBiere.getNomBiere());
			preparedStatement.setInt(2, typeBiere.getId());
			// Nombre de lignes mise à jour
			int rowUpdated = preparedStatement.executeUpdate();
			if(rowUpdated == 0) {
				typeBiere.setId(0);
			}
			return true;
		}catch(SQLException e) {
			return false;
		}finally {
			closeStatement(resultSet, preparedStatement);	
		}
	}

	@Override
	public boolean delete(TypeBiere typeBiere) {
		boolean success = true;
		PreparedStatement preparedStatement = null;
		try {
			String query = "DELETE FROM TYPEBIERE WHERE ID_TYPE = ?";
			preparedStatement = getConnexion().prepareStatement(query);
			//set des parametres
			preparedStatement.setInt(1, typeBiere.getId());
			//nombre de lignes supprimées
			int rowDelated = preparedStatement.executeUpdate();
			if(rowDelated == 0) {
				typeBiere.setId(0);
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
