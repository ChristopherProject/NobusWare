// 
// Decompiled by Procyon v0.5.36
// 

package com.github.creeper123123321.viafabric.platform;

import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.platform.ViaConnectionManager;

public class VRConnectionManager extends ViaConnectionManager
{
    @Override
    public boolean isFrontEnd(final UserConnection connection) {
        return !(connection instanceof VRClientSideUserConnection);
    }
}
