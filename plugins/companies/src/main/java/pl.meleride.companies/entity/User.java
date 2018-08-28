package pl.meleride.companies.entity;

import java.util.Optional;
import pl.meleride.companies.enums.MakeStatus;

public interface User extends pl.meleride.api.entity.User {

  Optional<Company> getCompany();

  void setCompany(Company company);

  MakeStatus getMakingStatus();

  void setMakingStatus(MakeStatus status);

}
