package com.sdbm.dao;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.Connection;

public class MsSQLConnect
{
	// Declare the JDBC objects.
	private static Connection connexion;

	private MsSQLConnect()
	{
		try
		{
			SQLServerDataSource ds = new SQLServerDataSource();
			ds.setServerName("127.0.0.1");
			ds.setPortNumber(1401);
			ds.setDatabaseName("SDBM");
			ds.setIntegratedSecurity(false);
			ds.setEncrypt(false);
			ds.setUser("sa");
			ds.setPassword("azerty@123456" );
			connexion = ds.getConnection();
		}

		// Handle any errors that may have occurred.
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static synchronized Connection getConnexion()
	{
		if (connexion == null) {
			new MsSQLConnect();
		}
		return connexion;
	}
}
