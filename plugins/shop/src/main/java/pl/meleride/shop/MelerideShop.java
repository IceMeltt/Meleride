package pl.meleride.shop;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.shop.listener.PlayerListeners;
import pl.meleride.shop.shop.ShopManager;
import pl.meleride.shop.user.UserManager;

public final class MelerideShop extends JavaPlugin {

  private ShopManager shopManager;
  private UserManager userManager;

  private WorldEditPlugin worldEdit;

  @Override
  public void onEnable() {
    if (!this.getServer().getPluginManager().isPluginEnabled("HologramAPI")) {
      this.getLogger().warning(
          "Plugin \"HologramAPI\" or \"WorldEdit\" not found! Install it, and then enable me, "
              + "i will not work without this plugins, i need holograms and regions api!");
      this.getPluginLoader().disablePlugin(this);
      return;
    }
    this.worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");


    this.userManager = new UserManager();
    this.shopManager = new ShopManager(this);

    this.saveDefaultConfig();
    this.shopManager.load();

    this.getServer().getPluginManager().registerEvents(
        new PlayerListeners(this.shopManager, this.userManager),
        this);
  }

  @Override
  public void onDisable() {
  }

  public WorldEditPlugin getWorldEditPlugin() {
    return worldEdit;
  }

  public UserManager getUserManager() {
    return this.userManager;
  }

  public ShopManager getShopManager() {
    return this.shopManager;
  }

}
