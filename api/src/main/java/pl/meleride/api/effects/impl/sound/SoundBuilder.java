package pl.meleride.api.effects.impl.sound;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import pl.meleride.api.helper.Buildable;

public final class SoundBuilder implements Buildable<PreparedSound> {
  
  private final PreparedSound preparedSound;
  
  public SoundBuilder(Sound sound) {
    this.preparedSound = new PreparedSound(sound);
  }
  
  public SoundBuilder(PreparedSound preparedSound) {
    this.preparedSound = preparedSound;
  }
  
  public SoundBuilder setCategory(SoundCategory soundCategory) {
    this.preparedSound.setSoundCategory(soundCategory);
    return this;
  }
  
  public SoundBuilder setVolume(float volume) {
    this.preparedSound.setVolume(volume);
    return this;
  }
  
  public SoundBuilder setPitch(float pitch) {
    this.preparedSound.setPitch(pitch);
    return this;
  }
  
  @Override
  public PreparedSound build() {
    return this.preparedSound.clone();
  }
  
}
