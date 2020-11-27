// 
// Decompiled by Procyon v0.5.36
// 

package com.github.creeper123123321.viafabric.platform;

import java.util.function.Function;
import java.lang.reflect.Method;
import java.util.Arrays;
import us.myles.ViaVersion.util.GsonUtil;
import us.myles.viaversion.libs.gson.JsonObject;
import us.myles.ViaVersion.api.platform.ViaInjector;

public class VRInjector implements ViaInjector
{
    @Override
    public void inject() {
    }
    
    @Override
    public void uninject() {
    }
    
    @Override
    public int getServerProtocolVersion() {
        return 47;
    }
    
    @Override
    public String getEncoderName() {
        return "via-encoder";
    }
    
    @Override
    public String getDecoderName() {
        return "via-decoder";
    }
    
    @Override
    public JsonObject getDump() {
        final JsonObject obj = new JsonObject();
        try {
            obj.add("serverNetworkIOChInit", GsonUtil.getGson().toJsonTree(Arrays.stream(Class.forName("net.minecraft.class_3242$1").getDeclaredMethods()).map((Function<? super Method, ?>)Method::toString).toArray(String[]::new)));
        }
        catch (ClassNotFoundException ex) {}
        return obj;
    }
}
