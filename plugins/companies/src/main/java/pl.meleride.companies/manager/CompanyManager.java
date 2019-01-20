package pl.meleride.companies.manager;

import com.google.common.collect.ImmutableSet;
import java.util.Optional;
import java.util.UUID;
import pl.meleride.companies.entity.Company;
import pl.meleride.companies.entity.User;

public interface CompanyManager {

  Optional<Company> getCompany(String name);

  Optional<Company> getCompany(UUID uniqueId);

  Optional<Company> getCompany(Company company);

  Optional<Company> getCompany(User owner);

  void addCompany(Company company);

  void removeCompany(Company company);

  ImmutableSet<Company> getAllCompanies();

}
