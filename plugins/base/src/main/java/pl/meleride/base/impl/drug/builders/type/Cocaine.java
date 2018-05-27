package pl.meleride.base.impl.drug.builders.type;

import static pl.meleride.api.message.MessageUtil.colored;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import pl.meleride.api.builder.item.ItemBuilder;
import pl.meleride.api.i18n.MessageBundle;
import pl.meleride.base.drug.DrugBuilder;
import pl.meleride.base.impl.drug.builders.Drug;
import pl.meleride.base.impl.drug.builders.DrugConfigurator;


public class Cocaine implements DrugBuilder {

  private Drug drug;

  public Cocaine() {
    drug = new Drug();
  }

  @Override
  public void addDrugConfig() {
    DrugConfigurator drugConfig = new DrugConfigurator();
    drugConfig.setPrice(4);
    drugConfig.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 480 * 20, 2));
    drugConfig.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 480 * 20, 3));
    drugConfig.setUsage(MessageBundle.create("drugtype.cocaine").toString());
    drug.setDrugConfig(drugConfig);
  }

  @Override
  public void addItemStack() {
    ItemStack itemStack = new ItemBuilder(Material.SUGAR)
        .setDamage(0)
        .setName(colored("Kokaina"))
        .build();
    drug.setItemStack(itemStack);
  }

  @Override
  public Drug getDrug() {
    return drug;
  }
}
