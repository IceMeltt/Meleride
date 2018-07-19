package pl.meleride.user.entity;

import java.util.Set;
import pl.meleride.user.disease.Disease;

public interface User extends pl.meleride.api.entity.User {

  Set<Disease> getDiseases();

  boolean hasDisease(Disease disease);

  void addDisease(Disease disease);

  void clearDiseases();

}