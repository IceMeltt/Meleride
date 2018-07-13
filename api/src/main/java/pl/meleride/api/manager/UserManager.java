package pl.meleride.api.manager;

import com.google.common.collect.ImmutableSet;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.entity.Player;
import pl.meleride.api.entity.User;

public interface UserManager<T extends User> {

  Optional<T> getUser(String name);

  Optional<T> getUser(UUID uniqueId);

  Optional<T> getUser(Player player);

  void addUser(T user);

  void removeUser(T user);

  ImmutableSet<T> getOnlineUsers();

}
