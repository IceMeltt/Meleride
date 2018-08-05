package pl.meleride.shop.hologram.handler;

import de.inventivegames.hologram.Hologram;
import de.inventivegames.hologram.touch.TouchAction;
import de.inventivegames.hologram.touch.TouchHandler;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.meleride.shop.InventoryBuilder;
import pl.meleride.shop.shop.ShopItem;
import pl.meleride.shop.shop.ShopManager;
import pl.meleride.shop.user.ShopUser;
import pl.meleride.shop.user.UserManager;

public class ShopTouchHandler extends ShopHandler implements TouchHandler {

  private final ShopManager shopManager;

  public ShopTouchHandler(final ShopItem shopItem,
      final UserManager userManager, final ShopManager shopManager) {
    super(shopItem, userManager);
    this.shopManager = shopManager;
  }

  @Override
  public void onTouch(final Hologram hologram, final Player player, final TouchAction touchAction) {
    final ShopUser user = this.userManager.getOrCreateUser(player.getUniqueId());

    final Inventory inventory = new InventoryBuilder()
        .setName(this.shopItem.getGuiOptions().getInventoryName())
        .setSize(9 * 5)
        .addItem(19, this.shopManager.getItemDecrease())
        .addItem(25, this.shopManager.getItemIncrease())
        .addItem(31, this.shopItem.makeInventoryItem(user))
        .build();

    player.openInventory(inventory);

    //numer slotow w inventory:
    //https://www.google.pl/search?q=inventory+slots+minecraft&source=lnms&tbm=isch&sa=X&ved=0ahUKEwiJ76vpjoDcAhWkiaYKHcO8BoQQ_AUICigB&biw=1366&bih=662#imgrc=LUCiJ6VBLtIF9M:
  }
}
