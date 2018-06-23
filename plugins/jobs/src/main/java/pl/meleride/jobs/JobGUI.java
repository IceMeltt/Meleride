package pl.meleride.jobs;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
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
      setItem(i, new GUIExtenderItem(new ItemBuilder(job.getGUIItem()).setName(job.getName())) {
        @Override
        public void onClick(InventoryClickEvent event) {
          if (event.getCurrentItem() != null) {
            Player player = (Player) event.getWhoClicked();
            player.closeInventory();

            if (jobManager.addUserJob(player.getUniqueId(), job)) {
              player.sendMessage("Wybrales prace " + job.getName());
              return;
            }

            player.sendMessage("Nie mozesz miec tylu prac :/");
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