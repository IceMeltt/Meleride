package pl.meleride.api.user;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface User {

  String getName();

  UUID getUniqueId();

  void setName(String name);

  Player getBukkitPlayer();

}
