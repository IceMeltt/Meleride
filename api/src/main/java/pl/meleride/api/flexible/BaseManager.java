package pl.meleride.api.flexible;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.bukkit.entity.Player;
import pl.meleride.api.user.User;

public interface BaseManager<T> {

  Optional<T> getUser(String name);

  Optional<T> getUser(UUID uniqueId);

  Optional<T> getUser(Player player);

  void addUser(T user);

  void removeUser(T user);

  Set<T> getOnlineUsers();

}
