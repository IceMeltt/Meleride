package pl.meleride.base.drug;

import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import pl.meleride.api.impl.i18n.MessageBundle;
import pl.meleride.base.exception.NoSuchDrugException;
import pl.meleride.base.impl.drug.DrugBuilder;

import static pl.meleride.api.impl.util.MessageUtil.*;

/*
 * Meleride (c) 2017-present
 * All Rights Reserved.
 * Don't even think about stealing the code ;).
 */
public class DrugFactory {

  public static Drug getDrugByName(String type){

    Optional<Drug> optionalDrug = Optional.empty();
    switch (type) {
      case "§2Marihuana":
        optionalDrug = Optional.of(new DrugBuilder()
            .withName(colored("&2Marihuana"))
            .withPrize(2)
            .withMaterial(Material.INK_SACK)
            .withDamage((byte) 2)
            .withEffect(new PotionEffect(PotionEffectType.JUMP, 480 * 20, 3))
            .withEffect(new PotionEffect(PotionEffectType.HUNGER, 480 * 20, 1))
            .withUsage(MessageBundle.create("drugtype.cannabis").toString())
            .build());
        break;
      case "Heroina":
        optionalDrug = Optional.of(new DrugBuilder()
            .withName(colored("Heroina"))
            .withPrize(2)
            .withMaterial(Material.INK_SACK)
            .withDamage((byte) 3)
            .withEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 180 * 20, 1))
            .withEffect(new PotionEffect(PotionEffectType.HUNGER, 360 * 20, 2))
            .withUsage(MessageBundle.create("drugtype.heroine").toString())
            .build());
        break;
      case "§bEcstazy":
        optionalDrug = Optional.of(new DrugBuilder()
            .withName(colored("&bEcstazy"))
            .withPrize(3)
            .withDamage(0)
            .withMaterial(Material.PRISMARINE_CRYSTALS)
            .withEffect(new PotionEffect(PotionEffectType.JUMP, 480 * 20, 3))
            .withEffect(new PotionEffect(PotionEffectType.HUNGER, 480 * 20, 2))
            .withUsage(MessageBundle.create("drugtype.mdma").toString())
            .build());
        break;
      case "Kokaina":
        optionalDrug = Optional.of(new DrugBuilder()
            .withName("Kokaina")
            .withPrize(4)
            .withMaterial(Material.SUGAR)
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