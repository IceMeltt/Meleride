package pl.meleride.base.impl.drug.builders.type;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import pl.meleride.api.impl.i18n.MessageBundle;
import pl.meleride.api.impl.item.ItemBuilder;
import pl.meleride.base.drug.DrugBuilder;
import pl.meleride.base.impl.drug.builders.Drug;
import pl.meleride.base.impl.drug.builders.DrugConfigurator;

import static pl.meleride.api.impl.util.MessageUtil.colored;


public class MDMA implements DrugBuilder {

  private Drug drug;

  public MDMA() {
    drug = new Drug();
  }

  @Override
  public void addDrugConfig() {
    DrugConfigurator drugConfig = new DrugConfigurator();
    drugConfig.setPrice(3);
    drugConfig.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 480 * 20, 3));
    drugConfig.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 480 * 20, 2));
    drugConfig.setUsage(MessageBundle.create("drugtype.mdma").toString());
    drug.setDrugConfig(drugConfig);
  }

  @Override
  public void addItemStack() {
    ItemStack itemStack = new ItemBuilder(Material.PRISMARINE_CRYSTALS)
        .setName(colored("&bEcstazy"))
        .setDamage(0)
        .build();
    drug.setItemStack(itemStack);
  }

  @Override
  public Drug getDrug() {
    return drug;
  }
}
