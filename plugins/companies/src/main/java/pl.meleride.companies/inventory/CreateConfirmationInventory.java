package pl.meleride.companies.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.companies.MelerideCompanies;
import pl.meleride.companies.entity.Company;
import pl.meleride.companies.entity.impl.CompanyBuilder;
import pl.socketbyte.opengui.GUI;
import pl.socketbyte.opengui.GUIExtender;
import pl.socketbyte.opengui.ItemBuilder;
import pl.socketbyte.opengui.ItemPack;
import pl.socketbyte.opengui.Rows;

import static pl.meleride.api.message.MessageUtil.*;

public class CreateConfirmationInventory extends GUIExtender {

  private MelerideCompanies plugin = JavaPlugin.getPlugin(MelerideCompanies.class);
  private Player executor;
  private Map<Player, String> companyName;

  public CreateConfirmationInventory(Player executor, String name) {
    super(new GUI("&8» &aCzy jestes pewien?", Rows.FIVE));
    this.companyName = new HashMap<>();
    this.executor = executor;
    this.companyName.put(executor, name);

    this.getGuiSettings().setCanDrag(false);
    this.getGuiSettings().setCanEnterItems(false);
    this.greenItemPacks().forEach((this::setItem));
    this.redItemPacks().forEach((this::setItem));

    this.greenItemPacks().stream()
        .forEach((itemPack -> this.addElementResponse(itemPack.getSlot(), event -> {
          Company preparedCompany = new CompanyBuilder()
              .withName(this.companyName.get(executor))
              .withOwner(this.plugin.getUserManager().getUser(executor).get())
              .withIdentifier(UUID.nameUUIDFromBytes(this.companyName.get(executor).getBytes()))
              .withLevel(0)
              .build();

          this.plugin.getCompanyManager().addCompany(preparedCompany);
          executor.sendMessage(colored("&8» &aPomyslnie stworzyles firme!"));
          executor.closeInventory();
        })));

    this.redItemPacks().stream()
        .forEach((itemPack -> this.addElementResponse(itemPack.getSlot(), event -> executor.closeInventory())));
  }

  private List<ItemPack> greenItemPacks() {
    List<ItemPack> packs = new ArrayList<>();
    packs.add(new ItemPack(10, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 5)));
    packs.add(new ItemPack(11, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 5)));
    packs.add(new ItemPack(12, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 5)));
    packs.add(new ItemPack(19, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 5)));
    packs.add(new ItemPack(20, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 5)));
    packs.add(new ItemPack(21, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 5)));
    packs.add(new ItemPack(28, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 5)));
    packs.add(new ItemPack(29, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 5)));
    packs.add(new ItemPack(30, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 5)));

    return packs;
  }

  private List<ItemPack> redItemPacks() {
    List<ItemPack> packs = new ArrayList<>();
    packs.add(new ItemPack(14, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14)));
    packs.add(new ItemPack(15, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14)));
    packs.add(new ItemPack(16, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14)));
    packs.add(new ItemPack(23, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14)));
    packs.add(new ItemPack(24, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14)));
    packs.add(new ItemPack(25, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14)));
    packs.add(new ItemPack(32, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14)));
    packs.add(new ItemPack(33, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14)));
    packs.add(new ItemPack(34, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14)));

    return packs;
  }

  @Override
  public void onOpen(InventoryOpenEvent inventoryOpenEvent) {

  }

  @Override
  public void onClose(InventoryCloseEvent inventoryCloseEvent) {

  }
}
