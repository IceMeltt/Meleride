package pl.meleride.shop.util;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import pl.meleride.api.builder.ItemBuilder;

public final class ItemUtil {

  public static ItemStack fromSection(final ConfigurationSection section) {

    final ItemBuilder builder = new ItemBuilder(Material.GRASS);

    if (section.isString("material")) {
      final Material material = Material.getMaterial(section.getString("material"));
      if (material != null) {
        builder.setType(material);
      }
    }
    if (section.isList("lore")) {
      builder.setLore(String.join("", section.getStringList("lore")));
    }
    if (section.isString("name")) {
      builder.setName(section.getString("name"));
    }
    if (section.isInt("amount")) {
      builder.setAmount(section.getInt("amount"));
    }
    if (section.isInt("data")) {
      builder.setDamage(section.getInt("data"));
    }
    if (section.isInt("durability")) {
      builder.setDamage(section.getInt("durability"));
    }

    return builder.build();
  }

  private ItemUtil() {
  }

}
