package pl.meleride.api.manager;

import com.google.common.collect.ImmutableSet;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.meleride.api.entity.User;

public abstract class AbstractUserManager<T extends User> implements UserManager<T> {

  private final ConcurrentMap<String, T> userNameMap = new ConcurrentHashMap<>(16, 0.9F, 1);
  private final ConcurrentMap<UUID, T> userUniqueIdMap = new ConcurrentHashMap<>(16, 0.9F, 1);

  @Override
  public Optional<T> getUser(String name) {
    Validate.notNull(name, "Player name cannot be null!");

    return this.userNameMap.values()
        .stream()
        .filter(user -> user.getName().get().equals(name))
        .findFirst();
  }

  @Override
  public Optional<T> getUser(UUID uniqueId) {
    Validate.notNull(uniqueId, "Player unique id cannot be null!");

    return this.userUniqueIdMap.values()
        .stream()
        .filter(user -> user.getIdentifier().equals(uniqueId))
        .findFirst();
  }

  @Override
  public Optional<T> getUser(Player player) {
    Validate.notNull(player, "Player object cannot be null!");

    return this.getUser(player.getUniqueId());
  }

  @Override
  public void addUser(T user) {
    Validate.notNull(user, "User object cannot be null!");

    this.userUniqueIdMap.put(user.getIdentifier(), user);
    if (user.getName().isPresent()) {
      this.userNameMap.put(user.getName().get(), user);
    }
  }

  @Override
  public void removeUser(T user) {
    Validate.notNull(user, "User object cannot be null!");

    this.userUniqueIdMap.remove(user.getIdentifier());
    this.userNameMap.remove(user.getName().get());
  }

  @Override
  public ImmutableSet<T> getOnlineUsers() {
    return ImmutableSet.copyOf(Bukkit.getServer().getOnlinePlayers().stream()
        .map(player -> this.getUser(player.getUniqueId()))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toSet()));
  }

}
