package pl.meleride.economy.user;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.meleride.api.flexible.BaseManager;

public class EconomyUserManager implements BaseManager<EconomyUser> {

  private final ConcurrentMap<String, EconomyUser> userNameMap = new ConcurrentHashMap<>(16, 0.9F, 1);
  private final ConcurrentMap<UUID, EconomyUser> userUniqueIdMap = new ConcurrentHashMap<>(16, 0.9F, 1);

  @Override
  public Optional<EconomyUser> getUser(String name) {
    Validate.notNull(name, "Player name cannot be null!");

    return this.userNameMap.values()
        .stream()
        .filter(user -> user.getName().equals(name))
        .findFirst();
  }

  @Override
  public Optional<EconomyUser> getUser(UUID uniqueId) {
    Validate.notNull(uniqueId, "Player unique id cannot be null!");

    return this.userUniqueIdMap.values()
        .stream()
        .filter(user -> user.getUniqueId().equals(uniqueId))
        .findFirst();
  }

  @Override
  public Optional<EconomyUser> getUser(Player player) {
    Validate.notNull(player, "Player object cannot be null!");

    return this.getUser(player.getUniqueId());
  }

  @Override
  public void addUser(EconomyUser user) {
    Validate.notNull(user, "User object cannot be null!");

    this.userUniqueIdMap.put(user.getUniqueId(), user);
    this.userNameMap.put(user.getName(), user);
  }

  @Override
  public void removeUser(EconomyUser user) {
    Validate.notNull(user, "User object cannot be null!");

    this.userUniqueIdMap.remove(user.getUniqueId());
    this.userNameMap.remove(user.getName());
  }

  @Override
  public Set<EconomyUser> getOnlineUsers() {
    return Bukkit.getServer().getOnlinePlayers().stream()
        .map(player -> this.getUser(player.getUniqueId()))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }


}
