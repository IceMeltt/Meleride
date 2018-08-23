package pl.meleride.companies.manager.impl;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.commons.lang.Validate;
import pl.meleride.companies.entity.Company;
import pl.meleride.companies.manager.CompanyManager;

public class CompanyManagerImpl implements CompanyManager {

  private final ConcurrentMap<String, Company> companyNameMap = new ConcurrentHashMap<>(16, 0.9F, 1);
  private final ConcurrentMap<UUID, Company> companyUniqueIdMap = new ConcurrentHashMap<>(16, 0.9F, 1);

  @Override
  public Optional<Company> getCompany(String name) {
    Validate.notNull(name, "Company name cannot be null!");

    return this.companyNameMap.values()
        .stream()
        .filter(company -> company.getName().get().equals(name))
        .findFirst();
  }

  @Override
  public Optional<Company> getCompany(UUID uniqueId) {
    Validate.notNull(uniqueId, "Company unique id cannot be null!");

    return this.companyUniqueIdMap.values()
        .stream()
        .filter(user -> user.getIdentifier().equals(uniqueId))
        .findFirst();
  }

  @Override
  public Optional<Company> getCompany(Company company) {
    Validate.notNull(company, "Company object cannot be null!");

    return this.getCompany(company.getIdentifier());
  }

  @Override
  public void addCompany(Company company) {
    Validate.notNull(company, "Company object cannot be null!");

    this.companyUniqueIdMap.put(company.getIdentifier(), company);
    if (company.getName().isPresent()) {
      this.companyNameMap.put(company.getName().get(), company);
    }
  }

  @Override
  public void removeCompany(Company company) {
    Validate.notNull(company, "Company object cannot be null!");

    this.companyUniqueIdMap.remove(company.getIdentifier());
    this.companyNameMap.remove(company.getName().get());
  }

}
