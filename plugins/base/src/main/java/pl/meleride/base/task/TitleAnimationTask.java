package pl.meleride.base.task;

import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TitleAnimationTask extends BukkitRunnable {

  private final List<String> animationContent;
  private final List<String> subTitleContent;
  private final Player player;
  private int splittedWordPosition;

  public TitleAnimationTask(List<String> animationContent,
      List<String> subTitleContent, Player player, int splittedWordPosition) {
    this.animationContent = animationContent;
    this.subTitleContent = subTitleContent;
    this.player = player;
    this.splittedWordPosition = splittedWordPosition;
  }

  @Override
  public void run() {
    if (this.splittedWordPosition >= this.animationContent.size()) {
      this.cancel();
      return;
    }

    this.player.sendTitle(this.animationContent.get(this.splittedWordPosition), this.subTitleContent.get(splittedWordPosition), 0, 15, 0);
    this.splittedWordPosition++;
  }

}
