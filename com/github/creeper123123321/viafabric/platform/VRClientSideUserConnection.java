// 
// Decompiled by Procyon v0.5.36
// 

package com.github.creeper123123321.viafabric.platform;

import io.netty.channel.ChannelFuture;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import us.myles.ViaVersion.api.data.UserConnection;

public class VRClientSideUserConnection extends UserConnection
{
    public VRClientSideUserConnection(final Channel socketChannel) {
        super(socketChannel);
    }
    
    @Override
    public void sendRawPacket(final ByteBuf packet, final boolean currentThread) {
        try {
            final Runnable act = () -> {
                try {
                    this.getChannel().pipeline().context("via-decoder").fireChannelRead(packet);
                }
                catch (Throwable t) {}
                return;
            };
            if (currentThread) {
                act.run();
            }
            else {
                this.getChannel().eventLoop().execute(act);
            }
        }
        catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public ChannelFuture sendRawPacketFuture(final ByteBuf packet) {
        this.getChannel().pipeline().context("via-decoder").fireChannelRead(packet);
        return this.getChannel().newSucceededFuture();
    }
    
    @Override
    public void sendRawPacketToServer(final ByteBuf packet, final boolean currentThread) {
        if (currentThread) {
            this.getChannel().pipeline().context("via-encoder").writeAndFlush(packet);
        }
        else {
            this.getChannel().eventLoop().submit(() -> this.getChannel().pipeline().context("via-encoder").writeAndFlush(packet));
        }
    }
}
