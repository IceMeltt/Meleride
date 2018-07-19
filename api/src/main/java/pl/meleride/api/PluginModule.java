package pl.meleride.api;

import pl.meleride.api.manager.UserManager;
import pl.meleride.api.storage.sql.SqlStorage;

public interface PluginModule {

  UserManager<?> getUserManager();

  SqlStorage getStorage();

}
