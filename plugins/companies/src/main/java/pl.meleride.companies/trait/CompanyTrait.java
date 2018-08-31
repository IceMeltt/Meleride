package pl.meleride.companies.trait;

import static pl.meleride.api.message.MessageUtil.colored;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.companies.MelerideCompanies;

@TraitName(value = "company_trait")
public class CompanyTrait extends Trait {

  private final MelerideCompanies main = JavaPlugin.getPlugin(MelerideCompanies.class);

  public CompanyTrait() {
    super("company_trait");
  }

  @EventHandler
  public void onClick(NPCRightClickEvent e) {
    Player player = e.getClicker();

    if (e.getNPC().equals(this.getNPC())) {
      if (!e.getNPC().getName().equalsIgnoreCase(colored("&aUrzad pracy"))) {
        return;
      }

      player.performCommand("company create");
    }
  }

}
