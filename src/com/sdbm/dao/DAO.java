package com.sdbm.dao;

import java.sql.Connection;
import java.util.List;


public abstract class DAO <T>
{
	private Connection connexion;

	protected DAO(Connection connexion)
	{
		this.connexion = connexion;
	}
	
	protected Connection getConnexion() {
		return connexion;
	}

	public abstract T getByID(Object object);
	public abstract List<T> getAll();
	public abstract boolean insert(T object);
	public abstract boolean update(T object);
	public abstract boolean delete( T object);
}
