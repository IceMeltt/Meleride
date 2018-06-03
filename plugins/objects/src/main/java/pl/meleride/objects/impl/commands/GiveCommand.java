package pl.meleride.objects.impl.commands;

import static pl.meleride.api.message.MessageUtil.colored;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.meleride.api.i18n.MessageBundler;
import pl.meleride.api.message.MessageType;
import pl.meleride.objects.impl.system.AbstractItem;
import pl.meleride.objects.impl.system.ItemManager;
import pl.meleride.objects.impl.system.ItemRegistrator;

public class GiveCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if(!(sender instanceof Player)) {
      Bukkit.getLogger().info(MessageBundler.create("command.onlyplayer").toString());
      return true;
    }

    Player player = (Player) sender;

    if(!(player.hasPermission("meleride.admin") || player.hasPermission("meleride.admin.daj"))) {
      MessageBundler.create("command.nopermission").target(MessageType.CHAT).sendTo(player);
      return true;
    }

    if(!ItemRegistrator.checkIfExist(args[0])) {
      player.sendMessage(colored("&8» &cNie znaleziono przedmiotu &7" + args[0] + "&c w bazie!"));
    } else {

      AbstractItem item = ItemManager.getObject(args[0]);
      player.getInventory().addItem(item.getItemStack());
      player.sendMessage(colored("&8» &aOtrzymano item &7" + args[0] + "&a!"));
    }

    return false;
  }
}
