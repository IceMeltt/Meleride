package pl.meleride.shop.shop;

import static pl.meleride.api.message.MessageUtil.colored;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.inventivegames.hologram.Hologram;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.meleride.shop.MelerideShop;
import pl.meleride.api.hologram.HologramBuilder;
import pl.meleride.shop.hologram.handler.ShopTouchHandler;
import pl.meleride.shop.hologram.handler.ShopViewHandler;
import pl.meleride.shop.util.ItemUtil;

public class ShopManager {

  private final Map<Hologram, ShopItem> shopItemsMap = new HashMap<>();
  private final MelerideShop plugin;

  private String regionBasket, regionBuy, basketInventoryName;

  private ItemStack itemDecrease, itemIncrease;

  public ShopManager(final MelerideShop plugin) {
    this.plugin = plugin;
  }

  public void load() {
    final FileConfiguration configuration = this.plugin.getConfig();

    configuration.getConfigurationSection("shop-items").getKeys(false).stream()
        .map(id -> {
          final ConfigurationSection section = configuration.getConfigurationSection("shop-items")
              .getConfigurationSection(id);
          return new ShopItem(section);
        })
        .forEach(shopItem -> {

          final Hologram hologram = new HologramBuilder(shopItem.getHologramOptions()).build();
          hologram.addTouchHandler(new ShopTouchHandler(shopItem, this.plugin.getUserManager(),
              this.plugin.getShopManager()));
          hologram.setTouchable(true);
          hologram.addViewHandler(new ShopViewHandler(shopItem, this.plugin.getUserManager()));
          hologram.spawn();
          this.shopItemsMap.put(hologram, shopItem);

        });

    this.regionBasket = configuration.getString("regions.basket");
    this.regionBuy = configuration.getString("regions.buy");
    this.basketInventoryName = colored(configuration.getString("inventory.basket.name"));

    this.itemDecrease = ItemUtil
        .fromSection(configuration.getConfigurationSection("inventory.buy.items.decrease"));
    this.itemIncrease = ItemUtil
        .fromSection(configuration.getConfigurationSection("inventory.buy.items.increase"));
  }

  public ImmutableMap<Hologram, ShopItem> getShopItemsMap() {
    return ImmutableMap.copyOf(this.shopItemsMap);
  }

  public ImmutableList<ShopItem> getShopItemsList() {
    return ImmutableList.copyOf(this.shopItemsMap.values());
  }

  public ItemStack getItemDecrease() {
    return this.itemDecrease;
  }

  public ItemStack getItemIncrease() {
    return this.itemIncrease;
  }

  public String getRegionBasket() {
    return regionBasket;
  }

  public String getRegionBuy() {
    return regionBuy;
  }

  public String getBasketInventoryName() {
    return basketInventoryName;
  }

}
