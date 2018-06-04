package pl.meleride.api.storage.sql.hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.commons.lang3.Validate;
import pl.meleride.api.storage.sql.AbstractSQLStorage;
import pl.meleride.api.storage.StorageException;

public class SQLHikariStorage extends AbstractSQLStorage {

  private final HikariDataSource source;

  public SQLHikariStorage(HikariConfig config) {
    Validate.notNull(config);

    this.source = new HikariDataSource(config);
  }

  public SQLHikariStorage(Properties properties) {
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
