package it.nobusware.client.utils;

import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class PathFinder {
    private final Vec3 startVec3;
    private final Vec3 endVec3;
    private ArrayList<Vec3> path = new ArrayList<>();
    private final ArrayList<Hub> hubs = new ArrayList<>();
    private final ArrayList<Hub> hubsToWork = new ArrayList<>();

    private static final Vec3[] flatCardinalDirections = {
            new Vec3(1, 0, 0),
            new Vec3(-1, 0, 0),
            new Vec3(0, 0, 1),
            new Vec3(0, 0, -1)
    };

    public PathFinder(Vec3 startVec3, Vec3 endVec3) {
        this.startVec3 = startVec3.addVector(0, 0, 0).floor();
        this.endVec3 = endVec3.addVector(0, 0, 0).floor();
    }

    public ArrayList<Vec3> getPath() {
        return path;
    }

    public void compute() {
        path.clear();
        hubsToWork.clear();
        ArrayList<Vec3> initPath = new ArrayList<>();
        initPath.add(startVec3);
        hubsToWork.add(new Hub(startVec3, initPath, startVec3.squareDistanceTo(endVec3), 0, 0));
        search:
        for (int i = 0; i < 1000; i++) {
            hubsToWork.sort(new CompareHub());
            int j = 0;
            if (hubsToWork.size() == 0) {
                break;
            }
            for (Hub hub : new ArrayList<>(hubsToWork)) {
                j++;
                if (j > 4) {
                    break;
                } else {
                    hubsToWork.remove(hub);
                    hubs.add(hub);

                    for (Vec3 direction : flatCardinalDirections) {
                        Vec3 loc = hub.getLoc().add(direction).floor();
                        if (checkPositionValidity(loc)) {
                            if (addHub(hub, loc)) {
                                break search;
                            }
                        }
                    }

                    Vec3 loc1 = hub.getLoc().addVector(0, 1, 0).floor();
                    if (checkPositionValidity(loc1)) {
                        if (addHub(hub, loc1)) {
                            break search;
                        }
                    }

                    Vec3 loc2 = hub.getLoc().addVector(0, -1, 0).floor();
                    if (checkPositionValidity(loc2)) {
                        if (addHub(hub, loc2)) {
                            break search;
                        }
                    }
                }
            }
        }
        hubs.sort(new CompareHub());
        path = hubs.get(0).getPath();
    }

    private static boolean checkPositionValidity(Vec3 loc) {
        return checkPositionValidity((int) loc.getX(), (int) loc.getY(), (int) loc.getZ(), false);
    }

    public static boolean checkPositionValidity(int x, int y, int z, boolean checkGround) {
        BlockPos block1 = new BlockPos(x, y, z);
        BlockPos block2 = new BlockPos(x, y + 1, z);
        BlockPos block3 = new BlockPos(x, y - 1, z);
        return !isBlockSolid(block1) && !isBlockSolid(block2) && (isBlockSolid(block3) || !checkGround) && isSafeToWalkOn(block3);
    }

    private static boolean isBlockSolid(BlockPos block) {
        return Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()).isSolidFullCube() ||
                (Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockSlab) ||
                (Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockStairs)||
                (Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockCactus)||
                (Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockChest)||
                (Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockEnderChest)||
                (Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockSkull)||
                (Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockPane)||
                (Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockFence)||
                (Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockWall)||
                (Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockGlass)||
                (Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockPistonBase)||
                (Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockPistonExtension)||
                (Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockPistonMoving)||
                (Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockStainedGlass)||
                (Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockTrapDoor);
    }

    private static boolean isSafeToWalkOn(BlockPos block) {
        return !(Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockFence) &&
                !(Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockWall);
    }

    private Hub isHubExisting(Vec3 loc) {
        for (Hub hub : hubs) {
            if (hub.getLoc().getX() == loc.getX() && hub.getLoc().getY() == loc.getY() && hub.getLoc().getZ() == loc.getZ()) {
                return hub;
            }
        }
        for (Hub hub : hubsToWork) {
            if (hub.getLoc().getX() == loc.getX() && hub.getLoc().getY() == loc.getY() && hub.getLoc().getZ() == loc.getZ()) {
                return hub;
            }
        }
        return null;
    }

    private boolean addHub(Hub parent, Vec3 loc) {
        Hub existingHub = isHubExisting(loc);
        double totalCost = 0;
        if (parent != null) {
            totalCost += parent.getTotalCost();
        }
        if (existingHub == null) {
            double minDistanceSquared = 9;
            if (loc.getX() == endVec3.getX() && loc.getY() == endVec3.getY() && loc.getZ() == endVec3.getZ() || loc.squareDistanceTo(endVec3) <= minDistanceSquared) {
                path.clear();
                path = Objects.requireNonNull(parent).getPath();
                path.add(loc);
                return true;
            } else {
                ArrayList<Vec3> path = new ArrayList<>(Objects.requireNonNull(parent).getPath());
                path.add(loc);
                hubsToWork.add(new Hub(loc, path, loc.squareDistanceTo(endVec3), 0, totalCost));
            }
        } else if (existingHub.getCost() > (double) 0) {
            ArrayList<Vec3> path = new ArrayList<>(Objects.requireNonNull(parent).getPath());
            path.add(loc);
            existingHub.setLoc(loc);
            existingHub.setPath(path);
            existingHub.setSquareDistanceToFromTarget(loc.squareDistanceTo(endVec3));
            existingHub.setCost();
            existingHub.setTotalCost(totalCost);
        }
        return false;
    }

    private static class Hub {
        private Vec3 loc;
        private ArrayList<Vec3> path;
        private double squareDistanceToFromTarget;
        private double cost;
        private double totalCost;

        Hub(Vec3 loc, ArrayList<Vec3> path, double squareDistanceToFromTarget, double cost, double totalCost) {
            this.loc = loc;
            this.path = path;
            this.squareDistanceToFromTarget = squareDistanceToFromTarget;
            this.cost = cost;
            this.totalCost = totalCost;
        }

        Vec3 getLoc() {
            return loc;
        }

        public ArrayList<Vec3> getPath() {
            return path;
        }

        double getSquareDistanceToFromTarget() {
            return squareDistanceToFromTarget;
        }

        double getCost() {
            return cost;
        }

        void setLoc(Vec3 loc) {
            this.loc = loc;
        }

        public void setPath(ArrayList<Vec3> path) {
            this.path = path;
        }

        void setSquareDistanceToFromTarget(double squareDistanceToFromTarget) {
            this.squareDistanceToFromTarget = squareDistanceToFromTarget;
        }

        void setCost() {
            this.cost = 0.0;
        }

        double getTotalCost() {
            return totalCost;
        }

        void setTotalCost(double totalCost) {
            this.totalCost = totalCost;
        }
    }

    public static class CompareHub implements Comparator<Hub> {
        @Override
        public int compare(Hub o1, Hub o2) {
            return (int) (
                    (o1.getSquareDistanceToFromTarget() + o1.getTotalCost()) - (o2.getSquareDistanceToFromTarget() + o2.getTotalCost())
            );
        }
    }
}