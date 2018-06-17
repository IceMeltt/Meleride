package pl.meleride.base.impl.drug;

import java.util.Set;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import pl.meleride.api.i18n.MessageBundler;
import pl.meleride.api.message.MessageType;
import pl.meleride.base.MelerideBase;
import pl.meleride.api.object.system.AbstractItem;

public class DrugListener {

  private MelerideBase instance = JavaPlugin.getPlugin(MelerideBase.class);

  public void invoke(Player player, AbstractItem drug, Set<PotionEffect> effects, String usage, PlayerInteractEvent e) {
    ItemStack itemStack = e.getItem();

    if (!(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
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

    if (System.currentTimeMillis() - this.instance.getUserUsing().getOrDefault(player.getUniqueId(), 0L) >= 1000 * 60L) {
      this.instance.getUserUsing().put(player.getUniqueId(), System.currentTimeMillis());

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
