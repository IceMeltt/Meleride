package pl.meleride.api.particles.animation;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import pl.meleride.api.helper.Provider;

public abstract class ParticleAnimation {

  private final Provider<Location> locationProvider;
  private boolean running;
  private long frameDelay;
  private long frameLimit;
  private long frameCounter;

  protected ParticleAnimation(Provider<Location> locationProvider, long frameDelay, long frameLimit) {
    Validate.isTrue(frameDelay > 0, "Frame delay must be greater than 0!");
    this.locationProvider = locationProvider;
    this.frameDelay = frameDelay;
    this.frameLimit = frameLimit;
    this.frameCounter = 0;
    this.running = true;
  }

  public void update() {
    if (!this.locationProvider.isAvailable()) {
      this.stop();
      return;
    }

    if (++this.frameCounter % this.frameDelay == 0)
      this.renderFrame();

    if (this.frameCounter / this.frameDelay == this.frameLimit)
      this.stop();
  }

  protected abstract void renderFrame();

  public Provider<Location> getLocationProvider() {
    return this.locationProvider;
  }

  public long getFrameDelay() {
    return this.frameDelay;
  }

  public long getFrameLimit() {
    return this.frameLimit;
  }

  public long getCurrentFrame() {
    return this.frameCounter / this.frameDelay;
  }

  public void stop() {
    this.running = false;
  }

  public boolean isRunning() {
    return this.running;
  }

}
