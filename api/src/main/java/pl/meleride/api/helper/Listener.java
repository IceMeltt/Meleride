package pl.meleride.api.helper;

import org.bukkit.event.Event;
import pl.meleride.api.storage.StorageException;

public interface Listener<T extends Event> extends org.bukkit.event.Listener {

  void performEvent(T event) throws StorageException;

}
