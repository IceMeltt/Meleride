package pl.meleride.api.storage.userflow;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.meleride.api.MelerideAPI;
import pl.meleride.api.storage.StorageException;
import pl.meleride.api.storage.dao.StorageDao;
import pl.meleride.api.user.User;
import pl.meleride.api.user.UserImpl;

public class UserInspector implements FlowInspector<User> {

  private final MelerideAPI instance;
  private final StorageDao<User> dao;
  private User user;

  public UserInspector(MelerideAPI instance) {
    this.instance = instance;
    this.dao = instance.getUserDao();
  }

  @Override
  public void join(Player player) {
    user = new UserImpl(player);

    if (!this.instance.getUserManager().getUser(player.getUniqueId()).isPresent()) {
      try {
        String query = "SELECT * FROM users WHERE uuid='" + player.getUniqueId() + "';";
        ResultSet result = this.instance.getStorage().query(query);
        if (!result.next()) {
          this.dao.update(user);
          this.instance.getUserManager().addUser(user);
        } else {
          this.dao.download(user);
          this.instance.getUserManager().addUser(user);
        }
      } catch(SQLException | StorageException e) {
        Bukkit.getLogger().severe("Wystapil blad podczas przetwarzania gracza!!!1");
        e.printStackTrace();
      }
    }
  }

  @Override
  public void quit(Player player) {
    if (!this.instance.getUserManager().getUser(player.getUniqueId()).isPresent()) {
      Bukkit.getLogger().warning("Uwaga! Nie znaleziono gracza " + player.getName() + " w bazie!");
      user = new UserImpl(player.getUniqueId());
      try {
        this.dao.download(user);
        this.user.setDataErrorStatus((byte) 1);
        this.dao.update(user);
      } catch(SQLException | StorageException e) {
        Bukkit.getLogger().severe("Wystapil blad podczas przetwarzania gracza!!!1");
        e.printStackTrace();
      }
    } else {
      user = this.instance.getUserManager().getUser(player.getUniqueId()).get();
      try {
        this.dao.update(user);
      } catch (StorageException e) {
        Bukkit.getLogger().severe("Wystapil blad podczas przetwarzania gracza!!!1");
        e.printStackTrace();
      }
    }
      this.instance.getUserManager().removeUser(user);
  }

  @Override
  public User getUser() {
    return user;
  }

}
