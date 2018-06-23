package pl.meleride.api.i18n;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import java.util.Collection;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import pl.meleride.api.MelerideAPI;
import pl.meleride.api.message.MessageType;
import pl.meleride.api.user.User;

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
      case TITLE:
        player.sendTitle(this.messageContent, "", -1, -1, -1);
        break;
      case SUB_TITLE:
        player.sendTitle("", this.messageContent, -1, -1, -1);
        break;
      case ACTION_BAR:
        player.spigot()
            .sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(this.messageContent));
        break;
      case HOLOGRAM:
        final MelerideAPI plugin = MelerideAPI.getPlugin(MelerideAPI.class);

        if (!plugin.isUsingHolograms()) {
          player.sendMessage(this.messageContent);
          break;
        }
        final Hologram hologram = HologramsAPI.createHologram(plugin, player.getLocation());

        hologram.getVisibilityManager().setVisibleByDefault(false);
        hologram.getVisibilityManager().showTo(player);
        hologram.appendTextLine(this.messageContent);

        plugin.getServer().getScheduler()
            .runTaskLater(plugin, hologram::delete, 20L * 4 + 10L); //4.5 second
        break;
      case CHAT:
      default:
        player.sendMessage(this.messageContent);
        break;
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
