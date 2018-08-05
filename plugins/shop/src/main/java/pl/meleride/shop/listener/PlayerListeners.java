package pl.meleride.shop.listener;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.meleride.shop.InventoryBuilder;
import pl.meleride.shop.MelerideShop;
import pl.meleride.shop.shop.ShopItem;
import pl.meleride.shop.shop.ShopManager;
import pl.meleride.shop.user.ShopUser;
import pl.meleride.shop.user.UserManager;

public class PlayerListeners implements Listener {

  private final ShopManager shopManager;
  private final UserManager userManager;

  public PlayerListeners(final ShopManager shopManager, final UserManager userManager) {
    this.shopManager = shopManager;
    this.userManager = userManager;
  }

  @EventHandler
  public void onClick(final InventoryClickEvent event) {
    if (!(event.getWhoClicked() instanceof Player) || event.getInventory() == null
        || event.getCurrentItem() == null) {
      return;
    }
    final Player player = ((Player) event.getWhoClicked());
    final ShopUser user = this.userManager.getOrCreateUser(player.getUniqueId());

    final Consumer<ShopItem> shopItemConsumer = (shopItem) -> {
      if (event.getCurrentItem().isSimilar(this.shopManager.getItemDecrease())) {
        final int updatedAmount = shopItem.getAmount(user) + 1;
        shopItem.setAmount(user, updatedAmount);
        player.sendMessage(
            "Zwiekszyles ilosc przedmiotu w sklepie (id: " + shopItem.getId() + ") do: "
                + updatedAmount);

      } else if (event.getCurrentItem().isSimilar(this.shopManager.getItemIncrease())) {
        final int updatedAmount = shopItem.getAmount(user) - 1;

        if (updatedAmount >= 1) {
          shopItem.setAmount(user, updatedAmount);
          player.sendMessage(
              "Zmniejszyles ilosc przedmiotu w sklepie (id: " + shopItem.getId() + ") do: "
                  + updatedAmount);
        } else {

          if (updatedAmount == 0) {
            player.sendMessage("Wyrzuciles ten item z koszyka!");
          } else {
            player.sendMessage("Ilosc przedmiotu nie moze byc mniejsza niz zero!");
          }

        }
      }
      event.getInventory().setItem(31, shopItem.makeInventoryItem(user));
    };

    this.shopManager.getShopItemsList().stream()
        .filter(shopItem -> shopItem.getGuiOptions().getInventoryName()
            .equalsIgnoreCase(event.getInventory().getTitle()) && shopItem.getAmount(user) > 0)
        .findFirst().ifPresent(shopItemConsumer);
  }

  @EventHandler
  public void onSwapHand(PlayerSwapHandItemsEvent event) {
    event.setCancelled(true);

    final Player player = event.getPlayer();
    final ShopUser user = this.userManager.getOrCreateUser(player.getUniqueId());

    Bukkit.getScheduler().runTask(MelerideShop.getPlugin(MelerideShop.class), () -> {

      final ApplicableRegionSet set = WGBukkit.getRegionManager(player.getWorld())
          .getApplicableRegions(player.getLocation());

      if (!set.getRegions().stream()
          .filter(region -> region.getId().endsWith(this.shopManager.getRegionBasket()))
          .collect(Collectors.toList()).isEmpty()) {

        final Map<Integer, ItemStack> itemsInBasket = new HashMap<>();

        this.shopManager.getShopItemsList()
            .stream()
            .filter(shopItem -> shopItem.getAmount(user) > 0)
            .forEach(shopItem -> itemsInBasket
                .put(itemsInBasket.size(), shopItem.makeInventoryItem(user)));

        final Inventory basket = new InventoryBuilder()
            .setName(this.shopManager.getBasketInventoryName())
            .setSize(9 * 5)
            .addItems(itemsInBasket)
            .build();

        player.openInventory(basket);
      }
    });
  }

  @EventHandler(ignoreCancelled = true)
  public void onMove(PlayerMoveEvent event) {
    if (event.getFrom().getX() == event.getTo().getX()
        || event.getFrom().getY() == event.getTo().getY()
        || event.getFrom().getZ() == event.getTo().getZ()) {
      return;
    }
    final Player player = event.getPlayer();
    final ShopUser user = this.userManager.getOrCreateUser(player.getUniqueId());

    Bukkit.getScheduler().runTask(MelerideShop.getPlugin(MelerideShop.class), () -> {

      final ApplicableRegionSet set = WGBukkit.getRegionManager(player.getWorld())
          .getApplicableRegions(player.getLocation());

      if (!set.getRegions().stream()
          .filter(region -> region.getId().endsWith(this.shopManager.getRegionBuy()))
          .collect(Collectors.toList()).isEmpty()) {
        final List<ShopItem> itemsInBasket = this.shopManager.getShopItemsList().stream()
            .filter(shopItem -> shopItem.getAmount(user) > 0)
            .collect(Collectors.toList());
        for (ShopItem shopItem : itemsInBasket) {
          double playerBalance = 1000;
          final int pieces = shopItem.getAmount(user);
          final double cost = pieces * shopItem.getCost();
          if (playerBalance < cost) {
            player.sendMessage(
                "Nie masz " + cost + " pieniedzy do zakupu itemu " + shopItem.getId() + " (x "
                    + pieces + ")");
            continue;
          }
          playerBalance -= cost;
          player.getInventory()
              .addItem(new ItemStack(shopItem.getGuiOptions().getItem().getType(), pieces));
          player.sendMessage(
              "Zakupiles przedmiot " + shopItem.getId() + " x: " + pieces + ", za: " + cost
                  + ", aktualny "
                  + "stan konta: " + playerBalance);
        }
      }
    });
  }

  /*  TODO:
    - eventy od wchodzenia i wychodzenia z regionu (lub to czyms zastapic,
    bo w onMove sprawdzanie ostatniego regionu i tak dalej nie ma sensu - najlepiej w onMove sprawdzac czy gracz
    wszedl na jakis blok (ktory nie bedzie dostepny dla normalnego gracza) - i wtedy cos robi)

    - mialo byc podczas "zmieniania reki" otwieranie koszyka, ale zaden event nie oferuje sprawdzenia na ktora reke
    zmienia gracz podczas eventu oraz nie wiem czy zcancelowanie eventu daloby efekt taki jaki chcemy =/

   */

}
