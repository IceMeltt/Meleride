package pl.meleride.companies.command;

import static pl.meleride.api.message.MessageUtil.colored;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.meleride.commands.context.CommandContext;
import pl.meleride.companies.MelerideCompanies;
import pl.meleride.companies.entity.Company;

final class CompanyListCommand {

  private final MelerideCompanies plugin;

  CompanyListCommand(MelerideCompanies plugin) {
    this.plugin = plugin;
  }

  void execute(CommandSender commandSender, CommandContext context) {
    Player player = (Player) commandSender;

    StringBuilder sb = new StringBuilder()
        .append(colored("&8» &7Aktualna lista zalozonych firm:"));
    for(Company company : this.plugin.getCompanyManager().getAllCompanies()) {
      sb.append(colored("&7» &e" + company.getName().get() + "\n"));
    }
    player.sendMessage(sb.toString());
  }

}
