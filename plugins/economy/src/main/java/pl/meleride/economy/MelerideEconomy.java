package pl.meleride.economy;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import com.zaxxer.hikari.HikariConfig;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.api.PluginModule;
import pl.meleride.api.i18n.MessageBundler;
import pl.meleride.economy.manager.UserManager;
import pl.meleride.api.storage.Resource;
import pl.meleride.api.storage.sql.SqlStorage;
import pl.meleride.api.storage.sql.hikari.SqlHikariStorage;
import pl.meleride.commands.bukkit.BukkitCommands;
import pl.meleride.economy.command.CurrencyCommand;
import pl.meleride.economy.currency.Currency;
import pl.meleride.economy.currencysign.CurrencySignManager;
import pl.meleride.economy.listener.BlockBreakListener;
import pl.meleride.economy.listener.PlayerJoinListener;
import pl.meleride.economy.listener.PlayerPreLoginListener;
import pl.meleride.economy.listener.PlayerQuitListener;
import pl.meleride.economy.listener.SignChangeListener;
import pl.meleride.economy.manager.UserManagerImpl;
import pl.meleride.economy.placeholder.AccountBalancePlaceholder;
import pl.meleride.economy.resource.UserResourceImpl;
import pl.meleride.economy.runnable.CurrencyUpdaterRunnable;
import pl.meleride.economy.entity.User;

public class MelerideEconomy extends JavaPlugin implements PluginModule {

  private UserManager userManager;
  private Resource<User> userResource;
  private CurrencySignManager currencySignManager;
  private SqlStorage storage;

  @Override
  public void onEnable() {
    this.userManager = new UserManagerImpl();
    this.userResource = new UserResourceImpl(this);
    this.storage = new SqlHikariStorage(this.dataSourceConfiguration());

    this.userResource.checkTable();
    this.currencySignManager = new CurrencySignManager();

    new CurrencyUpdaterRunnable(this);

    BukkitCommands bukkitCommands = new BukkitCommands(this);

    bukkitCommands.registerCommandObjects(new CurrencyCommand(this));

    this.registerListeners(
            new SignChangeListener(this),
            new PlayerJoinListener(this),
            new PlayerPreLoginListener(this),
            new PlayerQuitListener(this),
            new BlockBreakListener(this)
    );

    this.registerPlaceholders();
  }

  @Override
  public void onDisable() {
    this.getServer().getOnlinePlayers().stream()
        .map(user -> this.getUserManager().getUser(user).get())
        .forEach(user -> this.getUserResource().save(user));
  }

  private void registerListeners(Listener... listeners) {
    PluginManager pluginManager = Bukkit.getPluginManager();
    for (Listener listener : listeners) {
      pluginManager.registerEvents(listener, this);
    }
  }

  private void registerPlaceholders() {
    for (Currency currency : Currency.values()) {
      PlaceholderAPI.registerPlaceholder(
              this,
              "balance-" + currency.name().toLowerCase(),
              new AccountBalancePlaceholder(this, currency)
      );
    }
  }

  private HikariConfig dataSourceConfiguration() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(MessageBundler.create("database.jdbc").toString());

    return config;
  }

  @Override
  public UserManager getUserManager() {
    return this.userManager;
  }

  @Override
  public SqlStorage getStorage() {
    return this.storage;
  }

  public CurrencySignManager getCurrencySignManager() {
    return this.currencySignManager;
  }

  public Resource<User> getUserResource() {
    return this.userResource;
  }

}
