package pl.meleride.companies.entity.impl;

import com.google.common.base.Objects;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.Bukkit;
import pl.meleride.companies.entity.Company;
import pl.meleride.companies.entity.User;
import pl.meleride.companies.enums.MakeStatus;

public final class UserImpl implements User {

  private final UUID identifier;
  private String name;
  private Company company;
  private MakeStatus status;

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
  public Optional<Company> getCompany() {
    return Optional.ofNullable(this.company);
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public void setCompany(Company company) {
    this.company = company;
  }

  @Override
  public MakeStatus getMakingStatus() {
    return this.status;
  }

  @Override
  public void setMakingStatus(MakeStatus status) {
    this.status = status;
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
