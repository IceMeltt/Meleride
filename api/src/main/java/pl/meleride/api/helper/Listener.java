package pl.meleride.api.helper;

import org.bukkit.event.Event;

public interface Listener<T extends Event> extends org.bukkit.event.Listener {

  void performEvent(T event);

}
