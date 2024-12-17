package fr.farmeurimmo.backenddemolocation.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManager {

    private static final HikariConfig config = new HikariConfig();
    public static DatabaseManager INSTANCE;
    private static HikariDataSource ds;

    public DatabaseManager(String jdbcUrl, String username, String password) throws Exception {
        INSTANCE = this;

        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setLeakDetectionThreshold(5_000);
        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(30_000);
        config.setIdleTimeout(600_000);
        config.setMaxLifetime(1_800_000);
        ds = new HikariDataSource(config);


    }

    public void close() {
        if (ds == null || ds.isClosed()) return;
        ds.close();
    }
}
