package pl.meleride.api.storage.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import pl.meleride.api.storage.StorageException;

public abstract class AbstractSqlStorage implements SqlStorage {

  public abstract Connection getConnection() throws StorageException;

  @Override
  public void open() throws StorageException {
    this.update(TEST_QUERY);
  }

  @Override
  public void update(String query) throws StorageException {
    Connection connection = this.getConnection();
    try {
      connection.prepareStatement(query).executeUpdate();
    } catch (SQLException ex) {
      throw new StorageException(ex);
    } finally {
      this.closeConnection(connection);
    }
  }

  @Override
  public void update(String query, SQLStorageConsumer consumer) throws StorageException {
    Connection connection = this.getConnection();
    try {
      PreparedStatement statement = connection.prepareStatement(query);
      consumer.accept(statement);

      statement.executeUpdate();
    } catch (SQLException ex) {
      throw new StorageException(ex);
    } finally {
      this.closeConnection(connection);
    }
  }

  @Override
  public ResultSet query(Connection connection, String query) throws StorageException {
    try {
      return connection.prepareStatement(query).executeQuery();
    } catch (SQLException ex) {
      throw new StorageException(ex);
    }
  }

  @Override
  public ResultSet query(Connection connection, String query, SQLStorageConsumer consumer) throws StorageException {
    try {
      PreparedStatement statement = connection.prepareStatement(query);
      consumer.accept(statement);
      return statement.executeQuery();
    } catch (SQLException ex) {
      throw new StorageException(ex);
    }
  }

  public void closeConnection(Connection connection) throws StorageException {
    try {
      connection.close();
    } catch (SQLException e) {
      throw new StorageException(e);
    }
  }

}
