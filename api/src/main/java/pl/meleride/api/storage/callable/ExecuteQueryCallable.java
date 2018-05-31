package pl.meleride.api.storage.callable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.Callable;

public class ExecuteQueryCallable implements Callable<ResultSet> {

  private final PreparedStatement preparedStatement;

  public ExecuteQueryCallable(PreparedStatement preparedStatement) {
    this.preparedStatement = preparedStatement;
  }

  @Override
  public ResultSet call() throws Exception {
    return this.preparedStatement.executeQuery();
  }

}
