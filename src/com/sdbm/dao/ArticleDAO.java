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
		ResultSet resultSet = null; // On initialise à null pour pouvoir l'utiliser alors qu'il sera initialisé plus tard
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
			//set des paramètres
			preparedStatement.setInt(1, id);
			//Mettre le résultat dans le resultSet
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
		ResultSet resultSet = null; //Le curseur qui permet de récupérer les lignes de résultats
		PreparedStatement preparedStatement = null;
		ArrayList<Article> listeArticles = new ArrayList<Article>();
		try {
			//I. Ecrire la requête
			String query = "SELECT ID_ARTICLE, NOM_ARTICLE FROM Article ORDER BY NOM_ARTICLE";
			//II. Création du statement, récupération de la connexion et preparation de la requete dans le statement
			preparedStatement = getConnexion().prepareStatement(query);
			//III. Excecuter la requete grâce au Statement et mettre le résultat dans le resultSet
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
			//I. Définition de la requête
			String query = "INSERT INTO Article VALUES(?)";
			preparedStatement = getConnexion().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);//=> on récupère les clefs primaires générées, ici l'id
			//set les paramètres
			preparedStatement.setString(1, Article.getNomArticle());
			//execution de la requete (un update car un insert into) via le prepareStatement ou statement
			preparedStatement.executeUpdate();
			//On ajoute les résultats de la récupération des clefs primaires insérées (l'id de la ligne insérée) que l'on met dans resultSet
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
			//set les paramètres
			preparedStatement.setString(1, Article.getNomArticle());
			preparedStatement.setInt(2, Article.getId());
			// Nombre de lignes mise à jour
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
			//nombre de lignes supprimées
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
	
	//Fonction qui doit permettre de récupérer un article de la vueArticle de la base de données
	//en fonction d'une série de critères via la procédure stockée de la base de données
	
	public ArrayList<Article> getLike(ArticleSearch articleSearch){
		//I. création de la variable ResultSet et initialisation du ResulSet à null
		ResultSet resultSet = null;
		//II. Création et initialisation du prepared Statement à null
		PreparedStatement preparedStatement = null;
		//III. création de l'arrayList qui reçevra les résultats
		ArrayList<Article> listeArticles = new ArrayList<Article>();
		//IV. On met la procédure stockée dans une variable, attention à la syntaxe, il faut des accolades:
		//"{ call nom_de_la_procédure (sur SQLServer)(?, ?, ? etc etc)}" <= les paramètres (attention à leur nombre)
		String procedureStockee = "{call sp_QBE_VArticle (?,?,?,?,?,?,?,?,?,?)";
		try (CallableStatement callableStatement = getConnexion().prepareCall(procedureStockee)){
			//V. set les paramètres
		
			
			
			//VI. Exécuter la requête
			
			//VII. On récupère les résultats
			
			
			
			
			
		}catch(Exception e) {
			listeArticles = null;
		} finally {
			closeStatement(resultSet, preparedStatement);
		}
		return listeArticles;
	}
	
	/**
	 * Méthode pour ajouter les paramètres à l'appel de la procédure stockée afin d'éviter d'avoir une méthode getLike trop longue
	 * @param callableStatement
	 * @param articleSearch
	 * @throws SQLException
	 */
	private void ajouterParametresGetLike(CallableStatement callableStatement, ArticleSearch articleSearch) throws SQLException {
		/*Paramètres de la requête préparée: 
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
