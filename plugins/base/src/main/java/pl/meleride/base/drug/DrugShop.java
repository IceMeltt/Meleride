package pl.meleride.base.drug;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import pl.meleride.api.object.system.ItemRegistrator;

import static pl.meleride.api.message.MessageUtil.colored;

public class DrugShop {

  private Inventory inventory;

  public void initialize() {
    inventory = Bukkit.createInventory(null, 6 * 9, colored("&2Dealer narkotykow"));
    inventory.setItem(37, ItemRegistrator.getObjectMap().get("drug_cannabis").getItemStack());
    inventory.setItem(39, ItemRegistrator.getObjectMap().get("drug_mdma").getItemStack());
    inventory.setItem(41, ItemRegistrator.getObjectMap().get("drug_cocaine").getItemStack());
    inventory.setItem(43, ItemRegistrator.getObjectMap().get("drug_heroine").getItemStack());
  }

  public Inventory getInventory() {
    if(inventory == null) {
      initialize();
    }
    return inventory;
  }

}
