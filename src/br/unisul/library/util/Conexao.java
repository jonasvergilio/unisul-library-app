package br.unisul.library.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
	
	public static Connection conectaSqlite() {
		Connection con = null;
		try {
			File arquivo = new File("database/library.sqlite");
			if(arquivo.exists()) {
				Class.forName("org.sqlite.JDBC");
				con = DriverManager.getConnection("jdbc:sqlite:database/library.sqlite");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
}
