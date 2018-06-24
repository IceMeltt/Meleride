package pl.meleride.api.util;

import org.apache.commons.lang.Validate;

public class EnumUtils {

  public static <T extends Enum<T>> T getEnumFromString(Class<T> clazz, String string) {
    Validate.notNull(clazz, "Class parameter cannot be null!");
    Validate.notNull(string, "String cannot be null!");

    return Enum.valueOf(clazz, string);
  }

  private EnumUtils() {
  }

}
