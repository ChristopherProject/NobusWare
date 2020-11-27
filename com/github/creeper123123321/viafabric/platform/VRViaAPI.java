// 
// Decompiled by Procyon v0.5.36
// 

package com.github.creeper123123321.viafabric.platform;

import java.util.Collection;
import java.util.TreeSet;
import us.myles.ViaVersion.api.protocol.ProtocolRegistry;
import java.util.SortedSet;
import us.myles.ViaVersion.api.boss.BossBar;
import us.myles.ViaVersion.api.boss.BossStyle;
import us.myles.ViaVersion.api.boss.BossColor;
import io.netty.buffer.ByteBuf;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.Via;
import java.util.UUID;
import us.myles.ViaVersion.api.ViaAPI;

public class VRViaAPI implements ViaAPI<UUID>
{
    @Override
    public int getPlayerVersion(final UUID uuid) {
        final UserConnection con = Via.getManager().getConnection(uuid);
        if (con != null) {
            return con.getProtocolInfo().getProtocolVersion();
        }
        try {
            return Via.getManager().getInjector().getServerProtocolVersion();
        }
        catch (Exception e) {
            throw new AssertionError((Object)e);
        }
    }
    
    @Override
    public boolean isInjected(final UUID uuid) {
        return Via.getManager().isClientConnected(uuid);
    }
    
    @Override
    public String getVersion() {
        return Via.getPlatform().getPluginVersion();
    }
    
    @Override
    public void sendRawPacket(final UUID uuid, final ByteBuf byteBuf) throws IllegalArgumentException {
        final UserConnection ci = Via.getManager().getConnection(uuid);
        ci.sendRawPacket(byteBuf);
    }
    
    @Override
    public BossBar<Void> createBossBar(final String s, final BossColor bossColor, final BossStyle bossStyle) {
        return new VRBossBar(s, 1.0f, bossColor, bossStyle);
    }
    
    @Override
    public BossBar<Void> createBossBar(final String s, final float v, final BossColor bossColor, final BossStyle bossStyle) {
        return new VRBossBar(s, v, bossColor, bossStyle);
    }
    
    @Override
    public SortedSet<Integer> getSupportedVersions() {
        final SortedSet<Integer> outputSet = new TreeSet<Integer>(ProtocolRegistry.getSupportedVersions());
        outputSet.removeAll(Via.getPlatform().getConf().getBlockedProtocols());
        return outputSet;
    }
}
