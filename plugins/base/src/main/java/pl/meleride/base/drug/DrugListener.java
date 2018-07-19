package pl.meleride.base.drug;

import java.util.Set;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import pl.meleride.api.i18n.MessageBundler;
import pl.meleride.api.message.MessageType;
import pl.meleride.base.MelerideBase;
import pl.meleride.api.object.system.AbstractItem;
import pl.meleride.base.entity.User;

public class DrugListener {

  private final MelerideBase plugin;

  public DrugListener(MelerideBase plugin) {
    this.plugin = plugin;
  }

  public void invoke(Player player, AbstractItem drug, Set<PotionEffect> effects, String usage,
      PlayerInteractEvent e) {
    ItemStack itemStack = e.getItem();

    if (!(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction()
        .equals(Action.RIGHT_CLICK_BLOCK))) {
      return;
    }

    if (itemStack == null || !itemStack.hasItemMeta()) {
      return;
    }

    if (!player.getInventory().getItemInMainHand().isSimilar(drug.getItemStack())) {
      return;
    }

    if (!player.isSneaking()) {
      MessageBundler.create("base.haveToShift")
          .withField("PLAYER", player.getName())
          .target(MessageType.CHAT)
          .sendTo(player);
      return;
    }

    User user = this.plugin.getUserManager().getUser(player).get();
    if (System.currentTimeMillis() - user.getDrugCooldown() >= 1000 * 60L) {
      user.setDrugCooldown(System.currentTimeMillis());

      for (PotionEffect effect : effects) {
        player.addPotionEffect(effect);
      }

      player.getInventory().remove(drug.getItemStack());
      player.sendMessage(usage);
      e.setCancelled(true);
    } else {

      MessageBundler.create("drugs.isUsing")
          .withField("PLAYER", player.getName())
          .target(MessageType.CHAT)
          .sendTo(player);
    }
  }

  public void invoke(Player player, AbstractItem drug, DrugShop shop, InventoryClickEvent e) {
    Inventory inv = e.getClickedInventory();
    ItemStack itemStack = e.getCurrentItem();

    if (!inv.getName().equals(shop.getInventory().getName())) {
      return;
    }

    if (itemStack == null || !itemStack.hasItemMeta()) {
      return;
    }

    if (!itemStack.isSimilar(drug.getItemStack())) {
      return;
    }

    if (!e.isRightClick()) {
      MessageBundler.create("base.haveToPPM")
          .target(MessageType.CHAT)
          .sendTo(player);
      return;
    }
    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100.0F, 8.0F);
    player.getInventory().addItem(itemStack);

    MessageBundler.create("drugs.doneTrade")
        .withField("DRUG", drug.getItemStack().getItemMeta().getDisplayName())
        .target(MessageType.CHAT)
        .sendTo(player);
  }

}
