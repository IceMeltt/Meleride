package pl.meleride.api.impl.event;

import pl.meleride.api.basic.User;

public class UserAbortEvent extends EventImpl {

  private final User user;

  public UserAbortEvent(User user) {
    this.user = user;
  }

  public User getUser() {
    return this.user;
  }

}
