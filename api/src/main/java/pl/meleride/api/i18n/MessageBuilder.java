package pl.meleride.api.i18n;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.api.message.MessageType;

public final class MessageBuilder {

  private String messageContent;

  MessageBuilder(JavaPlugin plugin, String key) {
    this.messageContent = plugin.getConfig().getString(key);
  }

  public MessageBuilder withField(String field, String value) {
    Validate.notNull(field, "Field cannot be null!");
    Validate.notNull(value, "Value cannot be null!");

    this.messageContent = StringUtils.replace(this.messageContent, "{" + field + "}", value);
    return this;
  }

  public MessageSender target(MessageType messageType) {
    Validate.notNull(messageType, "Message type cannot be null!");

    return new MessageSender(messageType, this.messageContent);
  }

  public String toString() {
    return this.messageContent;
  }

}
