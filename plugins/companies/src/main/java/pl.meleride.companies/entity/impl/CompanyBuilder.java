package pl.meleride.companies.entity.impl;

import java.util.List;
import java.util.UUID;
import pl.meleride.api.helper.Buildable;
import pl.meleride.companies.entity.Company;
import pl.meleride.companies.entity.User;

public final class CompanyBuilder implements Buildable<Company> {

  private UUID identifier;
  private String name;
  private String business;
  private User owner;
  private List<User> workers;
  private int level;

  public CompanyBuilder withIdentifier(UUID identifier) {
    this.identifier = identifier;
    return this;
  }

  public CompanyBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public CompanyBuilder withBusiness(String business) {
    this.business = business;
    return this;
  }

  public CompanyBuilder withOwner(User owner) {
    this.owner = owner;
    return this;
  }

  public CompanyBuilder withWorkers(List<User> workers) {
    this.workers = workers;
    return this;
  }

  public CompanyBuilder withLevel(int level) {
    this.level = level;
    return this;
  }

  @Override
  public Company build() {
    return new CompanyImpl(this.identifier, this.name, this.business, this.owner, this.workers,
        this.level);
  }

}
