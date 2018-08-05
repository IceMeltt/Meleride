package pl.meleride.shop.shop;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.meleride.api.hologram.HologramOptions;
import pl.meleride.shop.user.ShopUser;
import pl.meleride.shop.util.ItemUtil;

public class ShopItem {

  private final String id;
  private final double cost;
  private final HologramOptions hologramOptions;
  private final GuiOptions guiOptions;

  private final Map<ShopUser, Integer> amountForUsers = new HashMap<>();

  public ShopItem(final String id, final double cost,
      final HologramOptions hologramOptions, final GuiOptions guiOptions) {
    this.id = id;
    this.cost = cost;
    this.hologramOptions = hologramOptions;
    this.guiOptions = guiOptions;
  }

  ShopItem(final ConfigurationSection section) {
    this.id = section.getName();
    this.cost = section.getDouble("cost-per-piece");
    this.hologramOptions = new HologramOptions(section.getConfigurationSection("hologram"));
    this.guiOptions = new GuiOptions(
        ItemUtil.fromSection(section.getConfigurationSection("inventory.item")),
        section.getString("inventory.name")
    );
  }

  public ItemStack makeInventoryItem(final ShopUser user) {
    final ItemStack item = this.guiOptions.getItem().clone();
    final ItemMeta meta = item.getItemMeta();
    if (meta.hasLore()) {
      meta.setLore(meta.getLore().stream()
          .map(entry -> {
            final int amount = this.getAmount(user);
            entry = StringUtils.replace(entry, "{AMOUNT}", String.valueOf(amount));
            entry = StringUtils
                .replace(entry, "{COST}", String.valueOf(amount * this.cost)); //cost for all pieces
            return entry;
          }).collect(Collectors.toList())
      );
    }
    item.setItemMeta(meta);
    return item;
  }

  public double getCost() {
    return cost;
  }

  public HologramOptions getHologramOptions() {
    return hologramOptions;
  }

  public GuiOptions getGuiOptions() {
    return guiOptions;
  }

  public String getId() {
    return id;
  }

  public void setAmount(final ShopUser user, final int amount) {
    this.amountForUsers.put(user, this.amountForUsers.getOrDefault(user, 0) + amount);
  }

  public int getAmount(final ShopUser user) {
    return this.amountForUsers.getOrDefault(user, 0);
  }

  public class GuiOptions {

    private final ItemStack item;
    private final String inventoryName;

    public GuiOptions(ItemStack item, String inventoryName) {
      this.item = item;
      this.inventoryName = inventoryName;
    }

    public ItemStack getItem() {
      return item;
    }

    public String getInventoryName() {
      return inventoryName;
    }
  }

}
