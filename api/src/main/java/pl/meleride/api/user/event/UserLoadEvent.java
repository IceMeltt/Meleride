package pl.meleride.api.user.event;

import pl.meleride.api.event.CustomEvent;
import pl.meleride.api.user.User;

public class UserLoadEvent extends CustomEvent {

  private final User user;

  public UserLoadEvent(User user) {
    this.user = user;
  }

  public User getUser() {
    return this.user;
  }

}