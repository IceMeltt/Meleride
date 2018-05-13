package pl.meleride.api.impl.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class EventImpl extends Event implements Cancellable {

  private static final HandlerList HANDLER_LIST = new HandlerList();
  private boolean cancelled;

  @Override
  public boolean isCancelled() {
    return this.cancelled;
  }

  @Override
  public void setCancelled(boolean cancelled) {
    this.cancelled = cancelled;
  }

  @Override
  public HandlerList getHandlers() {
    return HANDLER_LIST;
  }

  public static HandlerList getHandlerList() {
    return HANDLER_LIST;
  }

}
