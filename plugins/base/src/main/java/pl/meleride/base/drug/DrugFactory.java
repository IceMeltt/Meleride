package pl.meleride.base.drug;

import java.util.Optional;

import pl.meleride.base.exception.NoSuchDrugException;
import pl.meleride.base.impl.drug.builder.Creator;
import pl.meleride.base.impl.drug.builder.Drug;
import pl.meleride.base.impl.drug.builder.type.Cannabis;
import pl.meleride.base.impl.drug.builder.type.Cocaine;
import pl.meleride.base.impl.drug.builder.type.Heroine;
import pl.meleride.base.impl.drug.builder.type.MDMA;

public class DrugFactory {

  public static DrugPackager getDrugByName(String type) {

    Optional<DrugPackager> optionalDrug = Optional.empty();
    switch (type) {
      case "§2Marihuana":
        Creator cannabisCreator = new Creator(new Cannabis());
        Drug cannabis = cannabisCreator.createDrug();
        optionalDrug = Optional.of(new DrugPackager(cannabis.getDrugConfig().getUsage(),
            cannabis.getItemStack(),
            cannabis.getDrugConfig().getPotionEffects(),
            cannabis.getDrugConfig().getPrice()));
        break;
      case "Heroina":
        Creator heroineCreator = new Creator(new Heroine());
        Drug heroine = heroineCreator.createDrug();
        optionalDrug = Optional.of(new DrugPackager(heroine.getDrugConfig().getUsage(),
                heroine.getItemStack(),
                heroine.getDrugConfig().getPotionEffects(),
            heroine.getDrugConfig().getPrice()));
        break;
      case "§bEcstazy":
        Creator mdmaCreator = new Creator(new MDMA());
        Drug mdma = mdmaCreator.createDrug();
        optionalDrug = Optional.of(new DrugPackager(mdma.getDrugConfig().getUsage(),
            mdma.getItemStack(),
            mdma.getDrugConfig().getPotionEffects(),
            mdma.getDrugConfig().getPrice()));
        break;
      case "Kokaina":
        Creator cocaineCreator = new Creator(new Cocaine());
        Drug cocaine = cocaineCreator.createDrug();
        optionalDrug = Optional.of(new DrugPackager(cocaine.getDrugConfig().getUsage(),
            cocaine.getItemStack(),
            cocaine.getDrugConfig().getPotionEffects(),
            cocaine.getDrugConfig().getPrice()));
        break;
    }
    return optionalDrug.orElseThrow(NoSuchDrugException::new);
  }

}
