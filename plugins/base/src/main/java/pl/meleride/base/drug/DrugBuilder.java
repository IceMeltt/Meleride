package pl.meleride.base.drug;

import pl.meleride.base.impl.drug.builder.Drug;

public interface DrugBuilder {

  Drug getDrug();
  void addDrugConfig();
  void addItemStack();

}
