package pl.meleride.shop.user;

import java.util.Optional;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ShopUser {

  private final UUID uniqueId;

  ShopUser(final UUID uniqueId) {
    this.uniqueId = uniqueId;
  }

  public UUID getUniqueId() {
    return this.uniqueId;
  }

  public Optional<Player> asBukkitPlayer() {
    return Optional.ofNullable(Bukkit.getPlayer(this.uniqueId));
  }

}
