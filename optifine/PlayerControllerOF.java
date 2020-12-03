package optifine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class PlayerControllerOF extends PlayerControllerMP
{
    private boolean acting = false;

    public PlayerControllerOF(Minecraft mcIn, NetHandlerPlayClient netHandler)
    {
        super(mcIn, netHandler);
    }

    public boolean func_180511_b(BlockPos loc, EnumFacing face)
    {
        this.acting = true;
        boolean res = super.func_180511_b(loc, face);
        this.acting = false;
        return res;
    }

    public boolean func_180512_c(BlockPos posBlock, EnumFacing directionFacing)
    {
        this.acting = true;
        boolean res = super.func_180512_c(posBlock, directionFacing);
        this.acting = false;
        return res;
    }

    /**
     * Notifies the server of things like consuming food, etc...
     */
    public boolean sendUseItem(EntityPlayer player, World worldIn, ItemStack stack)
    {
        this.acting = true;
        boolean res = super.sendUseItem(player, worldIn, stack);
        this.acting = false;
        return res;
    }

    public boolean placeBlock(EntityPlayerSP player, WorldClient worldClient, ItemStack itemStack, BlockPos blockPos, EnumFacing facing, Vec3 vec3)
    {
        this.acting = true;
        boolean res = super.placeBlock(player, worldClient, itemStack, blockPos, facing, vec3);
        this.acting = false;
        return res;
    }

    public boolean isActing()
    {
        return this.acting;
    }
}
