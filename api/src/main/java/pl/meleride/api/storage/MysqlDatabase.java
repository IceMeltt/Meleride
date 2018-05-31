package pl.meleride.api.storage;

import com.zaxxer.hikari.HikariDataSource;
import pl.meleride.api.storage.callable.DatabaseConnectionCallable;

import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class MysqlDatabase extends AbstractDatabase {

  private final ExecutorService executorService;
  private HikariDataSource dataSource;

  private final String databaseName;
  private final String serverName;
  private final String username;
  private final String password;
  private final int port;

  public MysqlDatabase(ExecutorService executorService, String databaseName, String serverName, String username, String password, int port) {
    this.executorService = executorService;
    this.databaseName = databaseName;
    this.serverName = serverName;
    this.username = username;
    this.password = password;
    this.port = port;
    this.dataSource = new HikariDataSource();

    this.connect();

    try {
      connection = dataSource.getConnection();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void connect() {
    Callable<Void> databaseCallable = new DatabaseConnectionCallable(this);
    Future<Void> future = this.executorService.submit(databaseCallable);

    try {
      future.get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }

  public HikariDataSource getDataSource() {
    return this.dataSource;
  }

  public String getDatabaseName() {
    return this.databaseName;
  }

  public String getServerName() {
    return this.serverName;
  }

  public String getUsername() {
    return this.username;
  }

  public String getPassword() {
    return this.password;
  }

  public int getPort() {
    return this.port;
  }

}
