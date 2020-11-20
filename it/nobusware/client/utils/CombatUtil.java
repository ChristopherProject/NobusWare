package it.nobusware.client.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;

public class CombatUtil  {
	
	  public static List botList = new ArrayList();
	  
	  public static Entity getTarget(boolean mobs, boolean players, double range, boolean invis) {
		    for (Object obj : Minecraft.getMinecraft().theWorld.loadedEntityList) {
		      EntityLivingBase o;
		      if (!(obj instanceof EntityLivingBase) || (o = (EntityLivingBase)obj)
		        .getDistanceToEntity((Entity)Minecraft.getMinecraft().thePlayer) > ((((EntityLivingBase)obj).posY > Minecraft.getMinecraft().thePlayer.posY + 1.0D) ? (range + 2.0D) : range) || (o
		        .isInvisible() && !invis) || o.isDead() || o.getHealth() == 0.0F || o == Minecraft.getMinecraft().thePlayer || botList.contains(o))
		        continue; 
		      if (!(o instanceof net.minecraft.entity.player.EntityPlayer) || o instanceof net.minecraft.entity.passive.EntityAnimal)
		      continue;
		      return (Entity)o;
		    } 
		    return null;
		  }
	  
	  public static boolean canBlock(boolean mobs, boolean players, double range, boolean invis) {
	    for (Object obj : Minecraft.getMinecraft().theWorld.loadedEntityList) {
	      EntityLivingBase o;
	      if (!(obj instanceof EntityLivingBase) || (o = (EntityLivingBase)obj)
	        .getDistanceToEntity((Entity)Minecraft.getMinecraft().thePlayer) > range || (o
	        .isInvisible() && !invis) || o.isDead() || o == Minecraft.getMinecraft().thePlayer || botList
	        .contains(o.getName()) || (!(o instanceof net.minecraft.entity.player.EntityPlayer) && players))
	        continue; 
	      return true;
	    } 
	    return false;
	  }
	  
	  public static List<EntityLivingBase> getTargets(int maxTargets, boolean mobs, boolean players, double range, boolean invis) {
	    ArrayList<EntityLivingBase> list = new ArrayList<>();
	    for (Object obj : Minecraft.getMinecraft().theWorld.loadedEntityList) {
	      EntityLivingBase o;
	      if (!(obj instanceof EntityLivingBase) || (o = (EntityLivingBase)obj)
	        .getDistanceToEntity((Entity)Minecraft.getMinecraft().thePlayer) > range || o
	        .isDead() || o == Minecraft.getMinecraft().thePlayer || o.posY < Minecraft.getMinecraft().thePlayer.posY - 11.0D || o.posY > Minecraft.getMinecraft().thePlayer.posY + 11.0D || (o instanceof net.minecraft.entity.player.EntityPlayer && botList
	        
	        .contains(o)))
	        continue; 
	      if (!(o instanceof net.minecraft.entity.player.EntityPlayer) || o instanceof net.minecraft.entity.passive.EntityAnimal)
	        continue; 
	      if (list.size() >= maxTargets)
	        continue; 
	      list.add(o);
	    } 
	    return list;
	  }
	}