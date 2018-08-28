package pl.meleride.world.impl.weather.types;

import pl.meleride.api.i18n.MessageBundler;
import pl.meleride.world.impl.weather.types.setters.RainyWeatherSetter;
import pl.meleride.world.impl.weather.types.setters.SunnyWeatherSetter;
import pl.meleride.world.impl.weather.types.setters.ThunderingWeatherSetter;
import pl.meleride.world.impl.weather.types.setters.WeatherSetter;

import java.util.HashMap;
import java.util.Map;

public enum WeatherType {

  TORNADO(0, new ThunderingWeatherSetter()),
  TROPICAL_STORM(1, new ThunderingWeatherSetter()),
  HURRICANE(2, new RainyWeatherSetter()),
  SEVERE_THUNDERSTORMS(3, new ThunderingWeatherSetter()),
  THUNDERSTORMS(4, new ThunderingWeatherSetter()),
  MIXED_RAIN_AND_SNOW(5, new RainyWeatherSetter()),
  MIXED_RAIN_AND_SLEET(6, new RainyWeatherSetter()),
  MIXED_SNOW_AND_SLEET(7, new RainyWeatherSetter()),
  FREEZING_DRIZZLE(8, new RainyWeatherSetter()),
  DRIZZLE(9, new RainyWeatherSetter()),
  FREEZING_RAIN(10, new RainyWeatherSetter()),
  SHOWERS(11, new RainyWeatherSetter()),
  SNOW_FLURRIES(13, new RainyWeatherSetter()),
  LIGHT_SNOW_SHOWERS(14, new RainyWeatherSetter()),
  BLOWING_SNOW(15, new RainyWeatherSetter()),
  SNOW(16, new RainyWeatherSetter()),
  HAIL(17, new RainyWeatherSetter()),
  SLEET(18, new RainyWeatherSetter()),
  DUST(19, new SunnyWeatherSetter()),
  FOGGY(20, new SunnyWeatherSetter()),
  HAZE(21, new SunnyWeatherSetter()),
  SMOKY(22, new SunnyWeatherSetter()),
  BLUSTERY(23, new SunnyWeatherSetter()),
  WINDY(24, new SunnyWeatherSetter()),
  COLD(25, new SunnyWeatherSetter()),
  CLOUDY(26, new SunnyWeatherSetter()),
  MOSTLY_CLOUDY_NIGHT(27, new SunnyWeatherSetter()),
  MOSTLY_CLOUDY_DAY(28, new SunnyWeatherSetter()),
  PARTLY_CLOUDY_NIGHT(29, new SunnyWeatherSetter()),
  PARTLY_CLOUDY_DAY(30, new SunnyWeatherSetter()),
  CLEAR_NIGHT(31, new SunnyWeatherSetter()),
  SUNNY(32, new SunnyWeatherSetter()),
  FAIR_NIGHT(33, new SunnyWeatherSetter()),
  FAIR_DAY(34, new SunnyWeatherSetter()),
  MIXED_RAIN_AND_HAIL(35, new RainyWeatherSetter()),
  HOT(36, new SunnyWeatherSetter()),
  ISOLATED_THUNDERSTORMS(37, new ThunderingWeatherSetter()),
  SCATTERED_THUNDERSTORMS(38, new ThunderingWeatherSetter()),
  SCATTERED_SHOWERS(40, new RainyWeatherSetter()),
  HEAVY_SNOW(41, new RainyWeatherSetter()),
  SCATTERED_SNOW_SHOWERS(42, new RainyWeatherSetter()),
  PARTLY_CLOUDY(44, new SunnyWeatherSetter()),
  THUNDERSHOWERS(45, new ThunderingWeatherSetter()),
  SNOW_SHOWERS(46, new RainyWeatherSetter()),
  ISOLATED_THUNDERSHOWERS(47, new ThunderingWeatherSetter()),
  NOT_AVAILABLE(3200, null);

  private static final Map<Integer, WeatherType> BY_CODE = new HashMap<>();

  static {
    for (WeatherType weather : values())
      BY_CODE.put(weather.getCode(), weather);

    BY_CODE.put(12, SHOWERS);
    BY_CODE.put(39, SCATTERED_THUNDERSTORMS);
    BY_CODE.put(43, HEAVY_SNOW);
  }

  private int code;
  private WeatherSetter setter;

  WeatherType(int code, WeatherSetter setter) {
    this.code = code;
    this.setter = setter;
  }

  public int getCode() {
    return this.code;
  }

  public void setWeather() {
    this.setter.setWeather();
  }

  public String getName() {
    return MessageBundler.create("weather." + this.name().toLowerCase()).toString();
  }

  public static WeatherType byCode(Integer code) {
    return BY_CODE.getOrDefault(code, null);
  }

}
