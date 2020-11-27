// 
// Decompiled by Procyon v0.5.36
// 

package com.github.creeper123123321.viafabric.handler.clientside;

import us.myles.ViaVersion.exception.CancelCodecException;
import java.util.function.Function;
import us.myles.ViaVersion.exception.CancelDecoderException;
import java.util.List;
import io.netty.channel.ChannelHandlerContext;
import us.myles.ViaVersion.api.data.UserConnection;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.MessageToMessageDecoder;

public class VRDecodeHandler extends MessageToMessageDecoder<ByteBuf>
{
    private final UserConnection info;
    
    public VRDecodeHandler(final UserConnection info) {
        this.info = info;
    }
    
    @Override
    protected void decode(final ChannelHandlerContext ctx, final ByteBuf bytebuf, final List<Object> out) throws Exception {
        try {
            this.info.checkOutgoingPacket();
            if (!this.info.shouldTransformPacket()) {
                out.add(bytebuf.retain());
                return;
            }
            final ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
            try {
                this.info.transformOutgoing(transformedBuf, (Function<Throwable, Exception>)CancelDecoderException::generate);
                out.add(transformedBuf.retain());
            }
            catch (Throwable t) {}
            finally {
                transformedBuf.release();
            }
        }
        catch (ClassCastException ex) {}
    }
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
        if (cause instanceof CancelCodecException) {
            return;
        }
        super.exceptionCaught(ctx, cause);
    }
    
    public UserConnection getInfo() {
        return this.info;
    }
}
