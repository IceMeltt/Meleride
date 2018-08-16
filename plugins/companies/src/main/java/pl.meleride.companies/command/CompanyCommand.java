package pl.meleride.companies.command;

import org.bukkit.command.CommandSender;
import pl.meleride.commands.CommandInfo;
import pl.meleride.commands.context.CommandContext;
import pl.meleride.companies.MelerideCompanies;

public final class CompanyCommand {

  private final MelerideCompanies plugin;

  public CompanyCommand(MelerideCompanies plugin) {
    this.plugin = plugin;
  }

  @CommandInfo(name = "company", userOnly = true, permission = "meleride.companies.companies", usage = "[create/list]", min = 1)
  public void companyCommnad(CommandSender commandSender, CommandContext context) {
    switch (context.getParam(0)) {
      case "create":
        new CompanyCreateCommand(this.plugin).execute(commandSender, context);
      case "list":
        new CompanyListCommand(this.plugin).execute(commandSender, context);
    }
  }

}
