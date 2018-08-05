package pl.meleride.shop;

import static pl.meleride.api.message.MessageUtil.colored;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.meleride.api.helper.Buildable;

public class InventoryBuilder implements Buildable<Inventory> {

  private String name = "Default name";
  private int size = 9 * 5;

  private final Map<Integer, ItemStack> items = new HashMap<>();

  public InventoryBuilder addItem(final int slot, final ItemStack item) {
    this.items.put(slot, item);
    return this;
  }

  public InventoryBuilder addItems(Map<Integer, ItemStack> items) {
    items.forEach(this.items::put);
    return this;
  }

  public InventoryBuilder setName(final String name) {
    this.name = name;
    return this;
  }

  public InventoryBuilder setSize(final int size) {
    this.size = size;
    return this;
  }

  @Override
  public Inventory build() {
    final Inventory inventory = Bukkit.createInventory(null, this.size, colored(this.name));
    this.items.forEach(inventory::setItem);
    return inventory;
  }

}
