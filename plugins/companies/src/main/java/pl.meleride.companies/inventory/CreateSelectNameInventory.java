package pl.meleride.companies.inventory;

import static pl.meleride.api.message.MessageUtil.colored;

import java.util.Collections;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.companies.MelerideCompanies;
import pl.meleride.companies.configurator.CompanyConfigurator;
import pl.meleride.companies.entity.User;
import pl.meleride.companies.enums.MakeStatus;
import pl.meleride.companies.enums.Trade;

public class CreateSelectNameInventory {

  private MelerideCompanies plugin = JavaPlugin.getPlugin(MelerideCompanies.class);
  private Player player;
  private User user;

  public CreateSelectNameInventory(Player player) {
    this.player = player;
    this.user = this.plugin.getUserManager().getUser(player).get();
  }

  public void execute() {
    if(!this.user.getMakingStatus().equals(MakeStatus.NAME)) {
      this.user.setMakingStatus(MakeStatus.NAME);
    }

    new AnvilGUI(this.plugin, player, colored("Wpisz nazwe firmy"), (creator, reply) -> {
      if(this.plugin.getCompanyManager().getCompany(reply).isPresent()) {
        creator.sendMessage(colored("&8» &cFirma o tej nazwie juz istnieje!"));
        return null;
      }

      creator.playSound(creator.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 70.0F, 1.0F);
      new CompanyConfigurator(reply, null, Collections.emptyList(), user).nextStep(this.player, MakeStatus.TRADE);
      return "";
    });
  }

}
