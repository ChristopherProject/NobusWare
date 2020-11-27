// 
// Decompiled by Procyon v0.5.36
// 

package com.github.creeper123123321.viafabric.handler.serverside;

import us.myles.ViaVersion.util.PipelineUtil;
import us.myles.ViaVersion.exception.CancelCodecException;
import java.util.function.Function;
import us.myles.ViaVersion.exception.CancelDecoderException;
import java.util.List;
import io.netty.channel.ChannelHandlerContext;
import us.myles.ViaVersion.api.data.UserConnection;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.MessageToMessageDecoder;

public class FabricDecodeHandler extends MessageToMessageDecoder<ByteBuf>
{
    private final UserConnection info;
    
    public FabricDecodeHandler(final UserConnection info) {
        this.info = info;
    }
    
    @Override
    protected void decode(final ChannelHandlerContext ctx, final ByteBuf bytebuf, final List<Object> out) throws Exception {
        if (!this.info.checkIncomingPacket()) {
            throw CancelDecoderException.generate(null);
        }
        if (!this.info.shouldTransformPacket()) {
            out.add(bytebuf.retain());
            return;
        }
        final ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
        try {
            this.info.transformIncoming(transformedBuf, (Function<Throwable, Exception>)CancelDecoderException::generate);
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
