package pl.meleride.api.impl.i18n;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import pl.meleride.api.basic.User;
import pl.meleride.api.impl.type.MessageType;
import static pl.meleride.api.impl.util.MessageUtil.*;

import java.util.Collection;

public final class MessageSender {

  private final MessageType messageType;
  private final String messageContent;

  MessageSender(MessageType messageType, String messageContent) {
    this.messageType = messageType;
    this.messageContent = messageContent;
  }

  public void sendTo(Player player) {
    Validate.notNull(player, "Player cannot be null!");

    switch (this.messageType) {
      case CHAT:
        player.sendMessage(colored(this.messageContent));
        break;
      case TITLE:
        // todo
        break;
      case SUB_TITLE:
        // todo
        break;
      case ACTION_BAR:
        // todo
        break;

      default: player.sendMessage(colored(this.messageContent));
    }
  }

  public void sendTo(User user) {
    Validate.notNull(user, "User cannot be null!");

    this.sendTo(user.getBukkitPlayer());
  }

  public void sendTo(Collection<? extends Player> players) {
    Validate.notNull(players, "Players collection cannot be null!");

    players.forEach(this::sendTo);
  }

}
