package pl.meleride.economy.user;

import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.meleride.api.exception.UserManagerException;
import pl.meleride.api.storage.StorageException;
import pl.meleride.api.storage.dao.IdentifiableEntityDao;
import pl.meleride.economy.MelerideEconomy;

public class EconomyUserAccidentor implements BaseAccidentor {

  private MelerideEconomy instance;

  public EconomyUserAccidentor(MelerideEconomy instance) {
    this.instance = instance;
  }

  public void notFoundOnManager(Player player) {
    if(instance.getManager().getUser(player).isPresent()) {
      Bukkit.getLogger().warning("UWAGA: " );
      Bukkit.getLogger().warning("Gracz " + player.getName() + "istnieje juz w mapie graczy!");
      throw new UserManagerException();
    }
    EconomyUser newUser = new EconomyUserImpl(player);

    IdentifiableEntityDao<EconomyUser> dao = instance.getEconomyDao();

    try {
      dao.download(newUser);
    } catch(SQLException | StorageException e) {
      Bukkit.getLogger().severe("Wystapil blad podczas przetwarzania gracza!!!1");
      e.printStackTrace();
    }
  }

}
