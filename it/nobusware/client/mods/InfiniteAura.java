package it.nobusware.client.mods;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.PathFinder;
import it.nobusware.client.utils.Timer;
import it.nobusware.client.utils.value.impl.BooleanValue;
import it.nobusware.client.utils.value.impl.NumberValue;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InfiniteAura extends Module {

    private final NumberValue<Integer> maxTargets = new NumberValue<>("Targets", 5, 1, 50, 1);
    private final NumberValue<Integer> reachDistance = new NumberValue<>("Distance", 5, 1, 50, 1);
    private final NumberValue<Integer> cps = new NumberValue<>("CPS", 100, 1, 200, 1);
    private final BooleanValue players = new BooleanValue("Players", true);
    private final BooleanValue animals = new BooleanValue("Animals", false);

    private List<EntityLivingBase> targets = new CopyOnWriteArrayList<>();
    private final Timer cpsTimer = new Timer();
    public static Timer timer = new Timer();

    public InfiniteAura(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
        super(nome_mod, tasto, nome_array_printed, categoria);
        addValues(reachDistance, maxTargets, cps, players, animals);
    }

    @Override
    public void Abilitato() {
        timer.reset();
        targets.clear();
    }

    @Handler
    public void onUpdate(EventUpdate e) {
        if (isAbilitato() && e.isPre()) {

            int niggers = maxTargets.getValue();

            targets = getTargets();

            int delayValue = 20 / cps.getValue() * 50;
            if (cpsTimer.hasPassed(delayValue)) {
                if (targets.size() > 0) {
                    for (int i = 0; i < (Math.min(targets.size(), niggers)); i++) {
                        EntityLivingBase T = targets.get(i);
                        Vec3 topFrom = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                        Vec3 to = new Vec3(T.posX, T.posY, T.posZ);

                        ArrayList<Vec3> path = doStuff(topFrom, to);

                        for (Vec3 pathElm : path) {
                            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(pathElm.getX(), pathElm.getY(), pathElm.getZ(), true));
                        }

                        mc.thePlayer.swingItem();
                        mc.playerController.attackEntity(mc.thePlayer, T);
                        Collections.reverse(path);
                        for (Vec3 pathElm : path) {
                            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(pathElm.getX(), pathElm.getY(), pathElm.getZ(), true));
                        }
                    }
                    cpsTimer.reset();
                }
            }
        }
    }

    private boolean canPassThrow(BlockPos pos) {
        Block block = Minecraft.getMinecraft().theWorld.getBlockState(new net.minecraft.util.BlockPos(pos.getX(), pos.getY(), pos.getZ())).getBlock();
        return block.getMaterial() == Material.air || block.getMaterial() == Material.plants || block.getMaterial() == Material.vine || block == Blocks.ladder || block == Blocks.water || block == Blocks.flowing_water || block == Blocks.wall_sign || block == Blocks.standing_sign;
    }

    private boolean isValidEntity(EntityLivingBase entity) {
        int range = reachDistance.getValue();
        boolean playersValue = players.getValue();
        boolean animalsValue = animals.getValue();

        if ((mc.thePlayer.isEntityAlive())
                && !(entity instanceof EntityPlayerSP)) {
            if (mc.thePlayer.getDistanceToEntity(entity) <= range) {

                if (entity.isPlayerSleeping()) {
                    return false;
                }

                if (entity instanceof EntityPlayer) {
                    if (playersValue) {

                        EntityPlayer player = (EntityPlayer) entity;
                        if (!player.isEntityAlive() && player.getHealth() == 0.0) {
                            return false;
                        }
                    }
                } else {
                    if (!entity.isEntityAlive()) {

                        return false;
                    }
                }

                if (entity instanceof EntityMob && animalsValue) {

                    return true;
                }

                if ((entity instanceof EntityAnimal || entity instanceof EntityVillager) && animalsValue) {
                    return !entity.getName().equals("Villager");
                }
            }
        }

        return false;
    }

    private List<EntityLivingBase> getTargets() {
        List<EntityLivingBase> gays = new ArrayList<>();

        for (Object o : mc.theWorld.getLoadedEntityList()) {
            if (o instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase) o;
                if (isValidEntity(entity)) {
                    gays.add(entity);
                }
            }
        }
        gays.sort((o1, o2) -> (int) (o1.getDistanceToEntity(mc.thePlayer) * 1000 - o2.getDistanceToEntity(mc.thePlayer) * 1000));
        return gays;
    }

    private ArrayList<Vec3> doStuff(Vec3 topFrom, Vec3 to) {
        if (!canPassThrow(new BlockPos(topFrom))) {
            topFrom = topFrom.addVector(0, 1, 0);
        }
        PathFinder pathfinder = new PathFinder(topFrom, to);
        pathfinder.compute();

        int i = 0;
        Vec3 lastLoc = null;
        Vec3 lastDashLoc = null;
        ArrayList<Vec3> path = new ArrayList<>();
        ArrayList<Vec3> pathFinderPath = pathfinder.getPath();
        for (Vec3 pathElm : pathFinderPath) {
            if (i == 0 || i == pathFinderPath.size() - 1) {
                if (lastLoc != null) {
                    path.add(lastLoc.addVector(0.5, 0, 0.5));
                }
                path.add(pathElm.addVector(0.5, 0, 0.5));
                lastDashLoc = pathElm;
            } else {
                boolean canContinue = true;
                if (pathElm.squareDistanceTo(lastDashLoc) > 25) {
                    canContinue = false;
                } else {
                    double smallX = Math.min(lastDashLoc.getX(), pathElm.getX());
                    double smallY = Math.min(lastDashLoc.getY(), pathElm.getY());
                    double smallZ = Math.min(lastDashLoc.getZ(), pathElm.getZ());
                    double bigX = Math.max(lastDashLoc.getX(), pathElm.getX());
                    double bigY = Math.max(lastDashLoc.getY(), pathElm.getY());
                    double bigZ = Math.max(lastDashLoc.getZ(), pathElm.getZ());
                    cordsLoop:
                    for (int x = (int) smallX; x <= bigX; x++) {
                        for (int y = (int) smallY; y <= bigY; y++) {
                            for (int z = (int) smallZ; z <= bigZ; z++) {
                                if (!PathFinder.checkPositionValidity(x, y, z, false)) {
                                    canContinue = false;
                                    break cordsLoop;
                                }
                            }
                        }
                    }
                }
                if (!canContinue) {
                    path.add(lastLoc.addVector(0.5, 0, 0.5));
                    lastDashLoc = lastLoc;
                }
            }
            lastLoc = pathElm;
            i++;
        }
        return path;
    }
}
