package pl.meleride.economy.user;

import com.google.common.base.Charsets;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.meleride.api.helper.MathHelper;
import pl.meleride.economy.currency.Currency;

public class EconomyUserImpl implements EconomyUser {

  private final UUID uniqueId;
  private String name;
  private byte dataError;

  private final Map<Currency, Double> pocketBalance;
  public static final DateTimeFormatter TIME_PATTERN = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

  // [||] -------------------------------- [||]

  public EconomyUserImpl(String name) {
    this.name = name;
    this.uniqueId = UUID.nameUUIDFromBytes(("OfflinePlayer:" + this.name).getBytes(Charsets.UTF_8));
    this.pocketBalance = new HashMap<>();

    add(Currency.PLN, 0.0);
  }

  public EconomyUserImpl(UUID uniqueId) {
    this.uniqueId = uniqueId;
    this.name = Bukkit.getOfflinePlayer(this.uniqueId).getName();
    this.pocketBalance = new HashMap<>();

    add(Currency.PLN, 0.0);
  }

  public EconomyUserImpl(Player player) {
    this.uniqueId = player.getUniqueId();
    this.name = player.getName();
    this.pocketBalance = new HashMap<>();

    add(Currency.PLN, 0.0);
  }

  // [||] -------------------------------- [||]

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public UUID getUniqueId() {
    return this.uniqueId;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public Player getBukkitPlayer() {
    return Bukkit.getPlayer(this.uniqueId);
  }

  @Override
  public byte getDataErrorStatus() {
    return this.dataError;
  }

  @Override
  public void setDataErrorStatus(byte dataError) {
    this.dataError = dataError;
  }

  // [||] -------------------------------- [||]

  @Override
  public Map<Currency, Double> getPocketBalance() {
    return new HashMap<>(this.pocketBalance);
  }

  @Override
  public double getCurrencyBalance(Currency currency) {
    return this.pocketBalance.getOrDefault(currency, 0.0);
  }

  @Override
  public boolean has(Currency currency, double amount) {
    return pocketBalance.getOrDefault(currency, 0.0) >= amount;
  }

  @Override
  public boolean exchange(Currency from, Currency to, double amount) {
    if (this.pocketBalance.get(from) == null || this.pocketBalance.get(from) == 0.0) {
      return false;
    }

    if (amount <= 0 || amount > this.pocketBalance.get(from)) {
      return false;
    }

    double result = (amount * from.getExchangeRate()) / to.getExchangeRate();
    double newToBalance = MathHelper.round(this.pocketBalance.getOrDefault(to, 0.0) + result, 2);

    this.pocketBalance.put(from, this.pocketBalance.get(from) - amount);
    this.pocketBalance.put(to, newToBalance);

    return true;
  }

  @Override
  public boolean charge(Currency currency, double amount) {
    if (!has(currency, amount)) {
      return false;
    }

    this.pocketBalance.put(currency, this.pocketBalance.get(currency) - amount);
    return true;
  }

  @Override
  public void add(Currency currency, double amount) {
    this.pocketBalance.put(
        currency,
        this.pocketBalance.getOrDefault(currency, 0.0) + amount
    );
  }

}
