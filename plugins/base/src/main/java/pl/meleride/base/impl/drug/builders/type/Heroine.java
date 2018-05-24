package pl.meleride.base.impl.drug.builders.type;

import static pl.meleride.api.impl.util.MessageUtil.colored;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import pl.meleride.api.impl.i18n.MessageBundle;
import pl.meleride.api.impl.item.ItemBuilder;
import pl.meleride.base.drug.DrugBuilder;
import pl.meleride.base.impl.drug.builders.Drug;
import pl.meleride.base.impl.drug.builders.DrugConfigurator;

/*
 * Meleride (c) 2017-present
 * All Rights Reserved.
 * Don't even think about stealing the code ;).
 */
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
    drugConfig.setUsage(MessageBundle.create("drugtype.heroine").toString());
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
