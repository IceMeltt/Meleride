package pl.meleride.base.impl.drug;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.trait.Trait;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import pl.meleride.base.MelerideBase;

import static pl.meleride.api.message.MessageUtil.colored;

public class DrugTrait extends Trait {

  private final MelerideBase main = (MelerideBase) Bukkit.getPluginManager().getPlugin("MeleBase");

  public DrugTrait() {
    super("dealerTrait");
  }

  @EventHandler
  public void onClick(NPCRightClickEvent e) {
    Player player = e.getClicker();

    if (e.getNPC().equals(this.getNPC())) {
      if (!e.getNPC().getName().equalsIgnoreCase(colored("&aDealer"))) {
        return;
      }

      player.openInventory(main.getDrugShop().getInventory());
    }
  }

}
