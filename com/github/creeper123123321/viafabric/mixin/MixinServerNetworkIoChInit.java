// 
// Decompiled by Procyon v0.5.36
// 

package com.github.creeper123123321.viafabric.mixin;

import com.github.creeper123123321.viafabric.handler.serverside.FabricDecodeHandler;
import io.netty.channel.ChannelHandler;
import com.github.creeper123123321.viafabric.handler.serverside.FabricEncodeHandler;
import us.myles.ViaVersion.api.protocol.ProtocolPipeline;
import us.myles.ViaVersion.api.data.UserConnection;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.Channel;

public class MixinServerNetworkIoChInit
{
    private void onInitChannel(final Channel channel) {
        if (channel instanceof SocketChannel) {
            final UserConnection user = new UserConnection(channel);
            new ProtocolPipeline(user);
            channel.pipeline().addBefore("encoder", "via-encoder", new FabricEncodeHandler(user));
            channel.pipeline().addBefore("decoder", "via-decoder", new FabricDecodeHandler(user));
        }
    }
}
