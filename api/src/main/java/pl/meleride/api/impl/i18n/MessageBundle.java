package pl.meleride.api.impl.i18n;

import org.apache.commons.lang.Validate;

public final class MessageBundle {

  public static MessageBuilder create(String key) {
    Validate.notNull(key, "Key cannot be null!");

    return new MessageBuilder(key);
  }

  private MessageBundle() {}

}
