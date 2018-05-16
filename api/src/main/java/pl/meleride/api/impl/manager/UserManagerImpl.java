package pl.meleride.api.impl.manager;

import org.apache.commons.lang.Validate;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import pl.meleride.api.basic.User;
import pl.meleride.api.manager.UserManager;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class UserManagerImpl implements UserManager {

  @Inject
  private Server server;

  private final ConcurrentMap<String, User> userNameMap = new ConcurrentHashMap<>(16, 0.9F, 1);
  private final ConcurrentMap<UUID, User> userUniqueIdMap = new ConcurrentHashMap<>(16, 0.9F, 1);

  @Override
  public Optional<User> getUser(String name) {
    Validate.notNull(name, "Player name cannot be null!");

    return this.userNameMap.values()
        .stream()
        .filter(user -> user.getName().equalsIgnoreCase(name))
        .findFirst();
  }

  @Override
  public Optional<User> getUser(UUID uniqueId) {
    Validate.notNull(uniqueId, "Player unique id cannot be null!");

    return this.userUniqueIdMap.values()
        .stream()
        .filter(user -> user.getUniqueId().equals(uniqueId))
        .findFirst();
  }

  @Override
  public Optional<User> getUser(Player player) {
    Validate.notNull(player, "Player object cannot be null!");

    return this.getUser(player.getUniqueId());
  }

  @Override
  public void addUser(User user) {
    Validate.notNull(user, "User object cannot be null!");

    this.userUniqueIdMap.put(user.getUniqueId(), user);
    this.userNameMap.put(user.getName(), user);
  }

  @Override
  public void removeUser(User user) {
    Validate.notNull(user, "User object cannot be null!");

    this.userUniqueIdMap.remove(user.getUniqueId());
    this.userNameMap.remove(user.getName());
  }

  @Override
  public Set<User> getOnlineUsers() {
    return this.server.getOnlinePlayers().stream()
      .map(player -> this.getUser(player.getUniqueId())).filter(Optional::isPresent)
      .map(Optional::get)
      .collect(Collectors.toCollection(LinkedHashSet::new));
  }

}
