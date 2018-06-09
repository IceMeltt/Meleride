package pl.meleride.base.impl.drug.builder;

import java.util.LinkedHashSet;
import java.util.Set;
import org.bukkit.potion.PotionEffect;
import pl.meleride.api.i18n.MessageBundler;

public class DrugConfigurator {

  private String usage = MessageBundler.create("drug.usage.default").toString();
  private int price = 0;
  private final Set<PotionEffect> potionEffects = new LinkedHashSet<>();

  public void setUsage(String usage) {
    this.usage = usage;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public void addPotionEffect(PotionEffect potionEffect) {
    this.potionEffects.add(potionEffect);
  }

  public String getUsage() {
    return usage;
  }

  public int getPrice() {
    return price;
  }

  public Set<PotionEffect> getPotionEffects() {
    return potionEffects;
  }

}
