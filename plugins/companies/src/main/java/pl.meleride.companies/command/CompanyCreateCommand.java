package pl.meleride.companies.command;

import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.meleride.commands.context.CommandContext;
import pl.meleride.companies.MelerideCompanies;
import pl.meleride.companies.entity.User;

import static pl.meleride.api.message.MessageUtil.*;

final class CompanyCreateCommand {

  private final MelerideCompanies plugin;

  CompanyCreateCommand(MelerideCompanies plugin) {
    this.plugin = plugin;
  }

  void execute(CommandSender commandSender, CommandContext context) {
    Player player = (Player) commandSender;
    User user = this.plugin.getUserManager().getUser(player.getUniqueId()).get();

    new AnvilGUI(this.plugin, player, "Wpisz nazwę firmy", (creator, reply) -> {
      return colored("&8» &fPrzejdz dalej");
    });
  }

}

