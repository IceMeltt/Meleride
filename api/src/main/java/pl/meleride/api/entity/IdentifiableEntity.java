package pl.meleride.api.entity;

import java.io.Serializable;

public interface IdentifiableEntity<T extends Serializable> {

  T getIdentifier();

  void setIdentifier(T identifier);

}
