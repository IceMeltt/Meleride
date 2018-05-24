package pl.meleride.base.drug;

import pl.meleride.base.impl.drug.builders.Drug;

/*
 * Meleride (c) 2017-present
 * All Rights Reserved.
 * Don't even think about stealing the code ;).
 */
public interface DrugBuilder {

  Drug getDrug();
  void addDrugConfig();
  void addItemStack();
}
