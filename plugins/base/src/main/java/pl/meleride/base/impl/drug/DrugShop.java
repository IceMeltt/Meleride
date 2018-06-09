package pl.meleride.base.impl.drug;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import pl.meleride.base.drug.DrugFactory;
import static pl.meleride.api.message.MessageUtil.colored;

public class DrugShop {

  private Inventory inventory;

  public void initialize() {
    inventory = Bukkit.createInventory(null, 6 * 9, colored("&2Dealer narkotykow"));
    inventory.setItem(37, DrugFactory.getDrugByName("§2Marihuana").getItemStack());
    inventory.setItem(39, DrugFactory.getDrugByName("§bEcstazy").getItemStack());
    inventory.setItem(41, DrugFactory.getDrugByName("Kokaina").getItemStack());
    inventory.setItem(43, DrugFactory.getDrugByName("Heroina").getItemStack());
  }

  public Inventory getInventory() {
    if(inventory == null) {
      initialize();
    }
    return inventory;
  }

}
