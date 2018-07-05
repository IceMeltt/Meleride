package pl.meleride.api.user.accident;

import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.api.MelerideAPI;
import pl.meleride.api.exception.PlayerFoundInManagerException;
import pl.meleride.api.storage.StorageException;
import pl.meleride.api.storage.dao.StorageDao;
import pl.meleride.api.user.User;
import pl.meleride.api.user.UserImpl;

public class UserAccidentor {

  private static MelerideAPI instance = JavaPlugin.getPlugin(MelerideAPI.class);

  public static void notFoundOnManager(Player player) {
    if(instance.getUserManager().getUser(player).isPresent()) {
      Bukkit.getLogger().warning("UWAGA: " );
      Bukkit.getLogger().warning("Gracz " + player.getName() + "istnieje juz w mapie graczy!");
      throw new PlayerFoundInManagerException();
    }
    User newUser = new UserImpl(player);

    StorageDao<User> dao = instance.getUserDao();

    try {
      dao.download(newUser);
    } catch(SQLException | StorageException e) {
      Bukkit.getLogger().severe("Wystapil blad podczas przetwarzania gracza!!!1");
      e.printStackTrace();
    }
  }

  private UserAccidentor() {
  }

}
