package pl.meleride.api.object.system;

import pl.meleride.api.exception.NoSuchItemException;

public class ItemManager {

  public static AbstractItem getObject(String name) {
    if (!(ItemRegistrator.checkIfExist(name))) {
      throw new NoSuchItemException();
    } else {
      return ItemRegistrator.getRegisteredObject(name);
    }
  }

}
