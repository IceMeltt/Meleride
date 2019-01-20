package pl.meleride.companies.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Material;

public enum Trade {
  MINING(Material.IRON_PICKAXE, 2, "Kopalnictwo", "Górnik"),
  HISTORY_KNOWLEDGE(Material.SUGAR_CANE, 4, "Historioznawstwo", "Bo czemu u licha prace przepisywane byly 4 razy?", "Historyk"),
  WHEN_MELERIDE_KNOWLEDGE(Material.BOOK, 6, "Wiedza o Meleride", "Kulturoznawca"); //Why not, that's funny. Remember - that's only a demo release.

  private Material material;
  private String name;
  private int slot;
  private String title;
  private List<String> linkedJobs;

  Trade(Material material, int slot, String name, String title, String... linkedJobs) { //TODO Change String type to Job class.
    this.material = material;
    this.slot = slot;
    this.name = name;
    this.title = title;
    this.linkedJobs = new ArrayList<>();
    this.linkedJobs.addAll(Arrays.asList(linkedJobs));
  }

  public static Trade getBySlot(int slot) {
    switch (slot) {
      case 2:
        return Trade.MINING;
      case 4:
        return Trade.HISTORY_KNOWLEDGE;
      case 6:
        return Trade.WHEN_MELERIDE_KNOWLEDGE;
        default:
          throw new NullPointerException("No trade found at declared slot.");
    }
  }

  public Material getMaterial() {
    return this.material;
  }

  public int getSlot() {
    return this.slot;
  }

  public String getName() {
    return this.name;
  }

  public String getTitle() {
    return this.title;
  }

  public List<String> getLinkedJobs() {
    return this.linkedJobs;
  }

  public int getCountedLinkedJobs() {
    return this.linkedJobs.size();
  }
}
