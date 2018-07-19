package pl.meleride.user.entity.impl;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.meleride.user.disease.Disease;
import pl.meleride.user.entity.User;

public class UserImpl implements User {

  private UUID identifier;
  private String name;

  private final Set<Disease> diseases;

  public UserImpl(String name) {
    this.name = name;
    this.identifier = UUID.nameUUIDFromBytes(("OfflinePlayer:" + this.name).getBytes(
        StandardCharsets.UTF_8));
    this.diseases = new HashSet<>();
  }

  public UserImpl(UUID identifier) {
    this.identifier = identifier;
    this.name = Bukkit.getOfflinePlayer(this.identifier).getName();
    this.diseases = new HashSet<>();
  }

  public UserImpl(Player player) {
    this.identifier = player.getUniqueId();
    this.name = player.getName();
    this.diseases = new HashSet<>();
  }

  @Override
  public Optional<String> getName() {
    return Optional.ofNullable(this.name);
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public UUID getIdentifier() {
    return this.identifier;
  }

  @Override
  public void setIdentifier(UUID identifier) {
    this.identifier = identifier;
  }

  @Override
  public Set<Disease> getDiseases() {
    return new HashSet<>(diseases);
  }

  @Override
  public boolean hasDisease(Disease disease) {
    return diseases.contains(disease);
  }

  @Override
  public void addDisease(Disease disease) {
    this.diseases.add(disease);
  }

  @Override
  public void clearDiseases() {
    this.diseases.clear();
  }

}
