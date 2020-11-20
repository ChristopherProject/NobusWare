package it.nobusware.client.events;

import QuarantineAPI.Event;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;

public class CollisionEvent extends Event {

    public AxisAlignedBB axisalignedbb;
    public Block block;
    private Entity collidingEntity;
    private int x, y, z;

    public CollisionEvent(Entity collidingEntity, int x, int y, int z, AxisAlignedBB axisalignedbb, Block block) {
        this.collidingEntity = collidingEntity;
        this.x = x;
        this.y = y;
        this.z = z;
        this.axisalignedbb = axisalignedbb;
        this.block = block;
    }

    public AxisAlignedBB getBoundingBox() {
        return this.axisalignedbb;
    }

    public Entity getCollidingEntity() {
        return collidingEntity;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public Block getBlock() {
        return block;
    }

    public void setBoundingBox(AxisAlignedBB object) {
        this.axisalignedbb = object;
    }
}
