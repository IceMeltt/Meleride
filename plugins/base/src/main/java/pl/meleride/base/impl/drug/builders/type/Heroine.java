package pl.meleride.base.impl.drug.builders.type;

import static pl.meleride.api.message.MessageUtil.colored;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import pl.meleride.api.builder.item.ItemBuilder;
import pl.meleride.api.i18n.MessageBundler;
import pl.meleride.base.drug.DrugBuilder;
import pl.meleride.base.impl.drug.builders.Drug;
import pl.meleride.base.impl.drug.builders.DrugConfigurator;


public class Heroine implements DrugBuilder {

  private Drug drug;

  public Heroine() {
    drug = new Drug();
  }

  @Override
  public void addDrugConfig() {
    DrugConfigurator drugConfig = new DrugConfigurator();
    drugConfig.setPrice(2);
    drugConfig.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 180 * 20, 1));
    drugConfig.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 360 * 20, 2));
    drugConfig.setUsage(MessageBundler.create("drugtype.heroine").toString());
    drug.setDrugConfig(drugConfig);
  }


  @Override
  public void addItemStack() {
    ItemStack itemStack = new ItemBuilder(Material.INK_SACK)
        .setDamage((byte) 3)
        .setName(colored("Heroina"))
        .build();
    drug.setItemStack(itemStack);
  }

  @Override
  public Drug getDrug() {
    return drug;
  }
}
