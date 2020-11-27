// 
// Decompiled by Procyon v0.5.36
// 

package com.github.creeper123123321.viafabric.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import com.github.creeper123123321.viafabric.handler.clientside.VRDecodeHandler;
import com.github.creeper123123321.viafabric.handler.clientside.VREncodeHandler;
import com.github.creeper123123321.viafabric.platform.VRClientSideUserConnection;

import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;
import us.myles.ViaVersion.api.protocol.ProtocolPipeline;

@Mixin(targets = "net.minecraft.server.ServerNetworkIo$1")
public class MixinClientConnectionChInit {
    @Inject(method = "initChannel", at = @At(value = "TAIL"), remap = false)
    public static void onInitChannel(final Channel channel) {
        if (channel instanceof SocketChannel) {
        	System.out.println("MixinClientConnectionChInit Loaded");
            final VRClientSideUserConnection vRClientSideUserConnection = new VRClientSideUserConnection(channel);
            new ProtocolPipeline(vRClientSideUserConnection);
            channel.pipeline().addBefore("encoder", "via-encoder", new VREncodeHandler(vRClientSideUserConnection));
            channel.pipeline().addBefore("decoder", "via-decoder", new VRDecodeHandler(vRClientSideUserConnection));
        }
    }
}
