package pl.meleride.companies.inventory;

import static pl.meleride.api.message.MessageUtil.colored;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

public class CreateSelectTradeInventory extends GUIExtender {

  private MelerideCompanies plugin = JavaPlugin.getPlugin(MelerideCompanies.class);
  private String name;
  private User user;
  private Player executor;

  public CreateSelectTradeInventory(Player executor, String name) {
    super(new GUI(colored("&8» &cWybierz branze firmy!"), Rows.ONE));
    this.executor = executor;
    this.name = name;
    this.user = this.plugin.getUserManager().getUser(this.executor).get();

    this.getGuiSettings().setCanDrag(false);
    this.getGuiSettings().setCanEnterItems(false);
    this.getTradeItems().forEach(this::setItem);

    this.getTradeItems().forEach(itemPack -> this.addElementResponse(itemPack.getSlot(), event -> new CompanyConfigurator(this.name, Trade.getBySlot(itemPack.getSlot()),
        Collections.emptyList(), user).nextStep(this.executor, MakeStatus.WORKERS)));
  }

  private List<ItemPack> getTradeItems() {
    List<ItemPack> packs = new ArrayList<>();

    for(Trade trade : Trade.values()) {
      StringBuilder lore = new StringBuilder(colored("&8» &7" + trade.getTitle() + System.getProperty("line.separator")));
      lore.append(colored("&8» Powiazane prace: \n"));
      trade.getLinkedJobs().forEach(string -> lore.append(colored("&8» &e" + string + System.getProperty("line.separator"))));

      ItemBuilder tradeItem = new ItemBuilder(trade.getMaterial())
          .setName(colored(trade.getName()))
          .setLore(lore.toString())
          .update();

      packs.add(new ItemPack(trade.getSlot(), tradeItem));
    }

    return packs;
  }

  @Override
  public void onOpen(InventoryOpenEvent inventoryOpenEvent) {
    if(!this.user.getMakingStatus().equals(MakeStatus.TRADE)) {
      this.user.setMakingStatus(MakeStatus.TRADE);
    }
  }

  @Override
  public void onClose(InventoryCloseEvent inventoryCloseEvent) {
    this.user.setMakingStatus(MakeStatus.NOT_CREATING);
  }
}
