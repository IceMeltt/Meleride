package pl.meleride.user.reputation;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.meleride.api.i18n.MessageBundler;
import pl.meleride.api.message.MessageType;
import pl.meleride.user.MelerideUser;

import pl.meleride.user.entity.User;

public final class ReputationActionHandler {

  private final MelerideUser plugin;

  public ReputationActionHandler(final MelerideUser plugin) {
    this.plugin = plugin;
  }

  public void viewPlayerReputation(Player executorPlayer, String targetPlayerName) {

    if(!this.plugin.getUserManager().getUser(targetPlayerName).isPresent()) {
      MessageBundler.create("user.notPlayerExist")
          .withField("GETTER", targetPlayerName)
          .target(MessageType.CHAT)
          .sendTo(executorPlayer);
      return;
    }

    User user = this.plugin.getUserManager().getUser(targetPlayerName).get();

    MessageBundler.create("user.displayReputation")
        .withField("PLAYER", executorPlayer.getName())
        .withField("VALUE", String.valueOf(user.getReputation()))
        .target(MessageType.CHAT)
        .sendTo(executorPlayer);
  }

  public void addPlayerReputation(Player executorPlayer, String targetPlayerName, int amount) {

    if(!this.plugin.getUserManager().getUser(targetPlayerName).isPresent()) {
      MessageBundler.create("user.notPlayerExist")
          .withField("GETTER", targetPlayerName)
          .target(MessageType.CHAT)
          .sendTo(executorPlayer);
      return;
    }

    User user = this.plugin.getUserManager().getUser(targetPlayerName).get();

    int beforeTargetUserReputation = user.getReputation();
    this.plugin.getUserManager().addReputation(user, amount);

    MessageBundler.create("user.reputationAdded")
        .withField("SENDER", executorPlayer.getName())
        .withField("PLAYER", targetPlayerName)
        .withField("BEFORE_VALUE", String.valueOf(beforeTargetUserReputation))
        .withField("AFTER_VALUE", String.valueOf(user.getReputation()))
        .target(MessageType.CHAT)
        .sendTo(executorPlayer);

    if (Bukkit.getPlayer(user.getIdentifier()).isOnline()) {
      MessageBundler.create("user.reputationChanged")
          .withField("PLAYER", Bukkit.getPlayer(user.getIdentifier()).getName())
          .withField("VALUE", String.valueOf(user.getReputation()))
          .target(MessageType.CHAT)
          .sendTo(executorPlayer);
    }
  }


}
