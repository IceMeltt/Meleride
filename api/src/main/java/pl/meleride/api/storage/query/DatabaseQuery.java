package pl.meleride.api.storage.query;

import java.sql.ResultSet;

public interface DatabaseQuery {

  int executeUpdate();

  ResultSet executeQuery();

}
