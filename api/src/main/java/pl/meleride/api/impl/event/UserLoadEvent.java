package pl.meleride.api.impl.event;

import pl.meleride.api.basic.User;

public class UserLoadEvent extends EventImpl {

  private final User user;

  public UserLoadEvent(User user) {
    this.user = user;
  }

  public User getUser() {
    return this.user;
  }

}
