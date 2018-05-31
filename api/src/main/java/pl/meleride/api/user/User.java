package pl.meleride.api.user;

import org.bukkit.entity.Player;
import pl.meleride.api.storage.Basic;

import java.util.Optional;
import java.util.UUID;

public interface User extends Basic {

  Optional<String> getName();

  UUID getUniqueId();

  void setName(String name);

  Player getBukkitPlayer();

}
