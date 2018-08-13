package pl.meleride.companies.entity;

import java.util.List;
import java.util.UUID;
import pl.meleride.api.entity.IdentifiableEntity;

public interface Company extends IdentifiableEntity<UUID> {

  String getName();

  User getOwner();

  List<User> getWorkers();

  int getLevel();

  void setName(String name);

  void setOwner(User owner);

  void setWorkers(List<User> workers);

  void setLevel(int level);

}
