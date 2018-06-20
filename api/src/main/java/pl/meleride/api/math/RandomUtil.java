package pl.meleride.api.math;

import org.apache.commons.lang.Validate;
import java.util.Random;

public final class RandomUtil {

  private static final Random random = new Random();

  public static int nextInt(int min, int max) throws IllegalArgumentException {
    Validate.isTrue(max > min, "Max can't be smaller than min");

    return random.nextInt(max - min + 1) + min;
  }

  public static double nextDouble(double min, double max) throws IllegalArgumentException {
    Validate.isTrue(max > min, "Max can't be smaller than min");

    return random.nextDouble() * (max - min) + min;
  }

  private RandomUtil() { }

}