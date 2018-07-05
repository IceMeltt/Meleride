package pl.meleride.api.flexible;

import java.util.UUID;
import org.bukkit.entity.Player;

public interface PlayableUser {

  UUID getUniqueId();

  Player getBukkitPlayer();

  String getName();

  void setName(String name);

  byte getDataErrorStatus();

  void setDataErrorStatus(byte dataErrorStatus);

}
