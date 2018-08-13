package pl.meleride.companies.entity;

import java.util.Optional;

public interface User extends pl.meleride.api.entity.User {

  Optional<Company> getCompany();

  void setCompany(Company company);

}
