package pl.meleride.api.storage.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import pl.meleride.api.storage.StorageException;

public abstract class AbstractSqlStorage implements SqlStorage {

  protected abstract Connection getConnection() throws StorageException;

  @Override
  public void open() throws StorageException {
    this.update(TEST_QUERY);
  }

  @Override
  public void update(String query) throws StorageException {
    try (Connection connection = this.getConnection()) {
      connection.prepareStatement(query).executeUpdate();
    } catch (SQLException ex) {
      throw new StorageException(ex);
    }
  }

  @Override
  public void update(String query, SQLStorageConsumer consumer) throws StorageException {
    try (Connection connection = this.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(query);
      consumer.accept(statement);

      statement.executeUpdate();
    } catch (SQLException ex) {
      throw new StorageException(ex);
    }
  }

  @Override
  public ResultSet query(String query) throws StorageException {
    try {
      return this.getConnection().prepareStatement(query).executeQuery();
    } catch (SQLException ex) {
      throw new StorageException(ex);
    } finally {
      try {
        this.getConnection().close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public ResultSet query(String query, SQLStorageConsumer consumer) throws StorageException {
    try (Connection connection = this.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(query);
      consumer.accept(statement);

      return statement.executeQuery();
    } catch (SQLException ex) {
      throw new StorageException(ex);
    }finally {
      try {
        this.getConnection().close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

}
