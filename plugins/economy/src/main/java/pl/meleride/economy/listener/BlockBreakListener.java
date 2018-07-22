package pl.meleride.economy.listener;

import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pl.meleride.economy.MelerideEconomy;

public class BlockBreakListener implements Listener {

  private final MelerideEconomy plugin;

  public BlockBreakListener(final MelerideEconomy plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    if (!(event.getBlock().getState() instanceof Sign)) {
      return;
    }

    Sign sign = (Sign) event.getBlock().getState();

    if (!this.plugin.getCurrencySignManager().getCurrencySign(sign.getLocation()).isPresent()) {
      return;
    }

    this.plugin.getCurrencySignManager().getCurrencySign(sign.getLocation()).get().delete(this.plugin);
  }

}
