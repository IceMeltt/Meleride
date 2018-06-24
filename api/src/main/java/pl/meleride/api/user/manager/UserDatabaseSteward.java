package pl.meleride.api.user.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.meleride.api.storage.StorageException;
import pl.meleride.api.storage.sql.hikari.SQLHikariStorage;
import pl.meleride.api.user.User;
import pl.meleride.api.user.status.DiseaseStatus;

public class UserDatabaseSteward {

  private final UUID uuid;
  private final User user;
  private final SQLHikariStorage storage;
  private ResultSet result;

  public UserDatabaseSteward(final UUID uuid, final User user, final SQLHikariStorage instance) {
    this.uuid = uuid;
    this.user = user;
    this.storage = instance;
  }

  public void makePlayer(Player player) {
    StringBuilder sb = new StringBuilder("INSERT INTO users (uuid, name, disease) VALUES (")
        .append("'" + player.getUniqueId() + "',")
        .append("'" + player.getName() + "',")
        .append("'[]'")
        .append(") ON DUPLICATE KEY UPDATE ")
        .append("name='" + player.getName() + "',")
        .append("disease='[]';");
    try {
      storage.update(sb.toString());
    } catch(StorageException e) {
      Bukkit.getLogger().severe("Wystąpił BARDZO POTĘŻNY błąd w aktualizacji tablicy!!1");
      e.printStackTrace();
    }
  }

  public void savePlayer() {
    StringBuilder sb = new StringBuilder("INSERT INTO users (uuid, name, disease) VALUES (")
        .append("'" + user.getUniqueId().toString() + "',")
        .append("'" + user.getName() + "',")
        .append("'" + user.getDiseases() + "'")
        .append(") ON DUPLICATE KEY UPDATE ")
        .append("name='" + user.getName() + "',")
        .append("disease='" + user.getDiseases() + "';");
    try {
      storage.update(sb.toString());
    } catch(StorageException e) {
      Bukkit.getLogger().severe("Wystąpił BARDZO POTĘŻNY błąd w aktualizacji tablicy!!1");
      e.printStackTrace();
    }
  }

  public void setName(String name) throws SQLException, StorageException {
    this.result = storage.query("SELECT * from `users` WHERE uuid='" + uuid + "';");
    if(this.result == null) {
      user.setName(name);
      this.savePlayer();
    }
    user.setName(result.getString("name"));
  }

  public void setDiseases() throws SQLException, StorageException {
    this.result = storage.query("SELECT * from `users` WHERE uuid='" + uuid + "';");
    if(result == null) {
      this.savePlayer();
    }
    for(DiseaseStatus disease : DiseaseStatus.getDiseaseFromString(result.getString("disease").split(","))) {
      user.addDisease(disease);
    }
  }

}
