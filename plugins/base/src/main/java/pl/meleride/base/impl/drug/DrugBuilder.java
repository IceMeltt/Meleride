package pl.meleride.base.impl.drug;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang.Validate;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import pl.meleride.api.impl.i18n.MessageBundle;
import pl.meleride.api.impl.item.ItemBuilder;
import pl.meleride.base.drug.Drug;

import static pl.meleride.api.impl.util.MessageUtil.*;


public class DrugBuilder {

  private String name = "Nieznany";
  private int prize = 0;

  private String usage = MessageBundle.create("drugtype.defaultusage").toString();
  private Material material = Material.BARRIER;
  private int damage = 0;

  private Set<PotionEffect> potionEffects = new LinkedHashSet<>();

  public DrugBuilder withName(String name) {
    Validate.notNull(name);
    this.name = name;

    return this;
  }

  public DrugBuilder withMaterial(Material material) {
    Validate.notNull(material, "Material cannot be null!");
    this.material = material;

    return this;
  }

  public DrugBuilder withDamage(int damage) {
    this.damage = damage;

    return this;
  }

  public DrugBuilder withUsage(String usage) {
    Validate.notNull(usage, "Usage cannot be null!");
    this.usage = usage;

    return this;
  }

  public DrugBuilder withEffect(PotionEffect potionEffect) {
    this.potionEffects.add(potionEffect);

    return this;
  }

  public DrugBuilder withPrize(int prize) {
    this.prize = prize;

    return this;
  }

  public Drug build() {
    ItemStack itemStack = new ItemBuilder(material)
        .setName(name)
        .setDamage(damage)
        .setLore(colored("&eCena: " + prize))
        .build();

    return new Drug(name, prize, usage, itemStack, potionEffects);
  }
}
