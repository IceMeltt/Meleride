package pl.meleride.economy.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MathUtils {

  public static double round(double value, int places) {
    if (places < 0) {
      throw new IllegalArgumentException();
    }

    return new BigDecimal(value).setScale(places, RoundingMode.HALF_DOWN).doubleValue();
  }

  private MathUtils() {
  }

}
