package pl.meleride.economy.currency;

public enum Tendency {

  DECREASE("&c\\/"),
  INCREASE("&a/\\"),
  STATIC("&7-");

  private final String sign;

  Tendency(String sign) {
    this.sign = sign;
  }

  public String getSign() {
    return sign;
  }

}
