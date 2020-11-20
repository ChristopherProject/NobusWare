package it.nobusware.client.utils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class PlayerUtils {
	
	private  static Minecraft mc = Minecraft.getMinecraft();
  public static boolean isInLiquid() {
    for (int x = MathHelper.floor_double((Minecraft.getMinecraft()).thePlayer.boundingBox.minY); x < MathHelper.floor_double((Minecraft.getMinecraft()).thePlayer.boundingBox.maxX) + 1; x++) {
      for (int z = MathHelper.floor_double((Minecraft.getMinecraft()).thePlayer.boundingBox.minZ); z < MathHelper.floor_double((Minecraft.getMinecraft()).thePlayer.boundingBox.maxZ) + 1; z++) {
        BlockPos pos = new BlockPos(x, (int)(Minecraft.getMinecraft()).thePlayer.boundingBox.minY, z);
        Block block = (Minecraft.getMinecraft()).theWorld.getBlockState(pos).getBlock();
        if (block != null && !(block instanceof net.minecraft.block.BlockAir))
          return block instanceof net.minecraft.block.BlockLiquid; 
      } 
    } 
    return false;
  }
  
  public static boolean isInsideBlock() {
    for (int x = MathHelper.floor_double((Minecraft.getMinecraft()).thePlayer.boundingBox.minX); x < MathHelper.floor_double((Minecraft.getMinecraft()).thePlayer.boundingBox.maxX) + 1; x++) {
      for (int y = MathHelper.floor_double((Minecraft.getMinecraft()).thePlayer.boundingBox.minY); y < MathHelper.floor_double((Minecraft.getMinecraft()).thePlayer.boundingBox.maxY) + 1; y++) {
        for (int z = MathHelper.floor_double((Minecraft.getMinecraft()).thePlayer.boundingBox.minZ); z < MathHelper.floor_double((Minecraft.getMinecraft()).thePlayer.boundingBox.maxZ) + 1; z++) {
          Block block = (Minecraft.getMinecraft()).theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
          if (block != null && !(block instanceof net.minecraft.block.BlockAir)) {
            AxisAlignedBB boundingBox = block.getCollisionBoundingBox((World)(Minecraft.getMinecraft()).theWorld, new BlockPos(x, y, z), (Minecraft.getMinecraft()).theWorld.getBlockState(new BlockPos(x, y, z)));
            if (block instanceof net.minecraft.block.BlockHopper)
              boundingBox = new AxisAlignedBB(x, y, z, (x + 1), (y + 1), (z + 1)); 
            if (boundingBox != null && (Minecraft.getMinecraft()).thePlayer.boundingBox.intersectsWith(boundingBox))
              return true; 
          } 
        } 
      } 
    } 
    return false;
  }

  public static float getDirectionMoving() {
	    float dir = (mc.thePlayer).rotationYaw;
	    if ((mc.thePlayer).moveForward < 0.0F)
	      dir += 180.0F; 
	    if ((mc.thePlayer).moveStrafing > 0.0F)
	      dir -= 90.0F * (((mc.thePlayer).moveForward < 0.0F) ? -0.5F : (((mc.thePlayer).moveForward > 0.0F) ? 0.5F : 1.0F)); 
	    if ((mc.thePlayer).moveStrafing < 0.0F)
	      dir += 90.0F * (((mc.thePlayer).moveForward < 0.0F) ? -0.5F : (((mc.thePlayer).moveForward > 0.0F) ? 0.5F : 1.0F)); 
	    return dir;
	  }
}
