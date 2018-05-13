package pl.meleride.api.impl.basic;

import com.google.common.base.Charsets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.meleride.api.MelerideAPI;
import pl.meleride.api.basic.User;

import java.util.UUID;

public class UserImpl implements User {

  private String name;
  private UUID uniqueId;

  public UserImpl(String name) {
    this.name = name;
    this.uniqueId = UUID.nameUUIDFromBytes(("OfflinePlayer:" + this.name).getBytes(Charsets.UTF_8));

    MelerideAPI.getInstance().getUserManager().addUser(this);
  }

  public UserImpl(UUID uniqueId) {
    this.uniqueId = uniqueId;
    this.name = Bukkit.getOfflinePlayer(this.uniqueId).getName();

    MelerideAPI.getInstance().getUserManager().addUser(this);
  }

  public UserImpl(Player player) {
    this.uniqueId = player.getUniqueId();
    this.name = player.getName();

    MelerideAPI.getInstance().getUserManager().addUser(this);
  }

  @Override
  public String getName() {
    return this.name;
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
  public void setUniqueId(UUID uniqueId) {
    this.uniqueId = uniqueId;
  }

  @Override
  public Player getBukkitPlayer() {
    return Bukkit.getPlayer(this.uniqueId);
  }

}