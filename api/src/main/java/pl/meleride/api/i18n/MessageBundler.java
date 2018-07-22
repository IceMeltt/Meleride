package pl.meleride.api.i18n;

import org.apache.commons.lang.Validate;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.api.MelerideApi;

public final class MessageBundler {

  public static MessageBuilder create(String key) {
    Validate.notNull(key, "Key cannot be null!");

    return create(MelerideApi.getPlugin(MelerideApi.class), key);
  }

  public static MessageBuilder create(JavaPlugin plugin, String key) {
    Validate.notNull(plugin, "Plugin cannot be null!");
    Validate.notNull(key, "Key cannot be null!");

    return new MessageBuilder(plugin, key);
  }

  private MessageBundler() {}

}
