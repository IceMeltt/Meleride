package pl.meleride.economy.entity.impl;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.economy.MelerideEconomy;
import pl.meleride.economy.currency.Currency;
import pl.meleride.economy.entity.User;

public class UserImpl implements User {

  private final MelerideEconomy plugin = JavaPlugin.getPlugin(MelerideEconomy.class);

  private UUID identifier;
  private String name;

  private final Map<Currency, Double> pocketBalance = new HashMap<>();

  public UserImpl(String name) {
    this.name = name;
    this.identifier = UUID.nameUUIDFromBytes(("OfflinePlayer:" + this.name).getBytes(StandardCharsets.UTF_8));
  }

  public UserImpl(UUID identifier) {
    this.identifier = identifier;
    this.name = Bukkit.getOfflinePlayer(this.identifier).getName();
  }

  public UserImpl(Player player) {
    this.identifier = player.getUniqueId();
    this.name = player.getName();
  }

  @Override
  public UUID getIdentifier() {
    return this.identifier;
  }

  @Override
  public Optional<String> getName() {
    return Optional.ofNullable(this.name);
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public Map<Currency, Double> getPocketBalance() {
    return this.pocketBalance;
  }

  @Override
  public double getCurrencyBalance(Currency currency) {
    return this.pocketBalance.getOrDefault(currency, 0.0);
  }

}
