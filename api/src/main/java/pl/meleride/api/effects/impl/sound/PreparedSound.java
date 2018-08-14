package pl.meleride.api.effects.impl.sound;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import pl.meleride.api.effects.impl.AbstractPlayableEffect;

public class PreparedSound extends AbstractPlayableEffect {
  
  private final Sound sound;
  private SoundCategory soundCategory;
  private float volume;
  private float pitch;
  
  public PreparedSound(Sound sound) {
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
    Bukkit.getOnlinePlayers().stream()
        .filter((p) -> p.getLocation().distance(location) <= 150)
        .forEach((p) -> this.play(p, location));
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
  public PreparedSound clone() {
    PreparedSound clone = new PreparedSound(this.sound);
    clone.soundCategory = this.soundCategory;
    clone.volume = this.volume;
    clone.pitch = this.pitch;
    return clone;
  }
  
}
