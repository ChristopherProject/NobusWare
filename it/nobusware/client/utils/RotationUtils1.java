package it.nobusware.client.utils;

import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class RotationUtils1
{
	
	private static Minecraft mc = Minecraft.getMinecraft();
    public static float serveryaw;
    public static float serverpitch;
    
    public static float[] rotations(final Entity e) {
        final double diffX = e.posX - Minecraft.getMinecraft().thePlayer.posX;
        final double diffZ = e.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double diffY;
        if (e instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase)e;
            diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight() - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        }
        else {
            diffY = (e.boundingBox.minY + e.boundingBox.maxY) / 2.0 - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        }
        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793));
        return new float[] { mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw), mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch) };
    }
    
    public static float updateyaw(float corrente, float bersaglio) {
        corrente = Math.floorMod(Math.round(corrente), 360);
        bersaglio = Math.floorMod(Math.round(bersaglio), 360);
        float delta = bersaglio - corrente;
        if (delta > 180.0f) {
            delta -= 360.0f;
        }
        else if (delta < -180.0f) {
            delta += 360.0f;
        }
        return delta;
    }
    
    public static float[] getRotationsNeededBlock(final double x, final double y, final double z) {
        final double diffX = x + 0.5 - Minecraft.getMinecraft().thePlayer.posX;
        final double diffZ = z + 0.5 - Minecraft.getMinecraft().thePlayer.posZ;
        final double diffY = y + 0.5 - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793));
        return new float[] { Minecraft.getMinecraft().thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw), Minecraft.getMinecraft().thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch) };
    }
    
    public static float updatepitch(final float corrente, final float bersaglio) {
        final float delta = bersaglio - corrente;
        return delta;
    }
}
