package pl.meleride.companies.command;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.meleride.commands.context.CommandContext;
import pl.meleride.companies.MelerideCompanies;
import pl.meleride.companies.entity.Company;
import pl.meleride.companies.entity.User;
import pl.meleride.companies.entity.impl.CompanyBuilder;

class CompanyCreateCommand {

  private final MelerideCompanies plugin;

  CompanyCreateCommand(MelerideCompanies plugin) {
    this.plugin = plugin;
  }

  void execute(CommandSender commandSender, CommandContext context) {
    Player player = (Player) commandSender;
    User user = this.plugin.getUserManager().getUser(player.getUniqueId()).get();
    AtomicReference<String> companyName = new AtomicReference<>("");

    new AnvilGUI(this.plugin, (Player) commandSender, "Wpisz nazwę firmy", (creator, reply) -> {
      companyName.set(reply);
      return null;
    });

    Company company = new CompanyBuilder()
        .withIdentifier(UUID.randomUUID())
        .withName(companyName.get())
        .withOwner(user)
        .withLevel(0)
        .withWorkers(Collections.singletonList(user))
        .build();
  }

}

