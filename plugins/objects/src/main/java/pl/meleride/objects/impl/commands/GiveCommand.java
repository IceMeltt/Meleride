package pl.meleride.objects.impl.commands;

import static pl.meleride.api.message.MessageUtil.colored;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.meleride.commands.CommandInfo;
import pl.meleride.commands.context.CommandContext;
import pl.meleride.commands.util.CommandUtils;
import pl.meleride.objects.impl.system.AbstractItem;
import pl.meleride.objects.impl.system.ItemManager;
import pl.meleride.objects.impl.system.ItemRegistrator;

public class GiveCommand {

  @CommandInfo(
      name = "daj",
      description = "Komenda pozwalająca na przydzielanie graczom danych rzeczy.",
      permission = "meleride.admin.daj",
      min = 1,
      usage = "/daj [nazwa]",
      userOnly = true)
  public void giveCommand(CommandSender sender, CommandContext context) {
    Player player = CommandUtils.toPlayer(sender);

    if(!ItemRegistrator.checkIfExist(context.getParam(0))) {
      player.sendMessage(colored("&8» &cNie znaleziono przedmiotu &7" + context.getParam(0) + "&c w bazie!"));
    } else {
      AbstractItem item = ItemManager.getObject(context.getParam(0));
      player.getInventory().addItem(item.getItemStack());
      player.sendMessage(colored("&8» &aOtrzymano item &7" + context.getParam(0) + "&a!"));
    }
  }
}
