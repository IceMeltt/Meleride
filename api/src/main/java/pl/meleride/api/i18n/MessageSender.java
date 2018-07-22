package pl.meleride.api.i18n;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.meleride.api.entity.User;
import pl.meleride.api.message.MessageType;

import java.util.Collection;

public final class MessageSender {

  private final MessageType messageType;
  private final String messageValue;

  MessageSender(MessageType messageType, String messageValue) {
    this.messageType = messageType;
    this.messageValue = messageValue;
  }

  public void sendTo(Player player) {
    Validate.notNull(player, "Player cannot be null!");

    switch (this.messageType) {
      case CHAT:
        player.sendMessage(this.messageValue);
        break;
      case TITLE:
        player.sendTitle(this.messageValue, "", -1, -1, -1);
        break;
      case SUB_TITLE:
        player.sendTitle("", this.messageValue, -1, -1, -1);
        break;
      case ACTION_BAR:
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(this.messageValue));
        break;
      default:
        player.sendMessage(this.messageValue);
    }
  }

  public <T extends User> void sendTo(T user) {
    Validate.notNull(user, "User cannot be null!");

    this.sendTo(Bukkit.getPlayer(user.getIdentifier()));
  }

  public void sendTo(Collection<? extends Player> players) {
    Validate.notNull(players, "Players collection cannot be null!");

    players.forEach(this::sendTo);
  }

}
