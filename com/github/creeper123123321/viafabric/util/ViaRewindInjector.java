// 
// Decompiled by Procyon v0.5.36
// 

package com.github.creeper123123321.viafabric.util;

import de.gerrygames.viarewind.api.ViaRewindConfig;
import org.apache.logging.log4j.LogManager;
import java.util.logging.Logger;
import de.gerrygames.viarewind.api.ViaRewindPlatform;
import de.gerrygames.viarewind.api.ViaRewindConfigImpl;
import java.io.File;

public class ViaRewindInjector
{
    public static void initialize(final File dir) {
        try {
            Class.forName("de.gerrygames.viarewind.ViaRewind");
            final ViaRewindConfigImpl conf = new ViaRewindConfigImpl(dir.toPath().resolve("ViaRewind").resolve("config.yml").toFile());
            conf.reloadConfig();
            new ViaRewindPlatform() {
                private final Logger logger = new LoggerWrapper(LogManager.getLogger("ViaRewind"));
                
                @Override
                public Logger getLogger() {
                    return this.logger;
                }
            }.init(conf);
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
