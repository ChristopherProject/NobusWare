package it.nobusware.client.mods;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.value.Value;
import it.nobusware.client.utils.value.impl.EnumValue;
import net.minecraft.util.EnumParticleTypes;

public class ParticleMod extends Module {
	
	private EnumValue<Mode> mode = new EnumValue("Mode", Mode.HEART);
	
    public ParticleMod(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
		addValues(new Value[] { this.mode });
	}

    @Handler
    public void onUpdate(EventUpdate event) {
        if (mc.thePlayer.isMoving()) switch (mode.getValue()) {
            case HEART:
                mc.effectRenderer.func_178926_a(mc.thePlayer, EnumParticleTypes.HEART);
                break;
            case LAVA:
                mc.effectRenderer.func_178926_a(mc.thePlayer, EnumParticleTypes.LAVA);
                break;
            case SMOKE:
                mc.effectRenderer.func_178926_a(mc.thePlayer, EnumParticleTypes.REDSTONE);
                break;
            case CLOUD:
                mc.effectRenderer.func_178926_a(mc.thePlayer, EnumParticleTypes.CLOUD);
                break;
            case FLAME:
                mc.effectRenderer.func_178926_a(mc.thePlayer, EnumParticleTypes.FLAME);
                break;
            case SLIME:
                mc.effectRenderer.func_178926_a(mc.thePlayer, EnumParticleTypes.SLIME);
                break;
            case WATER:
                mc.effectRenderer.func_178926_a(mc.thePlayer, EnumParticleTypes.WATER_SPLASH);
                break;
            case FIREWORK:
                mc.effectRenderer.func_178926_a(mc.thePlayer, EnumParticleTypes.FIREWORKS_SPARK);
                break;
        }
    }

    public enum Mode {
        SMOKE, HEART, FIREWORK, FLAME, CLOUD, WATER, LAVA, SLIME
    }
}
