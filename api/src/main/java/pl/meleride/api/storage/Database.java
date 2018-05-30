package pl.meleride.api.storage;

import java.sql.Connection;
import java.sql.SQLException;

public interface Database {

  Connection getConnection();

  boolean closeConnection() throws SQLException;

  void connect();

}
