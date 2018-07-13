package pl.meleride.economy.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.meleride.api.storage.StorageException;
import pl.meleride.api.storage.dao.IdentifiableEntityDao;
import pl.meleride.api.storage.userflow.FlowInspector;
import pl.meleride.economy.MelerideEconomy;

@SuppressWarnings("Duplicates")
public class EconomyUserInspector implements FlowInspector<EconomyUser> {

  private final MelerideEconomy instance;
  private final IdentifiableEntityDao<EconomyUser> dao;
  private EconomyUser user;

  public EconomyUserInspector(MelerideEconomy instance) {
    this.instance = instance;
    this.dao = instance.getEconomyDao();
  }

  @Override
  public void join(Player player) {
    user = new EconomyUserImpl(player);

    if (!this.instance.getManager().getUser(player.getUniqueId()).isPresent()) {
      try {
        String query = "SELECT * FROM economy WHERE uuid='" + player.getUniqueId() + "';";
        ResultSet result = this.instance.getStorage().query(query);
        if (!result.next()) {
          this.dao.update(user);
          this.instance.getManager().addUser(user);
        } else {
          this.dao.download(user);
          this.instance.getManager().addUser(user);
        }
      } catch(SQLException | StorageException e) {
        Bukkit.getLogger().severe("Wystapil blad podczas przetwarzania gracza!!!1");
        e.printStackTrace();
      }
    }
  }

  @Override
  public void quit(Player player) {
    if (!this.instance.getManager().getUser(player.getUniqueId()).isPresent()) {
      Bukkit.getLogger().warning("Uwaga! Nie znaleziono gracza " + player.getName() + " w bazie!");
      user = new EconomyUserImpl(player.getUniqueId());
      try {
        this.dao.download(user);
        this.user.setDataErrorStatus((byte) 1);
        this.dao.update(user);
      } catch(SQLException | StorageException e) {
        Bukkit.getLogger().severe("Wystapil blad podczas przetwarzania gracza!!!1");
        e.printStackTrace();
      }
    } else {
      user = this.instance.getManager().getUser(player.getUniqueId()).get();
      try {
        this.dao.update(user);
      } catch (StorageException e) {
        Bukkit.getLogger().severe("Wystapil blad podczas przetwarzania gracza!!!1");
        e.printStackTrace();
      }
    }
    this.instance.getManager().removeUser(user);
  }

  @Override
  public EconomyUser getUser() {
    return user;
  }

}
