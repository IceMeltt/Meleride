package pl.meleride.api.impl.event;

import pl.meleride.api.basic.User;

public class UserInitEvent extends EventImpl {

  private final User user;

  public UserInitEvent(User user) {
    this.user = user;
  }

  public User getUser() {
    return this.user;
  }

}
