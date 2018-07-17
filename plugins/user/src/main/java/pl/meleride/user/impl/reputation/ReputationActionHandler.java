package pl.meleride.user.impl.reputation;

import org.bukkit.entity.Player;
import pl.meleride.api.i18n.MessageBundler;
import pl.meleride.api.message.MessageType;
import pl.meleride.api.user.User;
import pl.meleride.user.MelerideUser;

import java.util.Optional;

public final class ReputationActionHandler {

  private final MelerideUser instance;

  public ReputationActionHandler(final MelerideUser instance) {
    this.instance = instance;
  }

  public void viewPlayerReputation(Player executorPlayer, String targetPlayerName) {
    Optional<User> targetUser = instance.getUserManager().getUser(targetPlayerName);
    if (!targetUser.isPresent()) {
      MessageBundler.create("user.dataNotFound")
          .withField("USERNAME", targetPlayerName)
          .target(MessageType.CHAT)
          .sendTo(executorPlayer);
    }

    MessageBundler.create("user.displayReputation")
        .withField("VALUE", String.valueOf(targetUser.get().getReputation()))
        .target(MessageType.CHAT)
        .sendTo(executorPlayer);
  }

  public void addPlayerReputation(Player executorPlayer, String targetPlayerName, int amount) {
    Optional<User> targetUser = instance.getUserManager().getUser(targetPlayerName);
    if (!targetUser.isPresent()) {
      MessageBundler.create("user.dataNotFound")
          .withField("USERNAME", targetPlayerName)
          .target(MessageType.CHAT)
          .sendTo(executorPlayer);
    }

    int beforeTargetUserReputation = targetUser.get().getReputation();
    targetUser.get().addReputation(amount);

    MessageBundler.create("user.reputationAdded")
        .withField("USERNAME", targetPlayerName)
        .withField("BEFORE_VALUE", String.valueOf(beforeTargetUserReputation))
        .withField("AFTER_VALUE", String.valueOf(targetUser.get().getReputation()))
        .target(MessageType.CHAT)
        .sendTo(executorPlayer);

    if (targetUser.get().getBukkitPlayer().isOnline()) {
      MessageBundler.create("user.reputationChanged")
          .withField("VALUE", String.valueOf(targetUser.get().getReputation()))
          .target(MessageType.CHAT)
          .sendTo(executorPlayer);
    }
  }


}
