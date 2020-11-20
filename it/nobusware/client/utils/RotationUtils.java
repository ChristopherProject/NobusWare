package it.nobusware.client.utils;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class RotationUtils {
  private static Minecraft mc = Minecraft.getMinecraft();
  
  public static float[] getRotations(EntityLivingBase ent) {
    double x = ent.posX;
    double y = ent.posY + (ent.getEyeHeight() / 2.0F);
    double z = ent.posZ;
    return getRotationFromPosition(x, y, z);
  }
  
  public static float[] getAverageRotations(List<EntityLivingBase> targetList) {
    double posX = 0.0D;
    double posY = 0.0D;
    double posZ = 0.0D;
    for (Entity ent : targetList) {
      posX += ent.posX;
      posY += ent.boundingBox.maxY - 2.0D;
      posZ += ent.posZ;
    } 
    return new float[] { getRotationFromPosition(posX /= targetList.size(), posZ /= targetList.size(), posY /= targetList
          .size())[0], getRotationFromPosition(posX, posZ, posY)[1] };
  }
  
  public static float[] getBowAngles(Entity entity) {
    double xDelta = entity.posX - entity.lastTickPosX;
    double zDelta = entity.posZ - entity.lastTickPosZ;
    double d = (Minecraft.getMinecraft()).thePlayer.getDistanceToEntity(entity);
    d -= d % 0.8D;
    double xMulti = 1.0D;
    double zMulti = 1.0D;
    boolean sprint = entity.isSprinting();
    xMulti = d / 0.8D * xDelta * (sprint ? 1.25D : 1.0D);
    zMulti = d / 0.8D * zDelta * (sprint ? 1.25D : 1.0D);
    double x = entity.posX + xMulti - (Minecraft.getMinecraft()).thePlayer.posX;
    double z = entity.posZ + zMulti - (Minecraft.getMinecraft()).thePlayer.posZ;
    double y2 = (Minecraft.getMinecraft()).thePlayer.posY + (Minecraft.getMinecraft()).thePlayer.getEyeHeight() - entity.posY + entity.getEyeHeight();
    double dist = (Minecraft.getMinecraft()).thePlayer.getDistanceToEntity(entity);
    float yaw = (float)Math.toDegrees(Math.atan2(z, x)) - 90.0F;
    float pitch = (float)Math.toDegrees(Math.atan2(y2, dist));
    return new float[] { yaw, pitch };
  }
  
  public static float[] getRotationFromPosition(double x, double y, double z) {
    double xDiff = x - (Minecraft.getMinecraft()).thePlayer.posX;
    double yDiff = y - (Minecraft.getMinecraft()).thePlayer.posY - 1.2D;
    double zDiff = z - (Minecraft.getMinecraft()).thePlayer.posZ;
    double dist = Math.hypot(xDiff, zDiff);
    float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0D / Math.PI) - 90.0F;
    float pitch = (float)-(Math.atan2(yDiff, dist) * 180.0D / Math.PI);
    return new float[] { yaw, pitch };
  }
  
  public static float getTrajAngleSolutionLow(float d3, float d1, float velocity) {
    float sqrt = velocity * velocity * velocity * velocity - 0.006F * (0.006F * d3 * d3 + 2.0F * d1 * velocity * velocity);
    return (float)Math.toDegrees(Math.atan(((velocity * velocity) - Math.sqrt(sqrt)) / (0.006F * d3)));
  }
  
  public static float getYawChange(double posX, double posZ) {
    double deltaX = posX - (Minecraft.getMinecraft()).thePlayer.posX;
    double deltaZ = posZ - (Minecraft.getMinecraft()).thePlayer.posZ;
    double yawToEntity = (deltaZ < 0.0D && deltaX < 0.0D) ? (90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX))) : ((deltaZ < 0.0D && deltaX > 0.0D) ? (-90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX))) : Math.toDegrees(-Math.atan(deltaX / deltaZ)));
    return MathHelper.wrapAngleTo180_float(-((Minecraft.getMinecraft()).thePlayer.rotationYaw - (float)yawToEntity));
  }
  
  public static float getPitchChange(Entity entity, double posY) {
    double deltaX = entity.posX - (Minecraft.getMinecraft()).thePlayer.posX;
    double deltaZ = entity.posZ - (Minecraft.getMinecraft()).thePlayer.posZ;
    double deltaY = posY - 2.2D + entity.getEyeHeight() - (Minecraft.getMinecraft()).thePlayer.posY;
    double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
    double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
    return 
      -MathHelper.wrapAngleTo180_float((Minecraft.getMinecraft()).thePlayer.rotationPitch - (float)pitchToEntity) - 2.5F;
  }
  
  public static float getNewAngle(float angle) {
    if ((angle %= 360.0F) >= 180.0F)
      angle -= 360.0F; 
    if (angle < -180.0F)
      angle += 360.0F; 
    return angle;
  }
  
  public static float getDistanceBetweenAngles(float angle1, float angle2) {
    float angle3 = Math.abs(angle1 - angle2) % 360.0F;
    if (angle3 > 180.0F)
      angle3 = 360.0F - angle3; 
    return angle3;
  }
  
  public static float[] getRotations(Vec3d vec) {
    return getRotationFromPosition(vec.xCoord, vec.yCoord, vec.zCoord);
  }
  
  public static float[] getRotationsTS(EntityLivingBase ent) {
    double x = ent.posX + getX(ent.tsYaw, ent);
    double y = ent.posY + (ent.getEyeHeight() / 2.0F);
    double z = ent.posZ + getZ(ent.tsYaw, ent);
    return getRotationFromPosition(x, y, z);
  }
  
  public static void moveYaw(double yaw) {
    mc.thePlayer.rotationYawTs = yaw;
  }
  
  public static double getX(double yaw, EntityLivingBase e) {
    double cos = Math.cos(Math.toRadians(yaw + 90.0D));
    double sin = Math.sin(Math.toRadians(yaw + 90.0D));
    return 1.0D * cos + 0.0D * sin;
  }
  
  public static double getZ(double yaw, EntityLivingBase e) {
    double cos = Math.cos(Math.toRadians(yaw + 90.0D));
    double sin = Math.sin(Math.toRadians(yaw + 90.0D));
    return 1.0D * sin - 0.0D * cos;
  }
}
