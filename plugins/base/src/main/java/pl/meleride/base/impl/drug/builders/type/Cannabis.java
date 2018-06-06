package pl.meleride.base.impl.drug.builders.type;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import pl.meleride.api.builder.item.ItemBuilder;
import pl.meleride.api.i18n.MessageBundler;
import pl.meleride.base.drug.DrugBuilder;
import pl.meleride.base.impl.drug.builders.Drug;
import pl.meleride.base.impl.drug.builders.DrugConfigurator;

import static pl.meleride.api.message.MessageUtil.colored;


public class Cannabis implements DrugBuilder {

  private Drug drug;

  public Cannabis() {
    drug = new Drug();
  }

  @Override
  public void addDrugConfig() {
    DrugConfigurator drugConfig = new DrugConfigurator();
    drugConfig.setPrice(2);
    drugConfig.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 480 * 20, 3));
    drugConfig.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 480 * 20, 1));
    drugConfig.setUsage(MessageBundler.create("drugtype.cannabis").toString());
    drug.setDrugConfig(drugConfig);
  }

  @Override
  public void addItemStack() {
    ItemStack itemStack = new ItemBuilder(Material.INK_SACK)
        .setDamage((byte) 2)
        .setName(colored("&2Marihuana"))
        .build();
    drug.setItemStack(itemStack);
  }

  @Override
  public Drug getDrug() {
    return drug;
  }


}
