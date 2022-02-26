package gov.iti.jets.server.persistance;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public enum DataSource {

	INSTANCE;

	private HikariConfig config;
	private HikariDataSource dataSource;

	private DataSource() {
		Properties properties = new Properties();
		try {
			var props = new File(getClass().getResource("/hikaricp.properties").toURI());
			properties.load(new FileInputStream(props));
			config = new HikariConfig(properties);
			config.setMinimumIdle(8);
			config.setMaximumPoolSize(10);
			dataSource = new HikariDataSource(config);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public HikariDataSource getDataSource() {
		return dataSource;
	}

}
