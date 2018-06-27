package pl.meleride.economy.listener;

import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pl.meleride.economy.currencysign.CurrencySign;
import pl.meleride.economy.currencysign.CurrencySignManager;

public class BlockBreakListener implements Listener {

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    if (!(event.getBlock().getState() instanceof Sign)) {
      return;
    }

    Sign sign = (Sign) event.getBlock().getState();

    if (!CurrencySignManager.getCurrencySign(sign.getLocation()).isPresent()) {
      return;
    }

    CurrencySignManager.getCurrencySign(sign.getLocation()).get().delete();
  }

}
