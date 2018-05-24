package pl.meleride.base.drug;

import java.util.Collection;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import static pl.meleride.api.impl.util.MessageUtil.*;

/*
 * Meleride (c) 2017-present
 * All Rights Reserved.
 * Don't even think about stealing the code ;).
 */
public class Drug {

  private String name;
  private int prize;
  private String usage;
  private ItemStack itemStack;
  private Collection<PotionEffect> potionEffects;

  public Drug(String name, int prize, String usage, ItemStack itemStack, Collection<PotionEffect> potionEffects) {
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

  public Collection<PotionEffect> getPotionEffects() {
    return potionEffects;
  }
}