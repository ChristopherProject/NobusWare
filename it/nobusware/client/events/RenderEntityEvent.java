package it.nobusware.client.events;

import QuarantineAPI.Event;
import net.minecraft.entity.EntityLivingBase;

public class RenderEntityEvent extends Event {
	  private final EntityLivingBase entityLivingBase;
	  
	  public boolean pre;
	  
	  public RenderEntityEvent(EntityLivingBase entityLivingBase, boolean pre) {
	    this.entityLivingBase = entityLivingBase;
	    this.pre = pre;
	  }
	  
	  public EntityLivingBase getEntityLivingBase() {
	    return this.entityLivingBase;
	  }
	}
