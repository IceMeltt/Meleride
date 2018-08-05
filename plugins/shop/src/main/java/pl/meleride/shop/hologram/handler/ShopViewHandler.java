package pl.meleride.shop.hologram.handler;

import de.inventivegames.hologram.Hologram;
import de.inventivegames.hologram.view.ViewHandler;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import pl.meleride.shop.shop.ShopItem;
import pl.meleride.shop.user.ShopUser;
import pl.meleride.shop.user.UserManager;

public class ShopViewHandler extends ShopHandler implements ViewHandler {

  public ShopViewHandler(final ShopItem shopItem, final UserManager userManager) {
    super(shopItem, userManager);
  }

  @Override
  public String onView(final Hologram hologram, final Player player, String line) {

    final ShopUser user = this.userManager.getOrCreateUser(player.getUniqueId());

    final int amount = this.shopItem.getAmount(user);
    final double cost = this.shopItem.getCost();

    line = StringUtils.replace(line, "{AMOUNT}", String.valueOf(amount));
    line = StringUtils.replace(line, "{COST}", String.valueOf(amount * cost));
    return line;
  }

}
