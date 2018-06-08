package pl.meleride.base.impl.drug.builder;

import pl.meleride.base.drug.DrugBuilder;

public class Creator {

  private final DrugBuilder builder;

  public Creator(DrugBuilder builder) {
    this.builder = builder;
  }

  public Drug createDrug() {
    builder.addDrugConfig();
    builder.addItemStack();

    return builder.getDrug();
  }

}
