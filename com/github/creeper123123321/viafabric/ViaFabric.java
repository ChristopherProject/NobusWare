// 
// Decompiled by Procyon v0.5.36
// 

package com.github.creeper123123321.viafabric;

import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Logger;

import org.apache.logging.log4j.LogManager;

import com.github.creeper123123321.viafabric.commands.VRCommandHandler;
import com.github.creeper123123321.viafabric.platform.VRInjector;
import com.github.creeper123123321.viafabric.platform.VRLoader;
import com.github.creeper123123321.viafabric.platform.VRPlatform;
import com.github.creeper123123321.viafabric.util.DefaultEventLoop;
import com.github.creeper123123321.viafabric.util.JLoggerToLog4j;
import com.github.creeper123123321.viafabric.util.ProtocolSorter;
import com.github.creeper123123321.viafabric.util.ViaBackwardsInjector;
import com.github.creeper123123321.viafabric.util.ViaRewindInjector;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import io.netty.channel.EventLoop;
import net.minecraft.client.Minecraft;
import us.myles.ViaVersion.ViaManager;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.MappingDataLoader;

public class ViaFabric
{
    public static final Logger JLOGGER;
    public static final ExecutorService ASYNC_EXECUTOR;
    public static final EventLoop EVENT_LOOP;
    
    public static String getVersion() {
        return "1.0.0";
    }
    
    public void onInitialize() {
        final File viaVersionDir = new File(new File(Minecraft.getMinecraft().mcDataDir + "/" + "NobusWare"), "viaversion");
        Via.init(ViaManager.builder().injector(new VRInjector()).loader(new VRLoader()).commandHandler(new VRCommandHandler()).platform(new VRPlatform()).build());
        boolean viaBackwardsLoaded = false;
        try {
            Class.forName("nl.matsv.viabackwards.ViaBackwards");
            MappingDataLoader.enableMappingsCache();
            viaBackwardsLoaded = true;
        }
        catch (ClassNotFoundException ex) {}
        Via.getManager().init();
        try {
            if (viaBackwardsLoaded) {
                final Path configDirPath = viaVersionDir.toPath().resolve("ViaBackwards");
                ViaBackwardsInjector.initialize(configDirPath.toFile());
            }
        }
        catch (Throwable t) {
            System.err.println("Failed to initialize ViaBackwards: " + t.getClass().getSimpleName() + " - " + t.getMessage());
        }
        try {
            Class.forName("de.gerrygames.viarewind.ViaRewind");
            ViaRewindInjector.initialize(viaVersionDir);
        }
        catch (Throwable t) {
            System.err.println("Failed to initialize ViaRewind: " + t.getClass().getSimpleName() + " - " + t.getMessage());
        }
        ProtocolSorter.init();
    }
    
    static {
        JLOGGER = new JLoggerToLog4j(LogManager.getLogger("ViaFabric"));
        final ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("ViaFabric-%d").build();
        ASYNC_EXECUTOR = Executors.newFixedThreadPool(8, factory);
        EVENT_LOOP = new DefaultEventLoop(factory);
    }
}
