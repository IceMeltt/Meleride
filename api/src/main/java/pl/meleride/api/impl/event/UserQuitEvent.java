package pl.meleride.api.impl.event;

import pl.meleride.api.basic.User;

public class UserQuitEvent extends EventImpl {

  private final User user;

  public UserQuitEvent(User user) {
    this.user = user;
  }

  public User getUser() {
    return this.user;
  }

}
