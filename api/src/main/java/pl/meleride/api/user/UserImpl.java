package pl.meleride.api.user;

import com.google.common.base.Charsets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class UserImpl implements User {

  private final UUID uniqueId;
  private String name;

  public UserImpl(String name) {
    this.name = name;
    this.uniqueId = UUID.nameUUIDFromBytes(("OfflinePlayer:" + this.name).getBytes(Charsets.UTF_8));
  }

  public UserImpl(UUID uniqueId) {
    this.uniqueId = uniqueId;
    this.name = Bukkit.getOfflinePlayer(this.uniqueId).getName();
  }

  public UserImpl(Player player) {
    this.uniqueId = player.getUniqueId();
    this.name = player.getName();
  }

  @Override
  public Optional<String> getName() {
    return Optional.ofNullable(this.name);
  }

  @Override
  public UUID getUniqueId() {
    return this.uniqueId;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public Player getBukkitPlayer() {
    return Bukkit.getPlayer(this.uniqueId);
  }

}
