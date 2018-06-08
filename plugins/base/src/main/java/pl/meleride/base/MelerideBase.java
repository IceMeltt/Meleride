package pl.meleride.base;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.TraitInfo;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import pl.meleride.base.impl.drug.DrugListener;
import pl.meleride.base.impl.drug.DrugShop;
import pl.meleride.base.impl.drug.DrugTrait;


public class MelerideBase extends JavaPlugin {

  private DrugShop drugShop;

  @Override
  public void onEnable() {
    getLogger().info("Przygotowywanie GUI...");
    drugShop = new DrugShop();
    this.drugShop.initialize();

    getLogger().info("Rejestrowanie NPC Traitow...");
    CitizensAPI.getTraitFactory()
        .registerTrait(TraitInfo.create(DrugTrait.class).withName("dealerTrait"));

    getLogger().info("Rejestrowanie listenerow...");
    Bukkit.getPluginManager().registerEvents(new DrugListener(), this);
  }

  @Override
  public void onDisable() {
    Bukkit.getScheduler().cancelAllTasks();
  }

  public DrugShop getDrugShop() {
    return drugShop;
  }
}
