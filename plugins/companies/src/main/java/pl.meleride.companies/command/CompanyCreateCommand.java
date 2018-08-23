package pl.meleride.companies.command;

import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.meleride.commands.context.CommandContext;
import pl.meleride.companies.MelerideCompanies;
import pl.meleride.companies.entity.User;
import pl.meleride.companies.inventory.CreateConfirmationInventory;

import static pl.meleride.api.message.MessageUtil.*;

final class CompanyCreateCommand {

  private final MelerideCompanies plugin;

  CompanyCreateCommand(MelerideCompanies plugin) {
    this.plugin = plugin;
  }

  void execute(CommandSender commandSender, CommandContext context) {
    Player player = (Player) commandSender;
    User user = this.plugin.getUserManager().getUser(player.getUniqueId()).get();

    new AnvilGUI(this.plugin, player, colored("Wpisz nazwe firmy"), (creator, reply) -> {
      if(this.plugin.getCompanyManager().getCompany(reply).isPresent()) {
        creator.sendMessage(colored("&8» &cFirma o tej nazwie juz istnieje!"));
        return null;
      }

      creator.playSound(creator.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 70.0F, 1.0F);
      new CreateConfirmationInventory(player, reply).openInventory(player);
      return "";
    });
  }

}

