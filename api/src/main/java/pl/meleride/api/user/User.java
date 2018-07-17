package pl.meleride.api.user;

import java.util.Set;
import org.bukkit.entity.Player;

import java.util.UUID;
import pl.meleride.api.user.status.DiseaseStatus;

public interface User {

  String getName();

  UUID getUniqueId();

  void setName(String name);

  Player getBukkitPlayer();

  boolean hasAnyDisease();

  boolean hasDisease(DiseaseStatus diseaseStatus);

  Set<DiseaseStatus> getDiseases();

  void addDisease(DiseaseStatus disease);

  void removeDisease(DiseaseStatus disease);

  void setReputation(int value);

  void addReputation(int amount);

  void resetReputation();

  int getReputation();

}
