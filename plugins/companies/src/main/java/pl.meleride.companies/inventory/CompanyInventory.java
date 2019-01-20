package pl.meleride.companies.inventory;

import static pl.meleride.api.message.MessageUtil.colored;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.companies.MelerideCompanies;
import pl.meleride.companies.entity.Company;
import pl.meleride.companies.entity.User;
import pl.socketbyte.opengui.GUI;
import pl.socketbyte.opengui.GUIExtender;
import pl.socketbyte.opengui.ItemBuilder;
import pl.socketbyte.opengui.ItemPack;
import pl.socketbyte.opengui.Rows;

public class CompanyInventory extends GUIExtender {

  private MelerideCompanies plugin = JavaPlugin.getPlugin(MelerideCompanies.class);
  private String name;
  private Player player;
  private User user;
  private Company company;

  public CompanyInventory(Player player) {
    super(new GUI(colored("&8» &7Twoja firma:"), Rows.THREE));
    user = this.plugin.getUserManager().getUser(player).get();
    company = this.plugin.getCompanyManager().getCompany(user).get();

    this.getGuiSettings().setCanEnterItems(false);
    this.getGuiSettings().setCanDrag(false);

    this.getItemPacks().forEach(this::setItem);
  }

  private List<ItemPack> getItemPacks() {
    List<ItemPack> packs = new ArrayList<>();

    for(int i = 0; i < Rows.THREE.getSlots(); i++) {
      packs.add(new ItemPack(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 15).setName(" ").update()));
    }

    packs.add(new ItemPack(10, new ItemBuilder(Material.NAME_TAG).setName(colored("&8» &7Nazwa: &e" + company.getName().get())).update()));
    packs.add(new ItemPack(11, new ItemBuilder(Material.EXP_BOTTLE).setName(colored("&8» &7Poziom firmy: &e" + company.getLevel())).update()));
    packs.add(new ItemPack(12, new ItemBuilder(Material.DIAMOND_AXE).setName(colored("&8» &7Branza firmy: &e" + company.getBusiness())).update()));

    packs.add(new ItemPack(14, new ItemBuilder(Material.GOLD_NUGGET).setName(colored("&8» &7Zarobek firmy: &e+1452.00zl")).update()));
    packs.add(new ItemPack(15, new ItemBuilder(Material.SIGN).setName(colored("&8» &7Pozycja w rankingu: &e13")).update()));

    return packs;
  }

  @Override
  public void onOpen(InventoryOpenEvent inventoryOpenEvent) {

  }

  @Override
  public void onClose(InventoryCloseEvent inventoryCloseEvent) {

  }
}
