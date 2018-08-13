package pl.meleride.companies.entity.impl;

import com.google.common.base.Objects;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.Bukkit;
import pl.meleride.companies.entity.User;

public final class UserImpl implements User {

  private final UUID identifier;
  private String name;

  public UserImpl(UUID identifier) {
    this.identifier = identifier;
    this.name = Bukkit.getOfflinePlayer(this.identifier).getName();
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
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(identifier, name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserImpl user = (UserImpl) o;
    return Objects.equal(identifier, user.identifier) &&
        Objects.equal(name, user.name);
  }

}
