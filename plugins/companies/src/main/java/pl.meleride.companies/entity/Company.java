package pl.meleride.companies.entity;

import java.util.List;
import java.util.UUID;
import pl.meleride.api.entity.IdentifiableEntity;

public interface Company extends IdentifiableEntity<UUID> {

  String getName();

  User getOwner();

  List<String> getWorkers();

  int getLevel();

  void setName(String name);

  void setOwner(User owner);

  void setWorkers(List<String> workers);

  void setLevel(int level);

}
