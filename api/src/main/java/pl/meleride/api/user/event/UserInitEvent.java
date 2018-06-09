package pl.meleride.api.user.event;

import pl.meleride.api.event.CustomEvent;
import pl.meleride.api.user.User;

public class UserInitEvent extends CustomEvent {

  private final User user;

  public UserInitEvent(User user) {
    this.user = user;
  }

  public User getUser() {
    return this.user;
  }

}
