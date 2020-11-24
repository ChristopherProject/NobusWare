package it.nobusware.client.utils;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class BlockData {
	public EnumFacing face;

	public BlockPos position;

	public BlockData(BlockPos position, EnumFacing face) {
		this.position = position;
		this.face = face;
	}
}
