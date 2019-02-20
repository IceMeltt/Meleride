package pl.meleride.cars.util;

public final class SpeedConverter {

  public static double convert(double minecraftSpeed) {
    return minecraftSpeed * 50D; //1 minecraft speed unit = 50km/h - 300iq converter
  }

  private SpeedConverter() {
  }

}
