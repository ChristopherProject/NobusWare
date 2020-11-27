// 
// Decompiled by Procyon v0.5.36
// 

package com.github.creeper123123321.viafabric.handler.serverside;

import us.myles.ViaVersion.util.PipelineUtil;
import us.myles.ViaVersion.exception.CancelCodecException;
import java.util.function.Function;
import us.myles.ViaVersion.exception.CancelEncoderException;
import java.util.List;
import io.netty.channel.ChannelHandlerContext;
import us.myles.ViaVersion.api.data.UserConnection;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.MessageToMessageEncoder;

public class FabricEncodeHandler extends MessageToMessageEncoder<ByteBuf>
{
    private final UserConnection info;
    
    public FabricEncodeHandler(final UserConnection info) {
        this.info = info;
    }
    
    @Override
    protected void encode(final ChannelHandlerContext ctx, final ByteBuf bytebuf, final List<Object> out) throws Exception {
        this.info.checkOutgoingPacket();
        if (!this.info.shouldTransformPacket()) {
            out.add(bytebuf.retain());
            return;
        }
        final ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
        try {
            this.info.transformOutgoing(transformedBuf, (Function<Throwable, Exception>)CancelEncoderException::generate);
            out.add(transformedBuf.retain());
        }
        finally {
            transformedBuf.release();
        }
    }
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
        if (PipelineUtil.containsCause(cause, CancelCodecException.class)) {
            return;
        }
        super.exceptionCaught(ctx, cause);
    }
}
