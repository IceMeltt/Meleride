package pl.meleride.api.user.manager;

import org.bukkit.entity.Player;
import pl.meleride.api.user.User;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserManager {

  Optional<User> getUser(String name);

  Optional<User> getUser(UUID uniqueId);

  Optional<User> getUser(Player player);

  void addUser(User user);

  void removeUser(User user);

  Set<User> getOnlineUsers();

}
