package pl.meleride.companies;

import org.bukkit.plugin.Plugin;
import pl.meleride.api.storage.Resource;
import pl.meleride.api.storage.sql.SqlStorage;
import pl.meleride.companies.entity.User;
import pl.meleride.companies.manager.UserManager;

public interface MelerideCompanies extends Plugin {

  UserManager getUserManager();

  Resource<User> getUserResource();

  SqlStorage getStorage();

}
