package pl.meleride.api.storage.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import pl.meleride.api.MelerideAPI;
import pl.meleride.api.storage.StorageException;

public class StorageDaoImpl<T> implements StorageDao<T> {

  private MelerideAPI instance;

  public StorageDaoImpl(MelerideAPI instance) {
    this.instance = instance;
  }

  @Override
  public List<T> getAll(String table, String label) {
    List<T> objectList = new ArrayList<>();

    try {
      ResultSet result = instance.getStorage().query("SELECT * FROM `" + table + "`");
      while(result.next()) {
        T object = (T) result.getObject(label);
        objectList.add(object);
      }
    } catch(SQLException | StorageException e) {
      Bukkit.getLogger().severe("Wystąpił BARDZO POTEŻNY błąd w pobieraniu elementów!!1");
      e.printStackTrace();
    }
    return objectList;
  }

  @Override
  public Object getVariable(String table, String key, Object value, String whatYouAreLookingFor) {
    try {
        ResultSet result = instance.getStorage().query("SELECT * from `" + table + "` WHERE " + key + "='" + value + "';");
        Object object = result.getObject(whatYouAreLookingFor);

        return object;
    } catch(SQLException | StorageException e) {
      Bukkit.getLogger().severe("Wystąpił BARDZO POTEŻNY błąd w pobieraniu wartości!!1");
      e.printStackTrace();
    }
    return null;
  }

}
