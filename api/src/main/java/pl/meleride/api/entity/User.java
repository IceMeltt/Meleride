package pl.meleride.api.entity;

import java.util.UUID;

public interface User extends IdentifiableEntity<UUID> {

  String getName();

  void setName(String name);

}
