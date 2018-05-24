package pl.meleride.base.drug;

import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import pl.meleride.api.impl.i18n.MessageBundle;
import pl.meleride.base.exception.NoSuchDrugException;
import pl.meleride.base.impl.drug.DrugBuilder;

import static pl.meleride.api.impl.util.MessageUtil.*;


public class DrugFactory {

  public static Drug getDrugByName(String type) {

    Optional<Drug> optionalDrug = Optional.empty();
    switch (type) {
      case "§2Marihuana":
        optionalDrug = Optional.of(new DrugBuilder()
            .withName(colored("&2Marihuana"))
            .withMaterial(Material.INK_SACK)
            .withPrize(2)
            .withDamage((byte) 2)
            .withEffect(new PotionEffect(PotionEffectType.JUMP, 480 * 20, 3))
            .withEffect(new PotionEffect(PotionEffectType.HUNGER, 480 * 20, 1))
            .withUsage(MessageBundle.create("drugtype.cannabis").toString())
            .build());
        break;
      case "Heroina":
        optionalDrug = Optional.of(new DrugBuilder()
            .withName(colored("Heroina"))
            .withMaterial(Material.INK_SACK)
            .withPrize(2)
            .withDamage((byte) 3)
            .withEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 180 * 20, 1))
            .withEffect(new PotionEffect(PotionEffectType.HUNGER, 360 * 20, 2))
            .withUsage(MessageBundle.create("drugtype.heroine").toString())
            .build());
        break;
      case "§bEcstazy":
        optionalDrug = Optional.of(new DrugBuilder()
            .withName(colored("&bEcstazy"))
            .withMaterial(Material.PRISMARINE_CRYSTALS)
            .withPrize(3)
            .withDamage(0)
            .withEffect(new PotionEffect(PotionEffectType.JUMP, 480 * 20, 3))
            .withEffect(new PotionEffect(PotionEffectType.HUNGER, 480 * 20, 2))
            .withUsage(MessageBundle.create("drugtype.mdma").toString())
            .build());
        break;
      case "Kokaina":
        optionalDrug = Optional.of(new DrugBuilder()
            .withMaterial(Material.SUGAR)
            .withName("Kokaina")
            .withPrize(4)
            .withDamage(0)
            .withEffect(new PotionEffect(PotionEffectType.SPEED, 480 * 20, 2))
            .withEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 480 * 20, 3))
            .withUsage(MessageBundle.create("drugtype.cocaine").toString())
            .build());
        break;
    }
    return optionalDrug.orElseThrow(NoSuchDrugException::new);
  }

}
