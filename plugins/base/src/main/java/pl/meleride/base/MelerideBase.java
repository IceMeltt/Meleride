package pl.meleride.base;

import com.zaxxer.hikari.HikariConfig;
import java.util.Objects;
import java.util.Optional;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.TraitInfo;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.api.PluginModule;
import pl.meleride.api.helper.Listener;
import pl.meleride.api.i18n.MessageBundler;
import pl.meleride.api.manager.UserManager;
import pl.meleride.api.object.system.ItemRegistrator;
import pl.meleride.api.storage.Resource;
import pl.meleride.api.storage.sql.SqlStorage;
import pl.meleride.api.storage.sql.hikari.SqlHikariStorage;
import pl.meleride.base.drug.DrugShop;
import pl.meleride.base.drug.DrugTrait;
import pl.meleride.base.drug.item.Cannabis;
import pl.meleride.base.drug.item.Cocaine;
import pl.meleride.base.drug.item.Heroine;
import pl.meleride.base.drug.item.MDMA;
import pl.meleride.base.entity.User;
import pl.meleride.base.listener.PlayerJoinListener;
import pl.meleride.base.listener.PlayerPreLoginListener;
import pl.meleride.base.listener.PlayerQuitListener;
import pl.meleride.base.manager.UserManagerImpl;
import pl.meleride.base.resource.UserResourceImpl;

public class MelerideBase extends JavaPlugin implements PluginModule {

  private UserManager<User> userManager;
  private Resource<User> userResource;
  private SqlStorage storage;
  private DrugShop drugShop;

  @Override
  public void onEnable() {
    this.userManager = new UserManagerImpl();
    this.userResource = new UserResourceImpl(this);
    this.storage = new SqlHikariStorage(this.dataSourceConfiguration());

    this.userResource.checkTable();
    this.drugShop = new DrugShop();

    this.registerListeners(
        new PlayerPreLoginListener(this),
        new PlayerJoinListener(this),
        new PlayerQuitListener(this)
    );

    ItemRegistrator.register(
        new MDMA(this),
        new Cannabis(this),
        new Heroine(this),
        new Cocaine(this)
    );

    CitizensAPI.getTraitFactory()
        .registerTrait(TraitInfo
            .create(DrugTrait.class)
            .withName("dealerTrait"));

    this.saveDefaultConfig();
    this.getConfig().options().copyDefaults(true);
  }

  @Override
  public void onDisable() {
    this.getServer().getOnlinePlayers().stream()
        .map(user -> this.getUserManager().getUser(user))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .filter(Objects::nonNull)
        .forEach(user -> this.getUserResource().save(user));
  }

  private void registerListeners(Listener<?>... listeners) {
    for (Listener<?> listener : listeners) {
      this.getServer().getPluginManager().registerEvents(listener, this);
    }
  }

  private HikariConfig dataSourceConfiguration() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(MessageBundler.create("database.jdbc").toString());

    return config;
  }

  @Override
  public UserManager<User> getUserManager() {
    return this.userManager;
  }

  @Override
  public SqlStorage getStorage() {
    return this.storage;
  }

  public Resource<User> getUserResource() {
    return this.userResource;
  }

  public DrugShop getDrugShop() {
    return this.drugShop;
  }

}
