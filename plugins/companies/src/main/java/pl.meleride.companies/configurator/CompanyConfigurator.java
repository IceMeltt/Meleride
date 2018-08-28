package pl.meleride.companies.configurator;

import com.google.common.annotations.Beta;
import java.util.List;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.companies.MelerideCompanies;
import pl.meleride.companies.entity.Company;
import pl.meleride.companies.entity.User;
import pl.meleride.companies.entity.impl.CompanyBuilder;
import pl.meleride.companies.enums.MakeStatus;
import pl.meleride.companies.enums.Trade;
import pl.meleride.companies.inventory.CreateConfirmationInventory;
import pl.meleride.companies.inventory.CreateSelectNameInventory;
import pl.meleride.companies.inventory.CreateSelectTradeInventory;
import pl.meleride.companies.inventory.CreateWorkersInventory;

@Beta
public class CompanyConfigurator {

  private MelerideCompanies plugin = JavaPlugin.getPlugin(MelerideCompanies.class);
  private String name;
  private Trade trade;
  private List<User> workers;
  private User owner;

  public CompanyConfigurator(String name, Trade trade, List<User> workers, User owner) {
    this.name = name;
    this.trade = trade;
    this.workers = workers;
    this.owner = owner;
  }

  public void nextStep(Player player, MakeStatus step) {
    switch (step) {
      case NOT_CREATING:
        throw new UnsupportedOperationException("Can't step onto NOT_CREATING case.");
      case NAME:
        new CreateSelectNameInventory(player).execute();
        break;
      case TRADE:
        CreateSelectTradeInventory trade = new CreateSelectTradeInventory(player, this.name);
        trade.openInventory(player);
        break;
      case WORKERS:
        CreateWorkersInventory workers = new CreateWorkersInventory(player, this.name, this.trade);
        workers.openInventory(player);
        break;
      case CONFIRMATION:
        CreateConfirmationInventory confirmation = new CreateConfirmationInventory(player, this.name, this.trade, this.workers);
        confirmation.openInventory(player);
        break;
      default:
        new CreateSelectNameInventory(player).execute();
        break;
    }
  }

  public void build() {
    Company preparedCompany = new CompanyBuilder()
        .withName(this.name)
        .withIdentifier(UUID.nameUUIDFromBytes(this.name.getBytes()))
        .withBusiness(this.trade.getName())
        .withWorkers(this.workers)
        .withOwner(this.owner)
        .withLevel(0)
        .build();

    this.plugin.getCompanyManager().addCompany(preparedCompany);
  }

}
