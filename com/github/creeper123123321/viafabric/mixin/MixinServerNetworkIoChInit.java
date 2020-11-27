// 
// Decompiled by Procyon v0.5.36
// 

package com.github.creeper123123321.viafabric.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import com.github.creeper123123321.viafabric.handler.serverside.FabricDecodeHandler;
import com.github.creeper123123321.viafabric.handler.serverside.FabricEncodeHandler;

import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;
import net.minecraft.network.NetworkSystem;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.protocol.ProtocolPipeline;

@Mixin(NetworkSystem.class)
public class MixinServerNetworkIoChInit {
    @Inject(method = "initChannel", at = @At(value = "TAIL"), remap = false)
    private void onInitChannel(final Channel channel) {
        if (channel instanceof SocketChannel) {
            final UserConnection user = new UserConnection(channel);
            new ProtocolPipeline(user);
            channel.pipeline().addBefore("encoder", "via-encoder", new FabricEncodeHandler(user));
            channel.pipeline().addBefore("decoder", "via-decoder", new FabricDecodeHandler(user));
        }
    }
}
