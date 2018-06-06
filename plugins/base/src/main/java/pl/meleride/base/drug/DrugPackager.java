package pl.meleride.base.drug;

import java.util.Set;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class DrugPackager {

  private String usage;
  private ItemStack itemStack;
  private int price;

  private Set<PotionEffect> potionEffects;

  public DrugPackager(String usage, ItemStack itemStack, Set<PotionEffect> potionEffects, int price) {
    this.usage = usage;
    this.itemStack = itemStack;
    this.price = price;
    this.potionEffects = potionEffects;
  }

  public String getUsage() {
    return usage;
  }

  public ItemStack getItemStack() {
    return itemStack;
  }

  public int getPrice() {
    return price;
  }

  public Set<PotionEffect> getPotionEffects() {
    return potionEffects;
  }

}
