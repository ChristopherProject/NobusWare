// 
// Decompiled by Procyon v0.5.36
// 

package com.github.creeper123123321.viafabric.util;

import io.netty.util.concurrent.DefaultThreadFactory;
import java.util.concurrent.ThreadFactory;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SingleThreadEventLoop;

public class DefaultEventLoop extends SingleThreadEventLoop
{
    public DefaultEventLoop() {
        this((EventLoopGroup)null);
    }
    
    public DefaultEventLoop(final ThreadFactory threadFactory) {
        this(null, threadFactory);
    }
    
    public DefaultEventLoop(final EventLoopGroup parent) {
        this(parent, new DefaultThreadFactory(DefaultEventLoop.class));
    }
    
    public DefaultEventLoop(final EventLoopGroup parent, final ThreadFactory threadFactory) {
        super(parent, threadFactory, true);
    }
    
    @Override
    protected void run() {
        do {
            final Runnable task = this.takeTask();
            if (task == null) {
                continue;
            }
            task.run();
            this.updateLastExecutionTime();
        } while (!this.confirmShutdown());
    }
}
