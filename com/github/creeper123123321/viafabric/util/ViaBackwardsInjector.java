// 
// Decompiled by Procyon v0.5.36
// 

package com.github.creeper123123321.viafabric.util;

import java.util.logging.Logger;
import nl.matsv.viabackwards.api.ViaBackwardsPlatform;
import org.apache.logging.log4j.LogManager;
import java.io.File;

public class ViaBackwardsInjector
{
    public static void initialize(final File dir) {
        final LoggerWrapper lw = new LoggerWrapper(LogManager.getLogger("ViaBackwards"));
        final ViaBackwardsPlatform viaBackwards = new ViaBackwardsPlatform() {
            @Override
            public Logger getLogger() {
                return lw;
            }
            
            @Override
            public File getDataFolder() {
                return dir;
            }
            
            @Override
            public void disable() {
            }
        };
        viaBackwards.init(dir);
    }
}
