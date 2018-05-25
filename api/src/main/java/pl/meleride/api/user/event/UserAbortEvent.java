package pl.meleride.api.user.event;

import pl.meleride.api.event.EventImpl;
import pl.meleride.api.user.User;

public class UserAbortEvent extends EventImpl {

  private final User user;

  public UserAbortEvent(User user) {
    this.user = user;
  }

  public User getUser() {
    return this.user;
  }

}
