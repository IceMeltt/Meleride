package pl.meleride.api.storage.callable;

import com.zaxxer.hikari.HikariDataSource;
import pl.meleride.api.storage.MysqlDatabase;

import java.util.concurrent.Callable;

public class DatabaseConnectionCallable implements Callable<Void> {

  private final MysqlDatabase mysqlDatabase;

  public DatabaseConnectionCallable(MysqlDatabase mysqlDatabase) {
    this.mysqlDatabase = mysqlDatabase;
  }

  @Override
  public Void call() {
    int cores = Runtime.getRuntime().availableProcessors();

    HikariDataSource dataSource = this.mysqlDatabase.getDataSource();

    dataSource.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
    dataSource.addDataSourceProperty("serverName", this.mysqlDatabase.getServerName());
    dataSource.addDataSourceProperty("port", this.mysqlDatabase.getPort());
    dataSource.addDataSourceProperty("databaseName", this.mysqlDatabase.getDatabaseName());
    dataSource.addDataSourceProperty("user", this.mysqlDatabase.getUsername());
    dataSource.addDataSourceProperty("password", this.mysqlDatabase.getPassword());
    dataSource.addDataSourceProperty("cachePrepStmts", true);
    dataSource.addDataSourceProperty("prepStmtCacheSize", 250);
    dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
    dataSource.addDataSourceProperty("useServerPrepStmts", true);
    dataSource.setMaximumPoolSize((cores * 2) + 1);

    return null;
  }

}
