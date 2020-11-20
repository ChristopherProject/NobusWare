package it.nobusware.client.mods;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.Timer;
import it.nobusware.client.utils.value.Value;
import it.nobusware.client.utils.value.impl.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovingObjectPosition;

public class AutoClicker extends Module
{
  
	private NumberValue<Integer> click_speed = new NumberValue("CPS", Integer.valueOf(9), Integer.valueOf(1), Integer.valueOf(20), Integer.valueOf(1));
	private NumberValue<Integer> click_fov = new NumberValue("Reach", Integer.valueOf(9), Integer.valueOf(1), Integer.valueOf(20), Integer.valueOf(1));
    private Timer timer;
    int random;
    int delay;
 
    public AutoClicker(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
		addValues(new Value[] { this.click_speed, this.click_fov });
	    this.timer = new Timer();
	}
    
    @Handler
    public void onUpdate(final EventUpdate event) {
    	if(!this.isAbilitato()) {
    		return;
    	}
        if (this.timer.hasPassed(150.0)) {
            this.random = 0;
        }
        if (this.timer.hasPassed(350.0)) {
            this.random = 3;
        }
        if (this.timer.hasPassed(550.0)) {
            this.random = 2;
        }
        if (this.timer.hasPassed(750.0)) {
            this.random = 4;
        }
        if (this.timer.hasPassed(950.0)) {
            this.random = 1;
        }
        if (this.timer.hasPassed(1150.0)) {
            this.random = 3;
            this.timer.reset();
        }
        if (Minecraft.getMinecraft().gameSettings.keyBindAttack.pressed) {
            ++delay;
            for (int i = 0; i < this.mc.theWorld.playerEntities.size(); ++i) {
                final MovingObjectPosition omo = this.mc.objectMouseOver;
                if (omo == null || omo.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
                    final EntityPlayer entityPlayer = (EntityPlayer) this.mc.theWorld.playerEntities.get(i);
                    if (entityPlayer != this.mc.thePlayer) {
                        final float f = this.mc.thePlayer.getDistanceToEntity(entityPlayer);
                        if (f < 5.0f && this.mc.thePlayer.canEntityBeSeen(entityPlayer) && this.delay >= this.click_speed.getValue().intValue() + this.random  && !entityPlayer.isInvisible()) {
                            this.mc.thePlayer.swingItem();
                            this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(this.mc.objectMouseOver.entityHit, C02PacketUseEntity.Action.ATTACK));
                            this.mc.thePlayer.setSprinting(false);
                            final float sharpLevel = EnchantmentHelper.func_152377_a(Minecraft.getMinecraft().thePlayer.getHeldItem(), this.mc.thePlayer.getCreatureAttribute());
                            final boolean vanillaCrit = Minecraft.getMinecraft().thePlayer.fallDistance > 0.0f && !Minecraft.getMinecraft().thePlayer.onGround && !Minecraft.getMinecraft().thePlayer.isOnLadder() && !Minecraft.getMinecraft().thePlayer.isInWater() && !Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.blindness) && Minecraft.getMinecraft().thePlayer.ridingEntity == null;
                            if (vanillaCrit) {
                                Minecraft.getMinecraft().thePlayer.onCriticalHit(entityPlayer);
                            }
                            if (sharpLevel > 0.0f) {
                                Minecraft.getMinecraft().thePlayer.onEnchantmentCritical(entityPlayer);
                            }
                            this.delay = 0;
                        }
                    }
                }
                else if (this.delay >= this.click_speed.getValue().intValue() + this.random) {
                    this.mc.thePlayer.swingItem();
                    this.delay = 0;
                }
            }
        }
    }
   
    public void Abilitato() {
        this.timer.reset();
    }
}