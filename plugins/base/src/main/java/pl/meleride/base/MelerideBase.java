package pl.meleride.base;

import ch.jalu.injector.Injector;
import ch.jalu.injector.InjectorBuilder;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.TraitInfo;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import pl.meleride.base.impl.drug.DrugListener;
import pl.meleride.base.impl.drug.DrugShop;
import pl.meleride.base.impl.drug.DrugTrait;


public class MelerideBase extends JavaPlugin {

  private DrugShop drugShop;
  private Injector injector;

  @Override
  public void onEnable() {
    getLogger().info("Rejestrowanie instancji...");
    this.injector = new InjectorBuilder().addDefaultHandlers("pl.meleride.base").create();
    this.injector.register(MelerideBase.class, this);
    this.injector.register(Server.class, this.getServer());

    this.injector.register(DrugShop.class, new DrugShop());

    getLogger().info("Przygotowywanie GUI...");
    this.drugShop = injector.getSingleton(DrugShop.class);
    this.drugShop.initialize();

    getLogger().info("Rejestrowanie NPC Traitow...");
    CitizensAPI.getTraitFactory()
        .registerTrait(TraitInfo.create(DrugTrait.class).withName("dealerTrait"));

    getLogger().info("Rejestrowanie listenerow...");
    Bukkit.getPluginManager().registerEvents(this.injector.getSingleton(DrugListener.class), this);
  }

  @Override
  public void onDisable() {
    Bukkit.getScheduler().cancelAllTasks();
  }

  public DrugShop getDrugShop() {
    return drugShop;
  }
}
