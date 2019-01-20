package pl.meleride.companies.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.companies.MelerideCompanies;
import pl.meleride.companies.configurator.CompanyConfigurator;
import pl.meleride.companies.entity.User;
import pl.meleride.companies.enums.MakeStatus;
import pl.meleride.companies.enums.Trade;
import pl.socketbyte.opengui.GUI;
import pl.socketbyte.opengui.GUIExtender;
import pl.socketbyte.opengui.ItemBuilder;
import pl.socketbyte.opengui.ItemPack;
import pl.socketbyte.opengui.Rows;

import static pl.meleride.api.message.MessageUtil.*;

public class CreateConfirmationInventory extends GUIExtender {

  private MelerideCompanies plugin = JavaPlugin.getPlugin(MelerideCompanies.class);
  private String name;
  private Trade trade;
  private List<User> workers;
  private User owner;
  private Player executor;

  public CreateConfirmationInventory(Player executor, String name, Trade trade, List<User> workers) {
    super(new GUI("&8» &aCzy jestes pewien?", Rows.FIVE));
    this.executor = executor;
    this.name = name;
    this.trade = trade;
    this.workers = workers;
    this.owner = this.plugin.getUserManager().getUser(this.executor).get();


    this.getGuiSettings().setCanDrag(false);
    this.getGuiSettings().setCanEnterItems(false);
    this.greenItemPacks().forEach((this::setItem));
    this.redItemPacks().forEach((this::setItem));

    this.greenItemPacks().forEach((itemPack -> this.addElementResponse(itemPack.getSlot(), event -> {
      new CompanyConfigurator(this.name, this.trade, this.workers, this.owner).build();
          executor.sendMessage(colored("&8» &aPomyslnie stworzyles firme!"));
          executor.closeInventory();
        })));

    this.redItemPacks().forEach((itemPack -> this.addElementResponse(itemPack.getSlot(), event -> executor.closeInventory())));
  }

  private List<ItemPack> greenItemPacks() {
    List<ItemPack> packs = new ArrayList<>();
    ItemBuilder green = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 5)
        .setName(colored("&a&lTAK"))
        .update();

    packs.add(new ItemPack(10, green));
    packs.add(new ItemPack(11, green));
    packs.add(new ItemPack(12, green));
    packs.add(new ItemPack(19, green));
    packs.add(new ItemPack(20, green));
    packs.add(new ItemPack(21, green));
    packs.add(new ItemPack(28, green));
    packs.add(new ItemPack(29, green));
    packs.add(new ItemPack(30, green));

    return packs;
  }

  private List<ItemPack> redItemPacks() {
    List<ItemPack> packs = new ArrayList<>();
    ItemBuilder red = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14)
        .setName(colored("&c&lNIE"))
        .update();

    packs.add(new ItemPack(14, red));
    packs.add(new ItemPack(15, red));
    packs.add(new ItemPack(16, red));
    packs.add(new ItemPack(23, red));
    packs.add(new ItemPack(24, red));
    packs.add(new ItemPack(25, red));
    packs.add(new ItemPack(32, red));
    packs.add(new ItemPack(33, red));
    packs.add(new ItemPack(34, red));

    return packs;
  }

  @Override
  public void onOpen(InventoryOpenEvent inventoryOpenEvent) {
    if(!this.owner.getMakingStatus().equals(MakeStatus.CONFIRMATION)) {
      this.owner.setMakingStatus(MakeStatus.CONFIRMATION);
    }
  }

  @Override
  public void onClose(InventoryCloseEvent inventoryCloseEvent) {
    this.owner.setMakingStatus(MakeStatus.NOT_CREATING);
  }
}
