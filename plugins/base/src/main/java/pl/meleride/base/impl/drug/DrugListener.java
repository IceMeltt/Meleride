package pl.meleride.base.impl.drug;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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

import pl.meleride.api.impl.i18n.MessageBundle;
import pl.meleride.api.impl.type.MessageType;

import pl.meleride.base.drug.Drug;
import pl.meleride.base.drug.DrugFactory;


public class DrugListener implements Listener {

  private final Map<UUID, Long> userUsing = new HashMap<>(); //TODO Zmiana do Usera, jak User zdobedzie mozliwosc laczenia z DB
  private final DrugShop drugShop = new DrugShop();

  @EventHandler
  public void onRightClick(PlayerInteractEvent e) {
    Player player = e.getPlayer();
    ItemStack itemStack = e.getItem();
    Drug drug;

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
      MessageBundle.create("base.haveToShift")
          .target(MessageType.CHAT)
          .sendTo(player);
      return;
    }

    if (System.currentTimeMillis() - userUsing.getOrDefault(player.getUniqueId(), 0L)
        >= 1000 * 60L) {
      userUsing.put(player.getUniqueId(), System.currentTimeMillis());
      for (PotionEffect effect : drug.getPotionEffects()) {
        player.addPotionEffect(effect);
      }
      player.getInventory().remove(drug.getItemStack());
      player.sendMessage(drug.getUsage());
      e.setCancelled(true);
    } else {
      MessageBundle.create("drugs.isUsing")
          .target(MessageType.CHAT)
          .sendTo(player);
    }
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent e) {
    Inventory inv = e.getClickedInventory();
    ItemStack itemStack = e.getCurrentItem();
    Player player = (Player) e.getWhoClicked();
    Optional<Drug> drug;

    if (!inv.getName().equals(drugShop.getInventory().getName())) {
      return;
    }

    if(itemStack == null || !itemStack.hasItemMeta()) {
      return;
    }

    drug = Optional.of(DrugFactory.getDrugByName(itemStack.getItemMeta().getDisplayName()));

    if (!itemStack.isSimilar(drug.get().getItemStack())) {
      return;
    }

    if (!e.isRightClick()) {
        MessageBundle.create("base.haveToPPM").target(MessageType.CHAT).sendTo(player);
        return;
    }
    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100.0F, 8.0F);
    player.getInventory().addItem(itemStack);
    MessageBundle.create("drugs.doneTrade")
        .withField("{DRUG}", drug.get().getItemStack().getItemMeta().getDisplayName())
        .target(MessageType.CHAT)
        .sendTo(player);

  }
}
