package pl.meleride.base.impl.drug;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.trait.Trait;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import static pl.meleride.api.impl.util.MessageUtil.*;

/*
 * Meleride (c) 2017-present
 * All Rights Reserved.
 * Don't even think about stealing the code ;).
 */
public class DrugTrait extends Trait {

  private DrugShop shop = new DrugShop();

  public DrugTrait() {
    super("dealerTrait");
  }

  @EventHandler
  public void onClick(NPCRightClickEvent e) {
    Player player = e.getClicker();

    if (e.getNPC() == this.getNPC()) {
      if (!e.getNPC().getName().equalsIgnoreCase(colored("&aDealer"))) {
        return;
      }

      player.openInventory(shop.getInventory());
    }
  }
}