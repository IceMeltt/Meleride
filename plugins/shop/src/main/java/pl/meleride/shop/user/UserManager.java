package pl.meleride.shop.user;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager {

  private final Map<UUID, ShopUser> usersMap = new HashMap<>();

  public ShopUser getOrCreateUser(final UUID uuid) {
    ShopUser user = this.usersMap.get(uuid);
    if (user == null) {
      this.usersMap.put(uuid, user = new ShopUser(uuid));
    }
    return user;
    //return this.usersMap.putIfAbsent(uuid, new ShopUser(uuid));
  }

}
