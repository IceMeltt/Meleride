package pl.meleride.api.storage.sql.hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.commons.lang3.Validate;
import pl.meleride.api.storage.sql.AbstractSqlStorage;
import pl.meleride.api.storage.StorageException;
import pl.meleride.api.storage.sql.SQLStorageConsumer;

public class SqlHikariStorage extends AbstractSqlStorage {

  private final HikariDataSource source;

  public SqlHikariStorage(final HikariConfig config) {
    Validate.notNull(config);

    this.source = new HikariDataSource(config);
  }

  public SqlHikariStorage(Properties properties) {
    this(new HikariConfig(properties));
  }

  @Override
  public void close() throws StorageException {
    if (state()) {
      try {
        this.source.close();
      } catch (Exception ex) {
        throw new StorageException(ex);
      }
    }
  }
  
  @Override
  public ResultSet query(String query, SQLStorageConsumer consumer) throws StorageException {
    try(Connection connection = this.source.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(query);
      consumer.accept(statement);
        
      return statement.executeQuery();
    } catch (SQLException ex) {
      throw new StorageException(ex);
    } finally {
      this.source.close();
    }
  }
  
  @Override
  public ResultSet query(String query) throws StorageException {
    try {
      return this.source.getConnection().prepareStatement(query).executeQuery();
    } catch (SQLException ex) {
      throw new StorageException(ex);
    } finally {
      this.source.close();
    }
  }
  
  @Override
  public boolean state() throws StorageException {
    try {
      return this.source.isRunning();
    } catch (Exception ex) {
      throw new StorageException(ex);
    }
  }

  @Override
  protected Connection getConnection() throws StorageException {
    try {
      return this.source.getConnection();
    } catch (SQLException ex) {
      throw new StorageException(ex);
    }
  }

}
