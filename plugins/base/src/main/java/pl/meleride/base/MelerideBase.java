package pl.meleride.base;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.TraitInfo;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import pl.meleride.api.object.system.ItemRegistrator;
import pl.meleride.base.impl.drug.DrugShop;
import pl.meleride.base.impl.drug.DrugTrait;
import pl.meleride.base.impl.drug.item.Cannabis;
import pl.meleride.base.impl.drug.item.Cocaine;
import pl.meleride.base.impl.drug.item.Heroine;
import pl.meleride.base.impl.drug.item.MDMA;

public class MelerideBase extends JavaPlugin {

  private Map<UUID, Long> userUsing; //TODO wynocha so schnell wie möglich (ง ͠° ͟ل͜ ͡°)ง!
  private DrugShop drugShop;

  @Override
  public void onEnable() {
    userUsing = new HashMap<>();

    getLogger().info("Rejestrowanie przedmiotow...");
    ItemRegistrator.register(new MDMA(),
        new Cannabis(),
        new Heroine(),
        new Cocaine());

    getLogger().info("Przygotowywanie GUI...");
    drugShop = new DrugShop();
    this.drugShop.initialize();

    getLogger().info("Rejestrowanie NPC Traitow...");
    CitizensAPI.getTraitFactory()
        .registerTrait(TraitInfo.create(DrugTrait.class).withName("dealerTrait"));
  }

  @Override
  public void onDisable() {
    Bukkit.getScheduler().cancelAllTasks();
  }

  public DrugShop getDrugShop() {
    return drugShop;
  }

  public Map<UUID, Long> getUserUsing() {
    return userUsing;
  }

}
