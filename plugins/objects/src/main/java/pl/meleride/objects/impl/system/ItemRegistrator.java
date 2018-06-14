package pl.meleride.objects.impl.system;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.objects.MelerideObjects;

public class ItemRegistrator {

  private static final MelerideObjects INSTANCE = JavaPlugin.getPlugin(MelerideObjects.class);
  private static Map<String, AbstractItem> objects = new HashMap<>();

  public static void register(AbstractItem... abstractItems) {
    Validate.notNull(abstractItems, "AbstractItem cannot be null!");

    for(AbstractItem item : abstractItems) {
      objects.put(item.getName(), item);

      Bukkit.getPluginManager().registerEvents(item, INSTANCE);
    }
  }

  public static boolean checkIfExist(String name) {
    return objects.get(name) != null;
  }

  static AbstractItem getRegisteredObject(String name) {
    if(!(checkIfExist(name))) {
      Bukkit.getLogger().severe("Obiekt o nazwie " + name + " nie istnieje w systemie!");
    }

    return objects.get(name);
  }

  public static Map<String, AbstractItem> getObjectMap() {
    return new HashMap<>(objects);
  }

  private ItemRegistrator() {
  }

}
