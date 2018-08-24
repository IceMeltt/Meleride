package pl.meleride.cars.util;

import org.bukkit.util.Vector;

public class AxisRotation {

  public static Vector rotateAroundAxisX(Vector v, double cos, double sin) {
    double y = v.getY() * cos - v.getZ() * sin;
    double z = v.getY() * sin + v.getZ() * cos;
    return v.setY(y).setZ(z);
  }

  public static Vector rotateAroundAxisY(Vector v, double cos, double sin) {
    double x = v.getX() * cos + v.getZ() * sin;
    double z = v.getX() * -sin + v.getZ() * cos;
    return v.setX(x).setZ(z);
  }

  public static Vector rotateAroundAxisZ(Vector v, double cos, double sin) {
    double x = v.getX() * cos - v.getY() * sin;
    double y = v.getX() * sin + v.getY() * cos;
    return v.setX(x).setY(y);
  }

}
