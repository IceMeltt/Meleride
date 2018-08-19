package pl.meleride.api.playables;

public final class TimeUnit implements Cloneable {
  
  private float time;
  
  private TimeUnit(float time) {
    this.time = time;
  }
  
  public float asSeconds() {
    return this.time;
  }
  
  public float asMinutes() {
    return this.time / 60.0f;
  }
  
  public float asHours() {
    return this.time / 3600.0f;
  }
  
  public long asLongTicks() {
    return Math.round(this.time * 20);
  }
  
  public int asIntTicks() {
    return Math.round(this.time * 20);
  }
  
  public void add(TimeUnit unit) {
    this.time += unit.time;
  }
  
  public void subtract(TimeUnit unit) {
    this.time -= unit.time;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj.getClass() != TimeUnit.class)
      return false;
    return Math.abs(this.time - ((TimeUnit) obj).time) <= 0.01;
  }
  
  @Override
  public TimeUnit clone() {
    return new TimeUnit(this.time);
  }
  
  public static TimeUnit seconds(float time) {
    return new TimeUnit(time);
  }
  
  public static TimeUnit ticks(int time) {
    return new TimeUnit(time / 20.0f);
  }
  
  public static TimeUnit ticks(long time) {
    return new TimeUnit(time / 20.0f);
  }
  
  public static TimeUnit zero() {
    return new TimeUnit(0);
  }
  
}
