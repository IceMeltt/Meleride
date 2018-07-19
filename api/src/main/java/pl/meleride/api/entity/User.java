package pl.meleride.api.entity;

import java.util.Optional;
import java.util.UUID;

public interface User extends IdentifiableEntity<UUID> {

  Optional<String> getName();

  void setName(String name);

}
