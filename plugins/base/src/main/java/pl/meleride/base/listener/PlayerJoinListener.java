package pl.meleride.base.listener;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.meleride.api.helper.Listener;
import pl.meleride.base.MelerideBase;
import pl.meleride.base.entity.User;
import pl.meleride.base.task.TitleAnimationTask;

import static pl.meleride.api.message.MessageUtil.*;

public class PlayerJoinListener implements Listener<PlayerJoinEvent> {

  private final MelerideBase plugin;

  public PlayerJoinListener(MelerideBase plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  @Override
  public void performEvent(PlayerJoinEvent event) {
    User user = this.plugin.getUserManager().getUser(event.getPlayer().getUniqueId()).get();

    if (!user.getName().isPresent()) {
      user.setName(event.getPlayer().getName());
    }

    if (!event.getPlayer().hasPlayedBefore()) {
      this.plugin.getUserResource().save(user);
    } else {
      this.plugin.getUserResource().load(user);
    }

    String titleToAnimate = colored("&eM&f_ "
        + "&eMe&f_ "
        + "&eMel&f_ "
        + "&eMele&f_ "
        + "&eMele&6r&f_ "
        + "&eMele&6ri&f_ "
        + "&eMele&6rid&f_ "
        + "&eMele&6ride&f_ "
        + "&eMele&6ride "
        + "&e&lM&eele&6ride "
        + "&e&lM&le&ele&6ride "
        + "&e&lM&le&ll&ee&6ride "
        + "&e&lM&le&ll&le&6ride "
        + "&e&lM&le&ll&le&6&lr&6ide "
        + "&e&lM&le&ll&le&6&lr&li&6de "
        + "&e&lM&le&ll&le&6&lr&li&ld&6e "
        + "&e&lM&le&ll&le&6&lr&li&ld&le "
        + "&e&lM&le&ll&le&6&lr&li&ld&le "
        + "&e&lM&le&ll&le&6&lr&li&ld&le "
    );

    String subTitleToAnimate = colored("&7▆&f▇██▇▆ "
        + "&7▆▇&f██▇▆ "
        + "&7▆▇█&f█▇▆ "
        + "&7▆▇██&f▇▆ "
        + "&7▆▇██▇&f▆ "
        + "&7▆▇██▇▆ "
        + "&7▆▇██▆▇ "
        + "&7▆▇█▆▇▇ "
        + "&7▆▇▆█▇▇ "
        + "&7▆▆██▇▇ "
        + "&7▆▇██▇▆ "
        + "&7▆▆██▇▆ "
        + "&7▆▇▆█▇▆ "
        + "&7▆▇█▆▇▆ "
        + "&7▆▇██▆▆ "
        + "&7▆▇██▇▆ "
        + "&e▆▇██▇▆ "
        + "&6▆▇██▇▆ "
        + "&e▆▇█&6█▇▆ "
    );

    List<String> titleAnimationContent = Arrays
        .asList(StringUtils.splitByWholeSeparator(titleToAnimate, " "));

    List<String> subTitleAnimationContent = Arrays
        .asList(StringUtils.splitByWholeSeparator(subTitleToAnimate, " "));

    new TitleAnimationTask(titleAnimationContent, subTitleAnimationContent, event.getPlayer(), 0)
        .runTaskTimerAsynchronously(this.plugin, 20, 4);
  }

}
