package pl.meleride.api.user.event;

import pl.meleride.api.event.EventImpl;
import pl.meleride.api.user.User;

public class UserLoadEvent extends EventImpl {

  private final User user;

  public UserLoadEvent(User user) {
    this.user = user;
  }

  public User getUser() {
    return this.user;
  }

}
