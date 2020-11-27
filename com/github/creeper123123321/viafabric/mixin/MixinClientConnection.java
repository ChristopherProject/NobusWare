// 
// Decompiled by Procyon v0.5.36
// 

package com.github.creeper123123321.viafabric.mixin;

import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import net.minecraft.network.NettyCompressionDecoder;
import net.minecraft.network.NettyCompressionEncoder;
import net.minecraft.network.NetworkManager;

@Mixin(NetworkManager.class)
public class MixinClientConnection {
    @Redirect(
            method = "exceptionCaught",
            remap = false,
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/apache/logging/log4j/Logger;debug(Ljava/lang/String;Ljava/lang/Throwable;)V"
            ))
    private void redirectDebug(Logger logger, String message, Throwable t) {
        if ("Failed to sent packet".equals(message)) {
            logger.info(message, t);
        } else {
            logger.debug(message, t);
        }
    }

    @Redirect(method = "setCompressionThreshold", at = @At(
            value = "INVOKE",
            remap = false,
            target = "Lio/netty/channel/ChannelPipeline;addBefore(Ljava/lang/String;Ljava/lang/String;Lio/netty/channel/ChannelHandler;)Lio/netty/channel/ChannelPipeline;"
    ))
    public static ChannelPipeline decodeEncodePlacement(final ChannelPipeline instance, String base, final String newHandler, final int threshold) {
        NettyCompressionEncoder nettyCompressionEncoder = null;
        final ChannelHandler handler = null;
        final String s = base;
        switch (s) {
            case "decoder": {
                if (instance.get("via-decoder") != null) {
                    base = "via-decoder";
                }
                final NettyCompressionDecoder nettyCompressionDecoder = new NettyCompressionDecoder(threshold);
                break;
            }
            case "encoder": {
                if (instance.get("via-encoder") != null) {
                    base = "via-encoder";
                }
                nettyCompressionEncoder = new NettyCompressionEncoder(threshold);
                break;
            }
        }
        return instance.addBefore(base, newHandler, nettyCompressionEncoder);
    }
}
