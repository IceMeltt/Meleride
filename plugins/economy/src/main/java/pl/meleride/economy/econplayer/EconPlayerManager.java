package pl.meleride.economy.econplayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class EconPlayerManager {

  private static final Map<UUID, EconPlayer> ECON_PLAYER_MAP = new HashMap<>();

  public static void addPlayer(EconPlayer econPlayer) {
    if (!ECON_PLAYER_MAP.containsKey(econPlayer.getUuid())) {
      ECON_PLAYER_MAP.put(econPlayer.getUuid(), econPlayer);
    }
  }

  public static Optional<EconPlayer> getPlayer(UUID uuid) {
    return Optional.of(ECON_PLAYER_MAP.getOrDefault(uuid, null));
  }

}
