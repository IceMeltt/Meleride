package pl.meleride.companies;

import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.companies.manager.UserManager;
import pl.meleride.companies.manager.impl.UserManagerImpl;

public final class MelerideCompaniesPlugin extends JavaPlugin implements MelerideCompanies {

  private UserManager userManager;

  @Override
  public void onLoad() {
    this.userManager = new UserManagerImpl();
  }

  @Override
  public void onEnable() {
    super.onEnable();
  }

  @Override
  public void onDisable() {
    super.onDisable();
  }

  @Override
  public UserManager getUserManager() {
    return this.userManager;
  }

}
