package com.sdbm.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.sdbm.metier.Article;
import com.sdbm.metier.ArticleSearch;


public class ArticleDAO extends DAO<Article> {
	public ArticleDAO(Connection connexion) {
		super(connexion);
	}

	@Override
	public Article getByID(Object object) {// On passe un objet en argument car l'id n'est pas obligatoirement un int
		ResultSet resultSet = null; // On initialise � null pour pouvoir l'utiliser alors qu'il sera initialis� plus tard
		PreparedStatement preparedStatement = null;
		Article Article = null;
		int id;
		if(!(object instanceof Integer)) {
			return null;
		} 

		id = (int) object;
		try {
			String query = "SELECT ID_ARTICLE, NOM_ARTICLE FROM Article WHERE ID_ARTICLE = ?";
			preparedStatement = getConnexion().prepareStatement(query);
			//set des param�tres
			preparedStatement.setInt(1, id);
			//Mettre le r�sultat dans le resultSet
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				Article = new Article(resultSet.getInt(1), resultSet.getString(2));
			}	
		} catch(SQLException e) {
			e.printStackTrace();
			
		} finally {
			closeStatement(resultSet, preparedStatement);
		}
		return Article;
	}

	@Override
	public ArrayList<Article> getAll() {
		ResultSet resultSet = null; //Le curseur qui permet de r�cup�rer les lignes de r�sultats
		PreparedStatement preparedStatement = null;
		ArrayList<Article> listeArticles = new ArrayList<Article>();
		try {
			//I. Ecrire la requ�te
			String query = "SELECT ID_ARTICLE, NOM_ARTICLE FROM Article ORDER BY NOM_ARTICLE";
			//II. Cr�ation du statement, r�cup�ration de la connexion et preparation de la requete dans le statement
			preparedStatement = getConnexion().prepareStatement(query);
			//III. Excecuter la requete gr�ce au Statement et mettre le r�sultat dans le resultSet
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				listeArticles.add(new Article(resultSet.getInt(1), resultSet.getString(2)));
			}
				
		} catch(SQLException e) {
			listeArticles = null;
		} finally {
			closeStatement(resultSet, preparedStatement);
		}
		return listeArticles;
	}

	@Override
	public boolean insert(Article Article) {
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		try {
			//I. D�finition de la requ�te
			String query = "INSERT INTO Article VALUES(?)";
			preparedStatement = getConnexion().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);//=> on r�cup�re les clefs primaires g�n�r�es, ici l'id
			//set les param�tres
			preparedStatement.setString(1, Article.getNomArticle());
			//execution de la requete (un update car un insert into) via le prepareStatement ou statement
			preparedStatement.executeUpdate();
			//On ajoute les r�sultats de la r�cup�ration des clefs primaires ins�r�es (l'id de la ligne ins�r�e) que l'on met dans resultSet
			resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()){
				Article.setId(resultSet.getInt(1));
			}
			return true;

		}catch(SQLException e) {
			return false;
			
		} finally {
			closeStatement(resultSet, preparedStatement);
		}
	}

	@Override
	public boolean update(Article Article) {
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		try {
			String query = "UPDATE Article SET NOM_ARTICLE = ? WHERE ID_ARTICLE = ?";
			preparedStatement = getConnexion().prepareCall(query);
			//set les param�tres
			preparedStatement.setString(1, Article.getNomArticle());
			preparedStatement.setInt(2, Article.getId());
			// Nombre de lignes mise � jour
			int rowUpdated = preparedStatement.executeUpdate();
			if(rowUpdated == 0) {
				Article.setId(0);
			}
			return true;
		}catch(SQLException e) {
			return false;
		}finally {
			closeStatement(resultSet, preparedStatement);	
		}
	}

	@Override
	public boolean delete(Article Article) {
		boolean success = true;
		PreparedStatement preparedStatement = null;
		try {
			String query = "DELETE FROM Article WHERE ID_ARTICLE = ?";
			preparedStatement = getConnexion().prepareStatement(query);
			//set des parametres
			preparedStatement.setInt(1, Article.getId());
			//nombre de lignes supprim�es
			int rowDelated = preparedStatement.executeUpdate();
			if(rowDelated == 0) {
				Article.setId(0);
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
	
	//Fonction qui doit permettre de r�cup�rer un article de la vueArticle de la base de donn�es
	//en fonction d'une s�rie de crit�res via la proc�dure stock�e de la base de donn�es
	
	public ArrayList<Article> getLike(ArticleSearch articleSearch){
		//I. cr�ation de la variable ResultSet et initialisation du ResulSet � null
		ResultSet resultSet = null;
		//II. Cr�ation et initialisation du prepared Statement � null
		PreparedStatement preparedStatement = null;
		//III. cr�ation de l'arrayList qui re�evra les r�sultats
		ArrayList<Article> listeArticles = new ArrayList<Article>();
		//IV. On met la proc�dure stock�e dans une variable, attention � la syntaxe, il faut des accolades:
		//"{ call nom_de_la_proc�dure (sur SQLServer)(?, ?, ? etc etc)}" <= les param�tres (attention � leur nombre)
		String procedureStockee = "{call sp_QBE_VArticle (?,?,?,?,?,?,?,?,?,?)";
		try (CallableStatement callableStatement = getConnexion().prepareCall(procedureStockee)){
			//V. set les param�tres
		
			
			
			//VI. Ex�cuter la requ�te
			
			//VII. On r�cup�re les r�sultats
			
			
			
			
			
		}catch(Exception e) {
			listeArticles = null;
		} finally {
			closeStatement(resultSet, preparedStatement);
		}
		return listeArticles;
	}
	
	/**
	 * M�thode pour ajouter les param�tres � l'appel de la proc�dure stock�e afin d'�viter d'avoir une m�thode getLike trop longue
	 * @param callableStatement
	 * @param articleSearch
	 * @throws SQLException
	 */
	private void ajouterParametresGetLike(CallableStatement callableStatement, ArticleSearch articleSearch) throws SQLException {
		/*Param�tres de la requ�te pr�par�e: 
		 * 
		@nom varchar(25) = '',
		@volume int = 0,
		@titragemin float = 0,
		@titragemax float = 99,
		@marque int = 0,
		@fabricant int = 0 ,
		@pays int = 0,
		@continent int = 0,
		@couleur int = 0,
		@type int = 0
		 */
		
		callableStatement.setString(1, articleSearch.getNom());
		callableStatement.setInt(2, articleSearch.getVolume());
		callableStatement.setFloat(3, articleSearch.getTitrageMin());
		callableStatement.setFloat(4, articleSearch.getTitrageMax());
		callableStatement.setInt(5, articleSearch.getMarque());
		callableStatement.setInt(6, articleSearch.getFabricant());
		callableStatement.setInt(7, articleSearch.getPays());
		callableStatement.setInt(8, articleSearch.getContinent());
		callableStatement.setInt(9, articleSearch.getCouleur());
		callableStatement.setInt(10, articleSearch.getType());	
	}
	
	private void rowToArticle(Article article, ResultSet resultSet) throws Exception {
		
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
