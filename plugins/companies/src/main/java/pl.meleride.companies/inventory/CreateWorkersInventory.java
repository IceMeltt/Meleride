package pl.meleride.companies.inventory;

import static pl.meleride.api.message.MessageUtil.colored;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.meta.SkullMeta;
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

public class CreateWorkersInventory extends GUIExtender {

  private MelerideCompanies plugin = JavaPlugin.getPlugin(MelerideCompanies.class);
  private String name;
  private Trade trade;
  private User user;
  private Player executor;
  private List<User> workers;

  public CreateWorkersInventory(Player executor, String name, Trade trade) {
    super(new GUI(colored("&8» &cWybierz pracownikow firmy!"), Rows.SIX));
    this.executor = executor;
    this.name = name;
    this.trade = trade;
    this.user = this.plugin.getUserManager().getUser(this.executor).get();
    this.workers = new ArrayList<>();

    this.getWorkersItems().forEach(this::setItem);

    this.setItem();
  }

  private Set<ItemPack> getWorkersItems() {
    Set<ItemPack> packs = new HashSet<>();
    ItemBuilder builder;
    Set<? extends Player> users = new HashSet<>(this.plugin.getServer().getOnlinePlayers());
    //users.remove(executor);

    for (int i = 0; i < users.size(); i++) {
      for(Player player : users) {
        builder = new ItemBuilder(Material.SKULL_ITEM)
            .setName(executor.getName())
            .update();
        SkullMeta meta = (SkullMeta) builder.getMeta();
        meta.setOwningPlayer(player);

        ItemPack pack = new ItemPack(i, builder, event -> {
          if(event.getAction().equals(InventoryAction.PICKUP_ALL)) {
            if(!this.workers.contains(this.plugin.getUserManager().getUser(event.getCurrentItem().getItemMeta().getDisplayName()).get())) {
              this.workers.add(this.plugin.getUserManager().getUser(event.getCurrentItem().getItemMeta().getDisplayName()).get());
            } else {
              this.executor.sendMessage(colored("&8» &cTen uzytkownik znajduje sie juz w Twojej liscie pracownikow!"));
            }
          } else if(event.getAction().equals(InventoryAction.PICKUP_HALF)) {
            this.workers.remove(this.plugin.getUserManager().getUser(event.getCurrentItem().getItemMeta().getDisplayName()).get());
          } else {
            this.executor.sendMessage(colored("&8» &cTo nie jest prawidlowa akcja!"));
            this.executor.sendMessage(colored("&8» &cSprobuj uzyc LPM, badz PPM!"));
          }
        });

        packs.add(pack);
      }

      packs.add(new ItemPack(53, new ItemBuilder(Material.BARRIER).setName(colored("&8» &aNastepny krok!")).update(), event -> new CompanyConfigurator(this.name, this.trade, this.workers, this.user).nextStep(this.executor, MakeStatus.CONFIRMATION)));
    }
    return packs;
  }

  @Override
  public void onOpen(InventoryOpenEvent inventoryOpenEvent) {
    if(!this.user.getMakingStatus().equals(MakeStatus.WORKERS)) {
      this.user.setMakingStatus(MakeStatus.WORKERS);
    }
  }

  @Override
  public void onClose(InventoryCloseEvent inventoryCloseEvent) {
    this.user.setMakingStatus(MakeStatus.CONFIRMATION);
  }
}
