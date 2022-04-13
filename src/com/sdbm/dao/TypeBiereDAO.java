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
		ResultSet resultSet = null; // On initialise � null pour pouvoir l'utiliser alors qu'il sera initialis� plus tard
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
			//set des param�tres
			preparedStatement.setInt(1, id);
			//Mettre le r�sultat dans le resultSet
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
		ResultSet resultSet = null; //Le curseur qui permet de r�cup�rer les lignes de r�sultats
		PreparedStatement preparedStatement = null;
		ArrayList<TypeBiere> listeTypesBiere = new ArrayList<TypeBiere>();
		try {
			//I. Ecrire la requ�te
			String query = "SELECT ID_TYPE, NOM_TYPE FROM TYPEBIERE ORDER BY NOM_TYPE";
			//II. Cr�ation du statement, r�cup�ration de la connexion et preparation de la requete dans le statement
			preparedStatement = getConnexion().prepareStatement(query);
			//III. Excecuter la requete gr�ce au Statement et mettre le r�sultat dans le resultSet
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
			//I. D�finition de la requ�te
			String query = "INSERT INTO TYPEBIERE VALUES(?)";
			preparedStatement = getConnexion().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);//=> on r�cup�re les clefs primaires g�n�r�es, ici l'id
			//set les param�tres
			preparedStatement.setString(1, typeBiere.getNomBiere());
			//execution de la requete (un update car un insert into) via le prepareStatement ou statement
			preparedStatement.executeUpdate();
			//On ajoute les r�sultats de la r�cup�ration des clefs primaires ins�r�es (l'id de la ligne ins�r�e) que l'on met dans resultSet
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
			//set les param�tres
			preparedStatement.setString(1, typeBiere.getNomBiere());
			preparedStatement.setInt(2, typeBiere.getId());
			// Nombre de lignes mise � jour
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
			//nombre de lignes supprim�es
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
