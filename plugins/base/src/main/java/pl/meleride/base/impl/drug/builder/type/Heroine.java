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

public class Heroine implements DrugBuilder {

  private DrugConfigurator drugConfig;
  private ItemStack itemStack;

  private final Drug drug = new Drug();

  @Override
  public void addDrugConfig() {
    drugConfig = new DrugConfigurator();
    drugConfig.setPrice(2);
    drugConfig.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 180 * 20, 1));
    drugConfig.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 360 * 20, 2));
    drugConfig.setUsage(MessageBundler.create("drugtype.heroine").toString());
    drug.setDrugConfig(drugConfig);
  }


  @Override
  public void addItemStack() {
    itemStack = new ItemBuilder(Material.INK_SACK)
        .setDamage((byte) 3)
        .setName("Heroina")
        .build();
    drug.setItemStack(itemStack);
  }

  @Override
  public Drug getDrug() {
    return drug;
  }

}
