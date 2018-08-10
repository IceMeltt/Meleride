package pl.meleride.api.effects.impl.sound;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import pl.meleride.api.helper.Buildable;

public final class SoundBuilder implements Buildable<SoundObject> {
  
  private final SoundObject soundObject;
  
  public SoundBuilder(Sound sound) {
    this.soundObject = new SoundObject(sound);
  }
  
  public SoundBuilder(SoundObject soundObject) {
    this.soundObject = soundObject;
  }
  
  public SoundBuilder category(SoundCategory soundCategory) {
    this.soundObject.setSoundCategory(soundCategory);
    return this;
  }
  
  public SoundBuilder volume(float volume) {
    this.soundObject.setVolume(volume);
    return this;
  }
  
  public SoundBuilder pitch(float pitch) {
    this.soundObject.setPitch(pitch);
    return this;
  }
  
  @Override
  public SoundObject build() {
    return this.soundObject.clone();
  }
  
}
