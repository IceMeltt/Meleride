package pl.meleride.base.listener;

import java.util.List;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.meleride.api.helper.Listener;
import pl.meleride.api.storage.StorageException;
import pl.meleride.base.MelerideBase;
import pl.meleride.base.entity.User;
import pl.meleride.base.task.TitleAnimationTask;

import static pl.meleride.api.message.MessageUtil.*;

public class PlayerJoinListener implements Listener<PlayerJoinEvent> {

  private final MelerideBase plugin;

  public PlayerJoinListener(MelerideBase plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  @Override
  public void performEvent(PlayerJoinEvent event) throws StorageException {
    User user = this.plugin.getUserManager().getUser(event.getPlayer().getUniqueId()).get();

    if (!user.getName().isPresent()) {
      user.setName(event.getPlayer().getName());
    }

    if (!event.getPlayer().hasPlayedBefore()) {
      this.plugin.getUserResource().save(user);
    } else {
      this.plugin.getUserResource().load(user);
    }

    event.setJoinMessage("");

    List<String> titleAnimationContent = colored(
        this.plugin.getConfig().getStringList("animation.title"));
    List<String> subTitleAnimationContent = colored(
        this.plugin.getConfig().getStringList("animation.subtitle"));

    new TitleAnimationTask(titleAnimationContent, subTitleAnimationContent, event.getPlayer(), 0)
        .runTaskTimerAsynchronously(this.plugin, 20, 4);
  }

}
