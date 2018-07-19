package pl.meleride.base.entity.impl;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.meleride.base.entity.User;

public class UserImpl implements User {

  private UUID identifier;
  private String name;
  private long drugCooldown;

  public UserImpl(Player player) {
    this.identifier = player.getUniqueId();
    this.name = player.getName();
    this.drugCooldown = 0L;
  }

  public UserImpl(UUID identifier) {
    this.identifier = identifier;
    this.name = Bukkit.getOfflinePlayer(this.identifier).getName();
    this.drugCooldown = 0L;
  }

  public UserImpl(String name) {
    this.name = name;
    this.identifier = UUID.nameUUIDFromBytes(("OfflinePlayer:" + this.name).getBytes(StandardCharsets.UTF_8));
    this.drugCooldown = 0L;
  }

  @Override
  public UUID getIdentifier() {
    return this.identifier;
  }

  @Override
  public Optional<String> getName() {
    return Optional.ofNullable(this.name);
  }

  @Override
  public void setIdentifier(UUID identifier) {
    this.identifier = identifier;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public long getDrugCooldown() {
    return this.drugCooldown;
  }

  @Override
  public void setDrugCooldown(long drugCooldown) {
    this.drugCooldown = drugCooldown;
  }

}
