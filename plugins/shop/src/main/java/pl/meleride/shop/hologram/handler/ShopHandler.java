package pl.meleride.shop.hologram.handler;

import pl.meleride.shop.shop.ShopItem;
import pl.meleride.shop.user.UserManager;

class ShopHandler {

  final ShopItem shopItem;
  final UserManager userManager;

  ShopHandler(final ShopItem shopItem, final UserManager userManager) {
    this.shopItem = shopItem;
    this.userManager = userManager;
  }

}
