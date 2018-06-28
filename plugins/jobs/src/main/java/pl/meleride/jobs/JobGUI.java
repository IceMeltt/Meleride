package pl.meleride.jobs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import pl.meleride.jobs.event.QuitJobEvent;
import pl.meleride.jobs.event.SelectedJobEvent;
import pl.socketbyte.opengui.GUI;
import pl.socketbyte.opengui.GUIExtender;
import pl.socketbyte.opengui.GUIExtenderItem;
import pl.socketbyte.opengui.ItemBuilder;
import pl.socketbyte.opengui.Rows;

public class JobGUI extends GUIExtender {

  public JobGUI(JobManager jobManager) {
    super(new GUI("Wybierz Prace", Rows.FIVE));

    int i = 0;
    for (Job job : jobManager.getJobs()) {
      setItem(i, new GUIExtenderItem(new ItemBuilder(job.getGUIItem())
          .setName(job.getName())
          .setLore("Prawy przycisk zeby wybrac prace", "Lewy by opuscic")) {
        @Override
        public void onClick(InventoryClickEvent event) {
          if (event.getCurrentItem() != null) {
            Player player = (Player) event.getWhoClicked();
            player.closeInventory();

            if (event.getClick().isLeftClick()) {
              if (jobManager.addUserJob(player.getUniqueId(), job)) {
                player.sendMessage("Wybrales prace " + job.getName());
                Bukkit.getPluginManager().callEvent(new SelectedJobEvent(player, job));
                return;
              }

              player.sendMessage("Mozesz miec tylko 1 stala prace!");
            }

            if (event.getClick().isRightClick()) {
              if (jobManager.removeUserJob(player.getUniqueId(), job)) {
                player.sendMessage("Rzuciles prace " + job.getName());
                Bukkit.getPluginManager().callEvent(new QuitJobEvent(player, job));
                return;
              }

              player.sendMessage("Nie pracujesz jako " + job.getName());
            }
          }
        }
      });
      i++;
    }
  }

  @Override
  public void onOpen(InventoryOpenEvent event) {

  }

  @Override
  public void onClose(InventoryCloseEvent event) {

  }

}