package pl.meleride.api.storage.sql.def;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.commons.lang3.Validate;
import pl.meleride.api.storage.sql.AbstractSqlStorage;
import pl.meleride.api.storage.StorageException;

public class DefaultSqlStorage extends AbstractSqlStorage {

  public final static String JDBC = "com.mysql.jdbc.Driver";

  private final String url;
  private Connection connection;

  public DefaultSqlStorage(final String url) {
    Validate.notNull(url);

    this.url = url;
  }

  @Override
  public void close() throws StorageException {
    try {
      if (state()) {
        connection.close();
        connection = null;
      }
    } catch (SQLException ex) {
      throw new StorageException(ex);
    }
  }

  @Override
  public boolean state() throws StorageException {
    try {
      return connection != null && !connection.isClosed();
    } catch (SQLException ex) {
      throw new StorageException(ex);
    }
  }

  @Override
  public Connection getConnection() throws StorageException {
    try {
      if (!state()) {
        Class.forName(JDBC);
        this.connection = DriverManager.getConnection(url);
      }

      return connection;
    } catch (ClassNotFoundException | SQLException ex) {
      throw new StorageException(ex);
    }
  }

}
