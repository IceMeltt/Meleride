package pl.meleride.companies.manager;

import java.util.Optional;
import java.util.UUID;
import pl.meleride.companies.entity.Company;

public interface CompanyManager {

  Optional<Company> getCompany(String name);

  Optional<Company> getCompany(UUID uniqueId);

  Optional<Company> getCompany(Company company);

  void addCompany(Company company);

  void removeCompany(Company company);

}
