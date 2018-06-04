package pl.meleride.objects.impl.system;

import pl.meleride.objects.exceptions.NoSuchItemException;

public class ItemManager {

  public static AbstractItem getObject(String name) {
    if (!(ItemRegistrator.checkIfExist(name))) {
      throw new NoSuchItemException();
    } else {
      return ItemRegistrator.getRegisteredObject(name);
    }
  }

}
