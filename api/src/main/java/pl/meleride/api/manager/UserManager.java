package pl.meleride.api.manager;

import org.bukkit.entity.Player;
import pl.meleride.api.basic.User;

import java.util.Set;
import java.util.UUID;

public interface UserManager {

  User getUser(String name);

  User getUser(UUID uniqueId);

  User getUser(Player player);

  void addUser(User user);

  void removeUser(User user);

  Set<User> getOnlineUsers();

}
