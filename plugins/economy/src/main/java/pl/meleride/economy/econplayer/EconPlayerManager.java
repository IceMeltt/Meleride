package pl.meleride.economy.econplayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class EconPlayerManager {

  private final Map<UUID, EconPlayer> econPlayerMap = new HashMap<>();

  public EconPlayerManager() {
  }

  public void addPlayer(EconPlayer econPlayer) {
    if (!econPlayerMap.containsKey(econPlayer.getUuid())) {
      econPlayerMap.put(econPlayer.getUuid(), econPlayer);
    }
  }

  public Optional<EconPlayer> getPlayer(UUID uuid) {
    return Optional.of(econPlayerMap.getOrDefault(uuid, null));
  }

}
