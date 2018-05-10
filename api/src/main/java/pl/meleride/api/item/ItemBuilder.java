package pl.meleride.api.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ItemBuilder {

  private ItemStack itemStack;
  private ItemMeta itemMeta;

  public ItemBuilder(Material material) {
    this.itemStack = new ItemStack(material, 1);
    this.itemMeta = this.itemStack.getItemMeta();
  }

  public ItemBuilder(Material material, int amount) {
    this.itemStack = new ItemStack(material, amount);
    this.itemMeta = this.itemStack.getItemMeta();
  }

  public ItemBuilder(Material material, int amount, int damage) {
    this.itemStack = new ItemStack(material, amount, (short) damage);
    this.itemMeta = this.itemStack.getItemMeta();
  }

  public ItemBuilder(ItemStack itemStack) {
    this.itemStack = itemStack;
    this.itemMeta = this.itemStack.getItemMeta();
  }

  public ItemBuilder setName(String displayName) {
    this.itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
    return this;
  }

  public ItemBuilder setLore(String... lore) {
    List<String> modifiedLore = Arrays.stream(lore)
            .map(entry -> ChatColor.translateAlternateColorCodes('&', entry))
            .collect(Collectors.toList());

    this.itemMeta.setLore(modifiedLore);

    return this;
  }

  public ItemBuilder setAmount(int amount) {
    this.itemStack.setAmount(amount);
    return this;
  }

  public ItemBuilder setDamage(int damage) {
    this.itemStack.setDurability((short) damage);
    return this;
  }

  public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
    this.itemStack.addEnchantment(enchantment, level);
    return this;
  }

  public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments) {
    this.itemStack.addEnchantments(enchantments);
    return this;
  }

  public ItemBuilder setType(Material type) {
    this.itemStack.setType(type);
    return this;
  }

  public ItemBuilder addItemFlags(ItemFlag... itemFlags) {
    this.itemMeta.addItemFlags(itemFlags);
    return this;
  }

  public ItemBuilder setUnbreakable(boolean unbreakable) {
    this.itemMeta.setUnbreakable(unbreakable);
    return this;
  }

  public ItemBuilder setMaterialData(MaterialData materialData) {
    this.itemStack.setData(materialData);
    return this;
  }

  public ItemStack build() {
    this.itemStack.setItemMeta(this.itemMeta);

    return this.itemStack;
  }

}
