package pl.meleride.base.impl.drug.builders;

import pl.meleride.base.drug.DrugBuilder;

/*
 * Meleride (c) 2017-present
 * All Rights Reserved.
 * Don't even think about stealing the code ;).
 */
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
