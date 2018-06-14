package pl.meleride.objects.items.other.drugs;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.meleride.api.builder.item.ItemBuilder;
import pl.meleride.api.i18n.MessageBundler;
import pl.meleride.base.impl.drug.DrugShop;
import pl.meleride.objects.impl.system.AbstractItem;
import pl.meleride.objects.relationlisteners.other.DrugListener;

public class Cannabis extends AbstractItem {

  public Cannabis() {
    super("drug_cannabis");
    initialize();
  }

  private final String usage = MessageBundler.create("drugtype.cannabis").toString();
  private final int prize = Integer.parseInt(MessageBundler.create("drugprize.cannabis").toString());
  private final Set<PotionEffect> effects = new HashSet<>();

  //---

  private void initialize() {
    effects.add(new PotionEffect(PotionEffectType.JUMP, 480 * 20, 3));
    effects.add(new PotionEffect(PotionEffectType.HUNGER, 480 * 20, 1));
  }

  //---

  private ItemStack itemStack = new ItemBuilder(Material.INK_SACK)
        .setDamage((byte) 2)
      .setName("&2Marihuana")
      .build();

  private Set<PotionEffect> getEffects() {
    return effects;
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
    DrugListener listener = new DrugListener();

    listener.invoke(player, this, this.getEffects(), this.getUsage(), e);
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent e) {
    Player player = (Player) e.getWhoClicked();
    DrugListener listener = new DrugListener();

    listener.invoke(player, this, new DrugShop(), e);
  }

}
