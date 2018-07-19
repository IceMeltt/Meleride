package pl.meleride.user.manager;

import pl.meleride.api.manager.AbstractUserManager;
import pl.meleride.user.disease.Disease;
import pl.meleride.user.entity.User;

public class UserManagerImpl extends AbstractUserManager<User> implements UserManager {

  @Override
  public boolean hasDisease(User user, Disease disease) {
    return user.getDiseases().contains(disease);
  }

  @Override
  public void addDisease(User user, Disease disease) {
    user.getDiseases().add(disease);
  }

  @Override
  public void clearDiseases(User user) {
    user.getDiseases().clear();
  }

}
