package net.minecraft.network.play.client;

import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.BlockPos;

import java.io.IOException;

public class C08PacketPlayerBlockPlacement implements Packet
{
    private static final BlockPos field_179726_a = new BlockPos(-1, -1, -1);
    private BlockPos field_179725_b;
    private int placedBlockDirection;
    private ItemStack stack;
    private float facingX;
    private float facingY;
    private float facingZ;
    private static final String __OBFID = "CL_00001371";

    public C08PacketPlayerBlockPlacement() {}

    public C08PacketPlayerBlockPlacement(ItemStack itemStack)
    {
        this(field_179726_a, 255, itemStack, 0.0F, 0.0F, 0.0F);
    }

    public C08PacketPlayerBlockPlacement(BlockPos blockPos, ItemStack itemStack)
    {
        this(blockPos, 255, itemStack, 0.0F, 0.0F, 0.0F);
    }

    public C08PacketPlayerBlockPlacement(BlockPos blockPos, int blockDirection, ItemStack itemStack, float facingX, float facingY, float facingZ)
    {
        this.field_179725_b = blockPos;
        this.placedBlockDirection = blockDirection;
        this.stack = itemStack != null ? itemStack.copy() : null;
        this.facingX = facingX;
        this.facingY = facingY;
        this.facingZ = facingZ;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer data) throws IOException
    {
        this.field_179725_b = data.readBlockPos();
        this.placedBlockDirection = data.readUnsignedByte();
        this.stack = data.readItemStackFromBuffer();
        this.facingX = (float)data.readUnsignedByte() / 16.0F;
        this.facingY = (float)data.readUnsignedByte() / 16.0F;
        this.facingZ = (float)data.readUnsignedByte() / 16.0F;
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer data) throws IOException
    {
        data.writeBlockPos(this.field_179725_b);
        data.writeByte(this.placedBlockDirection);
        data.writeItemStackToBuffer(this.stack);
        data.writeByte((int)(this.facingX * 16.0F));
        data.writeByte((int)(this.facingY * 16.0F));
        data.writeByte((int)(this.facingZ * 16.0F));
    }

    public void func_180769_a(INetHandlerPlayServer p_180769_1_)
    {
        p_180769_1_.processPlayerBlockPlacement(this);
    }

    public BlockPos getBlockPos()
    {
        return this.field_179725_b;
    }

    public int getPlacedBlockDirection()
    {
        return this.placedBlockDirection;
    }

    public ItemStack getStack()
    {
        return this.stack;
    }

    /**
     * Returns the offset from xPosition where the actual click took place.
     */
    public float getPlacedBlockOffsetX()
    {
        return this.facingX;
    }

    /**
     * Returns the offset from yPosition where the actual click took place.
     */
    public float getPlacedBlockOffsetY()
    {
        return this.facingY;
    }

    /**
     * Returns the offset from zPosition where the actual click took place.
     */
    public float getPlacedBlockOffsetZ()
    {
        return this.facingZ;
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandler handler)
    {
        this.func_180769_a((INetHandlerPlayServer)handler);
    }
}
