package pl.meleride.api.basic;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface User {

  String getName();

  UUID getUniqueId();

  void setName(String name);

  void setUniqueId(UUID uniqueId);

  Player getBukkitPlayer();

}
