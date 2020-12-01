package it.nobusware.client.utils;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class ScaffoldUtils {
  private static final Minecraft mc = Minecraft.getMinecraft();
  
  private static final List<Block> blockBlacklist = Arrays.asList(Blocks.air, Blocks.water, Blocks.tnt, Blocks.chest, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.tnt, Blocks.enchanting_table, Blocks.carpet,
          Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.snow_layer, Blocks.ice, Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.chest,
          Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.tnt, Blocks.gold_ore, Blocks.iron_ore, Blocks.lapis_ore, Blocks.sand,
          Blocks.lit_redstone_ore, Blocks.quartz_ore, Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.lever,
          Blocks.enchanting_table);

  private static final List<Block> blockBlacklist2 = Arrays.asList(Blocks.air);
  
  public static double getExpandBlockSlotX(double expandValue) {
    double x2 = Math.cos(Math.toRadians((mc.thePlayer.rotationYaw + 90.0F)));
    double z2 = Math.sin(Math.toRadians((mc.thePlayer.rotationYaw + 90.0F)));
    double zOffset = mc.thePlayer.movementInput.moveForward * 0.1D * z2 - mc.thePlayer.movementInput.moveStrafe * 0.1D * x2;
    double z = mc.thePlayer.posZ + zOffset;
    return z;
  }
  
  public static double getExpandBlockSlotZ(double expandValue) {
    double x2 = Math.cos(Math.toRadians((mc.thePlayer.rotationYaw + 90.0F)));
    double z2 = Math.sin(Math.toRadians((mc.thePlayer.rotationYaw + 90.0F)));
    double xOffset = mc.thePlayer.movementInput.moveForward * 0.1D * x2 + mc.thePlayer.movementInput.moveStrafe * 0.1D * z2;
    double x = mc.thePlayer.posX + xOffset;
    return x;
  }

  public static BlockData getBlockData2(BlockPos var1) {
    if (!blockBlacklist2.contains(mc.theWorld.getBlockState(var1.add(0, -1, 0)).getBlock()))
      return new BlockData(var1.add(0, -1, 0), EnumFacing.UP);
    if (!blockBlacklist2.contains(mc.theWorld.getBlockState(var1.add(-1, 0, 0)).getBlock()))
      return new BlockData(var1.add(-1, 0, 0), EnumFacing.EAST);
    if (!blockBlacklist2.contains(mc.theWorld.getBlockState(var1.add(1, 0, 0)).getBlock()))
      return new BlockData(var1.add(1, 0, 0), EnumFacing.WEST);
    if (!blockBlacklist2.contains(mc.theWorld.getBlockState(var1.add(0, 0, -1)).getBlock()))
      return new BlockData(var1.add(0, 0, -1), EnumFacing.SOUTH);
    if (!blockBlacklist2.contains(mc.theWorld.getBlockState(var1.add(0, 0, 1)).getBlock()))
      return new BlockData(var1.add(0, 0, 1), EnumFacing.NORTH);
    BlockPos add = var1.add(-1, 0, 0);
    if (!blockBlacklist2.contains(mc.theWorld.getBlockState(add.add(-1, 0, 0)).getBlock()))
      return new BlockData(add.add(-1, 0, 0), EnumFacing.EAST);
    if (!blockBlacklist2.contains(mc.theWorld.getBlockState(add.add(1, 0, 0)).getBlock()))
      return new BlockData(add.add(1, 0, 0), EnumFacing.WEST);
    if (!blockBlacklist2.contains(mc.theWorld.getBlockState(add.add(0, 0, -1)).getBlock()))
      return new BlockData(add.add(0, 0, -1), EnumFacing.SOUTH);
    if (!blockBlacklist2.contains(mc.theWorld.getBlockState(add.add(0, 0, 1)).getBlock()))
      return new BlockData(add.add(0, 0, 1), EnumFacing.NORTH);
    BlockPos add2 = var1.add(1, 0, 0);
    if (!blockBlacklist2.contains(mc.theWorld.getBlockState(add2.add(-1, 0, 0)).getBlock()))
      return new BlockData(add2.add(-1, 0, 0), EnumFacing.EAST);
    if (!blockBlacklist2.contains(mc.theWorld.getBlockState(add2.add(1, 0, 0)).getBlock()))
      return new BlockData(add2.add(1, 0, 0), EnumFacing.WEST);
    if (!blockBlacklist2.contains(mc.theWorld.getBlockState(add2.add(0, 0, -1)).getBlock()))
      return new BlockData(add2.add(0, 0, -1), EnumFacing.SOUTH);
    if (!blockBlacklist2.contains(mc.theWorld.getBlockState(add2.add(0, 0, 1)).getBlock()))
      return new BlockData(add2.add(0, 0, 1), EnumFacing.NORTH);
    BlockPos add3 = var1.add(0, 0, -1);
    if (!blockBlacklist2.contains(mc.theWorld.getBlockState(add3.add(-1, 0, 0)).getBlock()))
      return new BlockData(add3.add(-1, 0, 0), EnumFacing.EAST);
    if (!blockBlacklist2.contains(mc.theWorld.getBlockState(add3.add(1, 0, 0)).getBlock()))
      return new BlockData(add3.add(1, 0, 0), EnumFacing.WEST);
    if (!blockBlacklist2.contains(mc.theWorld.getBlockState(add3.add(0, 0, -1)).getBlock()))
      return new BlockData(add3.add(0, 0, -1), EnumFacing.SOUTH);
    if (!blockBlacklist2.contains(mc.theWorld.getBlockState(add3.add(0, 0, 1)).getBlock()))
      return new BlockData(add3.add(0, 0, 1), EnumFacing.NORTH);
    BlockPos add4 = var1.add(0, 0, 1);
    if (!blockBlacklist2.contains(mc.theWorld.getBlockState(add4.add(-1, 0, 0)).getBlock()))
      return new BlockData(add4.add(-1, 0, 0), EnumFacing.EAST);
    if (!blockBlacklist2.contains(mc.theWorld.getBlockState(add4.add(1, 0, 0)).getBlock()))
      return new BlockData(add4.add(1, 0, 0), EnumFacing.WEST);
    if (!blockBlacklist2.contains(mc.theWorld.getBlockState(add4.add(0, 0, -1)).getBlock()))
      return new BlockData(add4.add(0, 0, -1), EnumFacing.SOUTH);
    if (!blockBlacklist2.contains(mc.theWorld.getBlockState(add4.add(0, 0, 1)).getBlock()))
      return new BlockData(add4.add(0, 0, 1), EnumFacing.NORTH);
    return null;
  }
  
  public static BlockData getBlockData(BlockPos var1) {
    if (!blockBlacklist.contains(mc.theWorld.getBlockState(var1.add(0, -1, 0)).getBlock()))
      return new BlockData(var1.add(0, -1, 0), EnumFacing.UP); 
    if (!blockBlacklist.contains(mc.theWorld.getBlockState(var1.add(-1, 0, 0)).getBlock()))
      return new BlockData(var1.add(-1, 0, 0), EnumFacing.EAST); 
    if (!blockBlacklist.contains(mc.theWorld.getBlockState(var1.add(1, 0, 0)).getBlock()))
      return new BlockData(var1.add(1, 0, 0), EnumFacing.WEST); 
    if (!blockBlacklist.contains(mc.theWorld.getBlockState(var1.add(0, 0, -1)).getBlock()))
      return new BlockData(var1.add(0, 0, -1), EnumFacing.SOUTH); 
    if (!blockBlacklist.contains(mc.theWorld.getBlockState(var1.add(0, 0, 1)).getBlock()))
      return new BlockData(var1.add(0, 0, 1), EnumFacing.NORTH); 
    BlockPos add = var1.add(-1, 0, 0);
    if (!blockBlacklist.contains(mc.theWorld.getBlockState(add.add(-1, 0, 0)).getBlock()))
      return new BlockData(add.add(-1, 0, 0), EnumFacing.EAST); 
    if (!blockBlacklist.contains(mc.theWorld.getBlockState(add.add(1, 0, 0)).getBlock()))
      return new BlockData(add.add(1, 0, 0), EnumFacing.WEST); 
    if (!blockBlacklist.contains(mc.theWorld.getBlockState(add.add(0, 0, -1)).getBlock()))
      return new BlockData(add.add(0, 0, -1), EnumFacing.SOUTH); 
    if (!blockBlacklist.contains(mc.theWorld.getBlockState(add.add(0, 0, 1)).getBlock()))
      return new BlockData(add.add(0, 0, 1), EnumFacing.NORTH); 
    BlockPos add2 = var1.add(1, 0, 0);
    if (!blockBlacklist.contains(mc.theWorld.getBlockState(add2.add(-1, 0, 0)).getBlock()))
      return new BlockData(add2.add(-1, 0, 0), EnumFacing.EAST); 
    if (!blockBlacklist.contains(mc.theWorld.getBlockState(add2.add(1, 0, 0)).getBlock()))
      return new BlockData(add2.add(1, 0, 0), EnumFacing.WEST); 
    if (!blockBlacklist.contains(mc.theWorld.getBlockState(add2.add(0, 0, -1)).getBlock()))
      return new BlockData(add2.add(0, 0, -1), EnumFacing.SOUTH); 
    if (!blockBlacklist.contains(mc.theWorld.getBlockState(add2.add(0, 0, 1)).getBlock()))
      return new BlockData(add2.add(0, 0, 1), EnumFacing.NORTH); 
    BlockPos add3 = var1.add(0, 0, -1);
    if (!blockBlacklist.contains(mc.theWorld.getBlockState(add3.add(-1, 0, 0)).getBlock()))
      return new BlockData(add3.add(-1, 0, 0), EnumFacing.EAST); 
    if (!blockBlacklist.contains(mc.theWorld.getBlockState(add3.add(1, 0, 0)).getBlock()))
      return new BlockData(add3.add(1, 0, 0), EnumFacing.WEST); 
    if (!blockBlacklist.contains(mc.theWorld.getBlockState(add3.add(0, 0, -1)).getBlock()))
      return new BlockData(add3.add(0, 0, -1), EnumFacing.SOUTH); 
    if (!blockBlacklist.contains(mc.theWorld.getBlockState(add3.add(0, 0, 1)).getBlock()))
      return new BlockData(add3.add(0, 0, 1), EnumFacing.NORTH); 
    BlockPos add4 = var1.add(0, 0, 1);
    if (!blockBlacklist.contains(mc.theWorld.getBlockState(add4.add(-1, 0, 0)).getBlock()))
      return new BlockData(add4.add(-1, 0, 0), EnumFacing.EAST); 
    if (!blockBlacklist.contains(mc.theWorld.getBlockState(add4.add(1, 0, 0)).getBlock()))
      return new BlockData(add4.add(1, 0, 0), EnumFacing.WEST); 
    if (!blockBlacklist.contains(mc.theWorld.getBlockState(add4.add(0, 0, -1)).getBlock()))
      return new BlockData(add4.add(0, 0, -1), EnumFacing.SOUTH); 
    if (!blockBlacklist.contains(mc.theWorld.getBlockState(add4.add(0, 0, 1)).getBlock()))
      return new BlockData(add4.add(0, 0, 1), EnumFacing.NORTH); 
    return null;
  }
  
  public static boolean isEmpty(ItemStack stack) {
    return (stack == null);
  }
  
  public static Vec3d getVec3d(BlockPos pos, EnumFacing face) {
    double x = pos.getX() + 0.50246892576D;
    double y = pos.getY() + 0.50246892576D;
    double z = pos.getZ() + 0.50246892576D;
    x += face.getFrontOffsetX() / 2.0D;
    z += face.getFrontOffsetZ() / 2.0D;
    y += face.getFrontOffsetY() / 2.0D;
    if (face == EnumFacing.UP || face == EnumFacing.DOWN) {
      x += randomNumber(0.3050246892576D, -0.3050246892576D);
      z += randomNumber(0.3050246892576D, -0.3050246892576D);
    } else {
      y += randomNumber(0.3050246892576D, -0.3050246892576D);
    } 
    if (face == EnumFacing.WEST || face == EnumFacing.EAST)
      z += randomNumber(0.3050246892576D, -0.3050246892576D); 
    if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH)
      x += randomNumber(0.3050246892576D, -0.3050246892576D); 
    return new Vec3d(x, y, z);
  }
  
  public static double randomNumber(double max, double min) {
    return Math.random() * (max - min) + min;
  }
  
  public static boolean invCheck() {
    for (int i = 36; i < 45; ) {
      if (!mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() || 
        !isValid(mc.thePlayer.inventoryContainer.getSlot(i).getStack())) {
        i++;
        continue;
      } 
      return false;
    } 
    return true;
  }
  
  public static boolean isValid(ItemStack item) {
    if (isEmpty(item))
      return false; 
    if (item.getUnlocalizedName().equalsIgnoreCase("tile.chest"))
      return false; 
    if (!(item.getItem() instanceof ItemBlock))
      return false; 
    return !blockBlacklist.contains(((ItemBlock)item.getItem()).getBlock());
  }
  
  public static boolean contains(Block block) {
    return blockBlacklist.contains(block);
  }
  
  public static int getBlockSlot() {
    for (int i = 36; i < 45; i++) {
      ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
      if (stack != null && stack.getItem() instanceof ItemBlock && 
        !contains(((ItemBlock)stack.getItem()).getBlock()))
        return i - 36; 
    } 
    return -1;
  }
  
  public static void swap(int slot, int hotBarNumber) {
    mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, hotBarNumber, 2, mc.thePlayer);
  }
}
