// 
// Decompiled by Procyon v0.5.36
// 

package com.github.creeper123123321.viafabric.commands;

import java.util.UUID;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.command.ViaCommandSender;

public class UserCommandSender implements ViaCommandSender
{
    private UserConnection con;
    
    public UserCommandSender(final UserConnection con) {
        this.con = con;
    }
    
    @Override
    public boolean hasPermission(final String s) {
        return false;
    }
    
    @Override
    public void sendMessage(final String s) {
        Via.getPlatform().sendMessage(this.getUUID(), s);
    }
    
    @Override
    public UUID getUUID() {
        return this.con.getProtocolInfo().getUuid();
    }
    
    @Override
    public String getName() {
        return this.con.getProtocolInfo().getUsername();
    }
}
