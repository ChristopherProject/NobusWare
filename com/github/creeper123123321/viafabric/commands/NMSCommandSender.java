// 
// Decompiled by Procyon v0.5.36
// 

package com.github.creeper123123321.viafabric.commands;

import java.util.UUID;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.ICommandSender;
import us.myles.ViaVersion.api.command.ViaCommandSender;

public class NMSCommandSender implements ViaCommandSender
{
    private final ICommandSender source;
    
    public NMSCommandSender(final ICommandSender source) {
        this.source = source;
    }
    
    @Override
    public boolean hasPermission(final String s) {
        return this.source.canCommandSenderUseCommand(3, s);
    }
    
    @Override
    public void sendMessage(final String s) {
        ((EntityPlayerSP)this.source).addChatMessage(new ChatComponentText(s));
    }
    
    @Override
    public UUID getUUID() {
        return ((EntityPlayerSP)this.source).getUniqueID();
    }
    
    @Override
    public String getName() {
        return ((EntityPlayerSP)this.source).getName();
    }
}
