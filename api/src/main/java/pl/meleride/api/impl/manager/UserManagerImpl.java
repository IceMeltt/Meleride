package pl.meleride.api.impl.manager;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.meleride.api.basic.User;
import pl.meleride.api.impl.basic.UserImpl;
import pl.meleride.api.manager.UserManager;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserManagerImpl implements UserManager {

  private final ConcurrentMap<String, User> userNameMap = new ConcurrentHashMap<>();
  private final ConcurrentMap<UUID, User> userUniqueIdMap = new ConcurrentHashMap<>();

  @Override
  public User getUser(String name) {
    Validate.notNull(name, "Player name cannot be null!");

    return this.userNameMap.values()
        .stream()
        .filter(user -> user.getName().equalsIgnoreCase(name))
        .findFirst()
        .orElse(new UserImpl(name));
  }

  @Override
  public User getUser(UUID uniqueId) {
    Validate.notNull(uniqueId, "Player unique id cannot be null!");

    return this.userUniqueIdMap.values()
        .stream()
        .filter(user -> user.getUniqueId().equals(uniqueId))
        .findFirst()
        .orElse(new UserImpl(uniqueId));
  }

  @Override
  public User getUser(Player player) {
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
    Set<User> users = new LinkedHashSet<>();
    Bukkit.getOnlinePlayers().forEach(player -> users.add(this.getUser(player.getUniqueId())));

    return users;
  }

}