package pl.meleride.api.storage.callable;

import java.sql.PreparedStatement;
import java.util.concurrent.Callable;

public class ExecuteUpdateCallable implements Callable<Integer> {

  private final PreparedStatement preparedStatement;

  public ExecuteUpdateCallable(PreparedStatement preparedStatement) {
    this.preparedStatement = preparedStatement;
  }

  @Override
  public Integer call() throws Exception {
    return this.preparedStatement.executeUpdate();
  }

}
