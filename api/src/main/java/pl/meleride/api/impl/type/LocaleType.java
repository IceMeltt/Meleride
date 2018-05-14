package pl.meleride.api.impl.type;

import java.util.Locale;

public enum LocaleType {

  DEFAULT(new Locale("pl", "PL"));

  private final Locale locale;

  LocaleType(Locale locale) {
    this.locale = locale;
  }

  public Locale getLocale() {
    return this.locale;
  }

}
