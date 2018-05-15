package pl.meleride.api;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.api.impl.i18n.MessageBundle;
import pl.meleride.api.impl.listener.PlayerLoginListener;
import pl.meleride.api.impl.listener.PlayerPreLoginListener;
import pl.meleride.api.impl.listener.PlayerQuitListener;
import pl.meleride.api.impl.manager.UserManagerImpl;
import pl.meleride.api.impl.type.MessageType;
import pl.meleride.api.manager.UserManager;
import pl.meleride.commands.Commands;
import pl.meleride.commands.bukkit.BukkitCommands;

public final class MelerideAPI extends JavaPlugin implements CommandExecutor {

  private UserManager userManager;
  private Commands commands;

  @Override
  public void onEnable() {
    this.userManager = new UserManagerImpl(this);
    this.commands = new BukkitCommands(this);

    this.registerListeners(
        new PlayerPreLoginListener(this),
        new PlayerLoginListener(this),
        new PlayerQuitListener(this)
    );

    this.getCommand("test").setExecutor(this);

  }

  private void registerListeners(Listener... listeners) {
    for (Listener listener : listeners) {
      this.getServer().getPluginManager().registerEvents(listener, this);
    }
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    MessageBundle.create("duzy.chuj")
        .withField("CHUJ", "DUZY CHUJ")
        .withField("TEST", "DUZY TEST")
        .target(MessageType.CHAT)
        .sendTo((Player) sender);

    MessageBundle.create("duzy.chuj")
        .withField("TEST", "DUZY TEST")
        .target(MessageType.ACTION_BAR)
        .sendTo((Player) sender);

    MessageBundle.create("duzy.chuj")
        .withField("TEST", "DUZY TEST")
        .target(MessageType.SUB_TITLE)
        .sendTo((Player) sender);

    System.out.println(MessageBundle.create("duzy.chuj").withField("TEST", "DUZY TEST").toString());
    return false;
  }

  public Commands getCommands() {
    return this.commands;
  }

  public UserManager getUserManager() {
    return this.userManager;
  }

}
