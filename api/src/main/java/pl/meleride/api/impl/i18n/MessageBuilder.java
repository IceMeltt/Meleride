package pl.meleride.api.impl.i18n;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import pl.meleride.api.impl.type.LocaleType;
import pl.meleride.api.impl.type.MessageType;

import java.util.ResourceBundle;

public final class MessageBuilder {

  private final static ResourceBundle RESOURCE_BUNDLE;

  private final String key;
  private String messageContent;

  static {
    RESOURCE_BUNDLE = ResourceBundle.getBundle("messages", LocaleType.DEFAULT.getLocale());
  }

  MessageBuilder(String key) {
    this.key = key;
    this.messageContent = RESOURCE_BUNDLE.getString(key);
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

}
