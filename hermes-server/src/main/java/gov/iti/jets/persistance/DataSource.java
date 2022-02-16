package gov.iti.jets.persistance;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public enum DataSource {

	INSTANCE;

	private  HikariConfig config = new HikariConfig();
	private  HikariDataSource ds;

	{
		config.setJdbcUrl("jdbc:mysql://localhost:3306/hermesdb");
		config.setUsername("root");
		config.setPassword("mina1234");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		ds = new HikariDataSource(config);
	}

	public  Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

}
