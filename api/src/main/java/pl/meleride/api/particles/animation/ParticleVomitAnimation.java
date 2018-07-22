package pl.meleride.api.particles.animation;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;
import pl.meleride.api.helper.provider.PlayerEyeLocationProvider;
import pl.meleride.api.particles.object.ParticleBundler;

import java.util.Random;

public final class ParticleVomitAnimation extends ParticleAnimation {

  private final Vector currentPosition;
  private final Material vomitContent;
  private float yOffset;

  protected ParticleVomitAnimation(Player player, Material vomitContent) {
    super(new PlayerEyeLocationProvider(player), 1, 5);
    this.currentPosition = player.getLocation().getDirection();
    this.vomitContent = vomitContent;
    this.yOffset = -0.15f;
  }

  @Override
  protected void renderFrame() {
    this.currentPosition.multiply(1.35f).add(new Vector(0, yOffset, 0));
    this.yOffset *= 1.4f;

    Location particleLocation = super.getLocationProvider().get().clone().add(currentPosition);
    this.spawnParticleTemplate(particleLocation, getMaterialWithDataAsItemStack(Material.WOOD, (byte)13));
    this.spawnParticleTemplate(particleLocation, getMaterialWithDataAsItemStack(Material.SAND, (byte)1));
    this.spawnParticleTemplate(particleLocation, new ItemStack(vomitContent, 1));
  }

  private void spawnParticleTemplate(Location location, ItemStack itemStack) {
    ParticleBundler.createExtended(Particle.ITEM_CRACK)
        .setData(itemStack)
        .setCount(2 + new Random().nextInt(10))
        .setSpeed(0.1f)
        .spawn(location);
  }

  private ItemStack getMaterialWithDataAsItemStack(Material material, byte data) {
    return new MaterialData(material, data).toItemStack(1);
  }
}
