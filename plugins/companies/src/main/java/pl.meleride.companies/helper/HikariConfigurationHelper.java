package pl.meleride.companies.helper;

import com.zaxxer.hikari.HikariConfig;
import pl.meleride.companies.MelerideCompanies;
import pl.meleride.companies.MelerideCompaniesPlugin;

public final class HikariConfigurationHelper {

  private final static MelerideCompanies PLUGIN = MelerideCompaniesPlugin
      .getPlugin(MelerideCompaniesPlugin.class);

  public static HikariConfig getDataSourceConfiguration() {
    HikariConfig config = new HikariConfig();

    config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
    config.addDataSourceProperty("serverName", PLUGIN.getConfig().getString("mysql.host"));
    config.addDataSourceProperty("port", PLUGIN.getConfig().getString("mysql.port"));
    config.addDataSourceProperty("databaseName", PLUGIN.getConfig().getString("mysql.database"));
    config.addDataSourceProperty("user", PLUGIN.getConfig().getString("mysql.user"));
    config.addDataSourceProperty("password", PLUGIN.getConfig().getString("mysql.password"));
    config.addDataSourceProperty("cachePrepStmts", true);
    config.addDataSourceProperty("prepStmtCacheSize", 250);
    config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
    config.addDataSourceProperty("useServerPrepStmts", true);
    config.addDataSourceProperty("cacheResultSetMetadata", true);
    config.setMaximumPoolSize(16);

    return config;
  }

  private HikariConfigurationHelper() {

  }

}
