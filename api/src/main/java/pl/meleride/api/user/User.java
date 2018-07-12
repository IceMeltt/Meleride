package pl.meleride.api.user;

import java.util.Set;

import pl.meleride.api.flexible.PlayableUser;
import pl.meleride.api.user.status.DiseaseStatus;

public interface User extends PlayableUser {

  // [ BASE ]
  void setName(String name);

  // [ DISEASES ]
  boolean hasAnyDisease();

  boolean hasDisease(DiseaseStatus diseaseStatus);

  Set<DiseaseStatus> getDiseases();

  void addDisease(DiseaseStatus disease);

  void removeDisease(DiseaseStatus disease);

}
