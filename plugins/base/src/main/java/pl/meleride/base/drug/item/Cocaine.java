package pl.meleride.base.drug.item;

import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.Validate;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.meleride.api.builder.ItemBuilder;
import pl.meleride.api.i18n.MessageBundler;
import pl.meleride.api.object.system.AbstractItem;
import pl.meleride.base.MelerideBase;
import pl.meleride.base.drug.DrugListener;
import pl.meleride.base.drug.DrugShop;

public class Cocaine extends AbstractItem {

  private final MelerideBase plugin;

  public Cocaine(MelerideBase plugin) {
    super("drug_cocaine");
    this.plugin = plugin;
    initialize();
  }

  private final String usage = MessageBundler.create("drugtype.cocaine").toString();
  private final int prize = Integer.parseInt(MessageBundler.create("drugprize.cocaine").toString());
  private final Set<PotionEffect> effects = new HashSet<>();

  //---

  private void initialize() {
    this.effects.add(new PotionEffect(PotionEffectType.SPEED, 480 * 20, 2));
    this.effects.add(new PotionEffect(PotionEffectType.FAST_DIGGING, 480 * 20, 3));
  }

  //---

  private ItemStack itemStack = new ItemBuilder(Material.SUGAR)
      .setDamage(0)
      .setName("Kokaina")
      .build();

  private Set<PotionEffect> getEffects() {
    return this.effects;
  }

  private String getUsage() {
    return this.usage;
  }

  private int getPrize() {
    return this.prize;
  }

  @Override
  public ItemStack getItemStack() {
    return this.itemStack;
  }

  //---

  @Override
  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent e) {
    Player player = e.getPlayer();
    DrugListener listener = new DrugListener(this.plugin);

    listener.invoke(player, this, this.getEffects(), this.getUsage(), e);
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent e) {
    Player player = (Player) e.getWhoClicked();
    DrugListener listener = new DrugListener(this.plugin);

    listener.invoke(player, this, new DrugShop(), e);
  }

}
