// 
// Decompiled by Procyon v0.5.36
// 

package com.github.creeper123123321.viafabric.platform;

import java.util.Arrays;
import java.util.Map;
import java.net.URL;
import java.io.File;
import java.util.List;
import us.myles.ViaVersion.AbstractViaConfig;

public class VRViaConfig extends AbstractViaConfig
{
    private static List<String> UNSUPPORTED;
    
    public VRViaConfig(final File configFile) {
        super(configFile);
        this.reloadConfig();
    }
    
    @Override
    public URL getDefaultConfigURL() {
        return this.getClass().getClassLoader().getResource("assets/viaversion/config.yml");
    }
    
    @Override
    protected void handleConfig(final Map<String, Object> config) {
    }
    
    @Override
    public List<String> getUnsupportedOptions() {
        return VRViaConfig.UNSUPPORTED;
    }
    
    @Override
    public boolean isAntiXRay() {
        return false;
    }
    
    @Override
    public boolean isItemCache() {
        return false;
    }
    
    @Override
    public boolean isNMSPlayerTicking() {
        return false;
    }
    
    @Override
    public boolean is1_12QuickMoveActionFix() {
        return false;
    }
    
    @Override
    public String getBlockConnectionMethod() {
        return "packet";
    }
    
    @Override
    public boolean is1_9HitboxFix() {
        return false;
    }
    
    @Override
    public boolean is1_14HitboxFix() {
        return false;
    }
    
    static {
        VRViaConfig.UNSUPPORTED = Arrays.asList("anti-xray-patch", "bungee-ping-interval", "bungee-ping-save", "bungee-servers", "quick-move-action-fix", "nms-player-ticking", "item-cache", "velocity-ping-interval", "velocity-ping-save", "velocity-servers", "blockconnection-method", "change-1_9-hitbox", "change-1_14-hitbox");
    }
}
