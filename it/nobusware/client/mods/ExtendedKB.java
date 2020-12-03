package it.nobusware.client.mods;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Mouse;

public class ExtendedKB extends Module {

    public ExtendedKB(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
        super(nome_mod, tasto, nome_array_printed, categoria);
    }

    @Handler
    public void onUpdate(EventUpdate e) {
        if (isAbilitato() && Mouse.isButtonDown(0)) {
            Entity en = nearEntity();
            if (en == null) {
                return;
            }

            if (!mc.thePlayer.boundingBox.intersectsWith(en.boundingBox)) {
                return;
            }

            for (int i = 0; i < 250; ++i) {
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(mc.thePlayer.onGround));
            }
        }
    }

    private EntityLivingBase nearEntity() {
        EntityLivingBase closestEntity = null;
        float mindistance = (float) 5.0;

        for (Object o : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (isNotItem(o) && !(o instanceof EntityPlayerSP)) {
                EntityLivingBase en = (EntityLivingBase) o;
                if (mc.thePlayer.getDistanceToEntity(en) < mindistance) {
                    mindistance = mc.thePlayer.getDistanceToEntity(en);
                    closestEntity = en;
                }
            }
        }

        return closestEntity;
    }


    private boolean isNotItem(Object o) {
        return o instanceof EntityLivingBase;
    }
}
