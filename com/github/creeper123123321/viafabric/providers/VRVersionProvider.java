// 
// Decompiled by Procyon v0.5.36
// 

package com.github.creeper123123321.viafabric.providers;

import it.nobusware.client.utils.ProtocolManager;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.protocols.base.VersionProvider;

public class VRVersionProvider extends VersionProvider
{
    @Override
    public int getServerProtocol(final UserConnection connection) throws Exception {
        return ProtocolManager.protocol;
    }
}
	