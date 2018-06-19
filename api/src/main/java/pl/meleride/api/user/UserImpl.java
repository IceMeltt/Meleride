package pl.meleride.api.user;

import com.google.common.base.Charsets;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import pl.meleride.api.user.status.DiseaseStatus;

public class UserImpl implements User {

  private String name;
  private final UUID uniqueId;

  private final Set<DiseaseStatus> disease = new HashSet<>();

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
  public Player getBukkitPlayer() {
    return Bukkit.getPlayer(this.uniqueId);
  }

  @Override
  public boolean hasAnyDisease() {
    return this.disease.isEmpty();
  }

  @Override
  public boolean hasDisease(DiseaseStatus disease) {
    return this.disease.contains(disease);
  }

  @Override
  public Set<DiseaseStatus> getDiseases() {
    return this.disease;
  }

  @Override
  public void addDisease(DiseaseStatus disease) {
    this.disease.add(disease);
  }

  @Override
  public void removeDisease(DiseaseStatus disease) {
    if (this.disease.contains(disease)) {
      this.disease.remove(disease);
    }
  }

}
