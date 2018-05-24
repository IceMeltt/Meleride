package pl.meleride.base.impl.drug.builders;

import org.bukkit.inventory.ItemStack;


public class Drug {

  private DrugConfigurator drugConfig;
  private ItemStack itemStack;

  public void setDrugConfig(DrugConfigurator drugConfig) {
    this.drugConfig = drugConfig;
  }

  public DrugConfigurator getDrugConfig() {
    return drugConfig;
  }

  public void setItemStack(ItemStack itemStack) {
    this.itemStack = itemStack;
  }

  public ItemStack getItemStack() {
    return itemStack;
  }
}
