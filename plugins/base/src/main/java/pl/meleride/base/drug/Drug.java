package pl.meleride.base.drug;

import java.util.Set;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import static pl.meleride.api.impl.util.MessageUtil.*;


public class Drug {

  private String name;
  private int prize;
  private String usage;
  private ItemStack itemStack;
  private Set<PotionEffect> potionEffects;

  public Drug(String name, int prize, String usage, ItemStack itemStack, Set<PotionEffect> potionEffects) {
    this.name = name;
    this.prize = prize;
    this.usage = usage;
    this.itemStack = itemStack;
    this.potionEffects = potionEffects;
  }

  public String getName() {
    return colored(name);
  }

  public int getPrize() { return prize; }

  public String getUsage() {
    return usage;
  }

  public ItemStack getItemStack() {
    return itemStack;
  }

  public Set<PotionEffect> getPotionEffects() {
    return potionEffects;
  }
}
