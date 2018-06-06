package pl.meleride.base.impl.drug.builder.type;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import pl.meleride.api.builder.item.ItemBuilder;
import pl.meleride.api.i18n.MessageBundler;
import pl.meleride.base.drug.DrugBuilder;
import pl.meleride.base.impl.drug.builder.Drug;
import pl.meleride.base.impl.drug.builder.DrugConfigurator;

public class Cocaine implements DrugBuilder {

  private DrugConfigurator drugConfig;
  private ItemStack itemStack;

  private Drug drug = new Drug();

  @Override
  public void addDrugConfig() {
    drugConfig = new DrugConfigurator();
    drugConfig.setPrice(4);
    drugConfig.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 480 * 20, 2));
    drugConfig.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 480 * 20, 3));
    drugConfig.setUsage(MessageBundler.create("drugtype.cocaine").toString());
    drug.setDrugConfig(drugConfig);
  }

  @Override
  public void addItemStack() {
    itemStack = new ItemBuilder(Material.SUGAR)
        .setDamage(0)
        .setName("Kokaina")
        .build();
    drug.setItemStack(itemStack);
  }

  @Override
  public Drug getDrug() {
    return drug;
  }

}
