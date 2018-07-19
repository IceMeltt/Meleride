package pl.meleride.user.manager;

import pl.meleride.user.disease.Disease;
import pl.meleride.user.entity.User;

public interface UserManager extends pl.meleride.api.manager.UserManager<User> {

  boolean hasDisease(User user, Disease disease);

  void addDisease(User user, Disease disease);

  void clearDiseases(User user);

}
