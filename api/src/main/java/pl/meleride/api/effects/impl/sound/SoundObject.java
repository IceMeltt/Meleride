package pl.meleride.api.effects.impl.sound;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import pl.meleride.api.effects.PlayerPlayable;
import pl.meleride.api.effects.WorldPlayable;

public class SoundObject implements WorldPlayable, PlayerPlayable, Cloneable {
  
  private final Sound sound;
  private SoundCategory soundCategory;
  private float volume;
  private float pitch;
  
  public SoundObject(Sound sound) {
    this.sound = sound;
    this.soundCategory = SoundCategory.NEUTRAL;
    this.volume = 1;
    this.pitch = 1;
  }
  
  public Sound getSound() {
    return this.sound;
  }
  
  public SoundCategory getSoundCategory() {
    return this.soundCategory;
  }
  
  public void setSoundCategory(SoundCategory soundCategory) {
    this.soundCategory = soundCategory;
  }
  
  public float getVolume() {
    return this.volume;
  }
  
  public void setVolume(float volume) {
    this.volume = volume;
  }
  
  public float getPitch() {
    return this.pitch;
  }
  
  public void setPitch(float pitch) {
    this.pitch = pitch;
  }
  
  @Override
  public void play(Location location) {
    location.getWorld().playSound(location,
        this.sound,
        this.soundCategory,
        this.volume,
        this.pitch);
  }
  
  @Override
  public void play(Player player, Location location) {
    player.playSound(location,
        this.sound,
        this.soundCategory,
        this.volume,
        this.pitch);
  }
  
  @Override
  public SoundObject clone() {
    SoundObject soundObject = new SoundObject(this.sound);
    soundObject.soundCategory = this.soundCategory;
    soundObject.volume = this.volume;
    soundObject.pitch = this.pitch;
    return this;
  }
  
}
