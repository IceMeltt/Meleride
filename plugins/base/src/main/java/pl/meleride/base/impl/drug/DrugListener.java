package pl.meleride.base.impl.drug;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import pl.meleride.api.i18n.MessageBundler;
import pl.meleride.api.message.MessageType;

import pl.meleride.base.drug.DrugFactory;
import pl.meleride.base.drug.DrugPackager;

public class DrugListener implements Listener {

  private final Map<UUID, Long> userUsing = new HashMap<>(); //TODO Zmiana do Usera, jak User zdobedzie mozliwosc laczenia z DB
  @Inject private DrugShop drugShop;

  @EventHandler
  public void onRightClick(PlayerInteractEvent e) {
    Player player = e.getPlayer();
    ItemStack itemStack = e.getItem();
    DrugPackager drug;

    if (!(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
      return;
    }

    if(itemStack == null || !itemStack.hasItemMeta()) {
      return;
    }

    drug = DrugFactory.getDrugByName(itemStack.getItemMeta().getDisplayName());

    if (!player.getInventory().getItemInMainHand().isSimilar(drug.getItemStack())) {
      return;
    }

    if (!player.isSneaking()) {
      MessageBundler.create("base.haveToShift")
          .target(MessageType.CHAT)
          .sendTo(player);
      return;
    }

    if (System.currentTimeMillis() - this.userUsing.getOrDefault(player.getUniqueId(), 0L)
        >= 1000 * 60L) {
      this.userUsing.put(player.getUniqueId(), System.currentTimeMillis());
      for (PotionEffect effect : drug.getPotionEffects()) {
        player.addPotionEffect(effect);
      }
      player.getInventory().remove(drug.getItemStack());
      player.sendMessage(drug.getUsage());
      e.setCancelled(true);
    } else {
      MessageBundler.create("drugs.isUsing")
          .target(MessageType.CHAT)
          .sendTo(player);
    }
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent e) {
    Inventory inv = e.getClickedInventory();
    ItemStack itemStack = e.getCurrentItem();
    Player player = (Player) e.getWhoClicked();
    DrugPackager drug;

    if (!inv.getName().equals(this.drugShop.getInventory().getName())) {
      return;
    }

    if(itemStack == null || !itemStack.hasItemMeta()) {
      return;
    }

    drug = DrugFactory.getDrugByName(itemStack.getItemMeta().getDisplayName());

    if (!itemStack.isSimilar(drug.getItemStack())) {
      return;
    }

    if (!e.isRightClick()) {
        MessageBundler.create("base.haveToPPM").target(MessageType.CHAT).sendTo(player);
        return;
    }
    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100.0F, 8.0F);
    player.getInventory().addItem(itemStack);
    MessageBundler.create("drugs.doneTrade")
        .withField("{DRUG}", drug.getItemStack().getItemMeta().getDisplayName())
        .target(MessageType.CHAT)
        .sendTo(player);

  }

}
