package pl.meleride.economy;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import java.util.Properties;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.api.flexible.BaseManager;
import pl.meleride.api.i18n.MessageBundler;
import pl.meleride.api.storage.StorageException;
import pl.meleride.api.storage.dao.StorageDao;
import pl.meleride.api.storage.sql.hikari.SQLHikariStorage;
import pl.meleride.api.storage.userflow.FlowInspector;
import pl.meleride.api.user.accident.BaseAccidentor;
import pl.meleride.commands.bukkit.BukkitCommands;
import pl.meleride.economy.command.CurrencyCommand;
import pl.meleride.economy.currency.Currency;
import pl.meleride.economy.currencysign.CurrencySignManager;
import pl.meleride.economy.listener.BlockBreakListener;
import pl.meleride.economy.listener.PlayerLoginListener;
import pl.meleride.economy.listener.PlayerQuitListener;
import pl.meleride.economy.listener.SignChangeListener;
import pl.meleride.economy.placeholder.AccountBalancePlaceholder;
import pl.meleride.economy.runnable.CurrencyUpdaterRunnable;
import pl.meleride.economy.user.EconomyUser;
import pl.meleride.economy.user.EconomyUserAccidentor;
import pl.meleride.economy.user.EconomyUserDao;
import pl.meleride.economy.user.EconomyUserInspector;
import pl.meleride.economy.user.EconomyUserManager;

public class MelerideEconomy extends JavaPlugin {

  private SQLHikariStorage storage;
  private StorageDao<EconomyUser> dao;
  private BaseAccidentor accidentor;
  private BaseManager<EconomyUser> manager;
  private FlowInspector<EconomyUser> inspector;
  private CurrencySignManager currencySignManager;

  @Override
  public void onEnable() {
    this.initializeDatabase();

    dao = new EconomyUserDao(this);
    accidentor = new EconomyUserAccidentor(this);
    manager = new EconomyUserManager();
    inspector = new EconomyUserInspector(this);
    currencySignManager = new CurrencySignManager();

    new CurrencyUpdaterRunnable(this);

    BukkitCommands bukkitCommands = new BukkitCommands(this);

    bukkitCommands.registerCommandObjects(new CurrencyCommand(this));

    this.registerListeners(
            new SignChangeListener(this),
            new PlayerLoginListener(this),
            new PlayerQuitListener(this),
            new BlockBreakListener(this)
    );

    this.registerPlaceholders();
  }

  public CurrencySignManager getCurrencySignManager() {
    return this.currencySignManager;
  }

  private void initializeDatabase() {
    Properties properties = new Properties();
    properties.put("jdbcUrl", MessageBundler.create("database.jdbc").toString());
    storage = new SQLHikariStorage(properties);

    StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS economy(")
        .append("uuid CHAR(36) NOT NULL,")
        .append("name TEXT(16) NOT NULL,")
        .append("pln DOUBLE NOT NULL,")
        .append("eur DOUBLE NOT NULL,")
        .append("usd DOUBLE NOT NULL,")
        .append("czk DOUBLE NOT NULL,")
        .append("jpy DOUBLE NOT NULL,")
        .append("dataError TINYINT(1) NOT NULL,")
        .append("primary key(uuid));");

    try {
      this.storage.update(sb.toString());
    } catch(StorageException e) {
      this.getLogger().severe("Wystąpił BARDZO POTĘŻNY błąd w tworzeniu tabeli!!1");
      e.printStackTrace();
    }
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

  public SQLHikariStorage getStorage() {
    return storage;
  }

  public StorageDao<EconomyUser> getEconomyDao() {
    return dao;
  }

  public BaseAccidentor getAccidentor() {
    return accidentor;
  }

  public BaseManager<EconomyUser> getManager() {
    return manager;
  }

  public FlowInspector<EconomyUser> getInspector() {
    return inspector;
  }

}
