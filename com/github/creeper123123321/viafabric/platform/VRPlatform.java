// 
// Decompiled by Procyon v0.5.36
// 

package com.github.creeper123123321.viafabric.platform;

import java.io.File;
import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Logger;

import com.github.creeper123123321.viafabric.ViaFabric;
import com.github.creeper123123321.viafabric.commands.UserCommandSender;
import com.github.creeper123123321.viafabric.util.FutureTaskId;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.ViaAPI;
import us.myles.ViaVersion.api.ViaVersionConfig;
import us.myles.ViaVersion.api.command.ViaCommandSender;
import us.myles.ViaVersion.api.configuration.ConfigurationProvider;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.platform.TaskId;
import us.myles.ViaVersion.api.platform.ViaConnectionManager;
import us.myles.ViaVersion.api.platform.ViaPlatform;
import us.myles.ViaVersion.sponge.VersionInfo;
import us.myles.viaversion.libs.gson.JsonObject;

public class VRPlatform implements ViaPlatform<UUID>
{
    private final VRViaConfig config;
    private final File dataFolder;
    private final ViaConnectionManager connectionManager;
    private final ViaAPI<UUID> api;
    
    public VRPlatform() {
        final Path configDir = new File(new File(Minecraft.getMinecraft().mcDataDir + "/" + "NobusWare"), "viaversion").toPath().resolve("ViaVersion");
        this.config = new VRViaConfig(configDir.resolve("viaversion.yml").toFile());
        this.dataFolder = configDir.toFile();
        this.connectionManager = new VRConnectionManager();
        this.api = new VRViaAPI();
    }
    
    public static MinecraftServer getServer() {
        return getIntegratedServer();
    }
    
    private static MinecraftServer getIntegratedServer() {
        return null;
    }
    
    @Override
    public Logger getLogger() {
        return ViaFabric.JLOGGER;
    }
    
    @Override
    public String getPlatformName() {
        return "ViaVersion Electrum";
    }
    
    @Override
    public String getPlatformVersion() {
        return ViaFabric.getVersion();
    }
    
    @Override
    public String getPluginVersion() {
        try {
            return VersionInfo.class.getField("VERSION").get(null).toString();
        }
        catch (IllegalAccessException | NoSuchFieldException ex2) {
            ex2.printStackTrace();
            return "?";
        }
    }
    
    @Override
    public TaskId runAsync(final Runnable runnable) {
        return new FutureTaskId(CompletableFuture.runAsync(runnable, ViaFabric.ASYNC_EXECUTOR).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        }));
    }
    
    @Override
    public TaskId runSync(final Runnable runnable) {
        return this.runEventLoop(runnable);
    }
    
    private TaskId runEventLoop(final Runnable runnable) {
        return new FutureTaskId(CompletableFuture.runAsync(runnable, ViaFabric.EVENT_LOOP).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        }));
    }
    
    @Override
    public TaskId runSync(final Runnable runnable, final Long ticks) {
        return new FutureTaskId(ViaFabric.EVENT_LOOP.schedule(runnable, ticks * 50L, TimeUnit.MILLISECONDS).addListener(future -> {
            if (!future.isSuccess()) {
                future.cause().printStackTrace();
            }
        }));
    }
    
    @Override
    public TaskId runRepeatingSync(final Runnable runnable, final Long ticks) {
        return new FutureTaskId(ViaFabric.EVENT_LOOP.scheduleAtFixedRate(runnable, 0L, ticks * 50L, TimeUnit.MILLISECONDS).addListener(future -> {
            if (!future.isSuccess()) {
                future.cause().printStackTrace();
            }
        }));
    }
    
    @Override
    public void cancelTask(final TaskId taskId) {
        if (taskId instanceof FutureTaskId) {
            ((FutureTaskId)taskId).getObject().cancel(false);
        }
    }
    
    @Override
    public ViaCommandSender[] getOnlinePlayers() {
        final MinecraftServer server = getServer();
        if (server != null) {
            return this.getServerPlayers();
        }
        return Via.getManager().getConnectedClients().values().stream().map((Function<? super UserConnection, ?>)UserCommandSender::new).toArray(ViaCommandSender[]::new);
    }
    
    private ViaCommandSender[] getServerPlayers() {
        return new ViaCommandSender[0];
    }
    
    @Override
    public void sendMessage(final UUID uuid, final String s) {
        final UserConnection user = Via.getManager().getConnection(uuid);
        this.sendMessageClient(s);
    }
    
    private void sendMessageClient(final String s) {
    }
    
    @Override
    public boolean kickPlayer(final UUID uuid, final String s) {
        final UserConnection user = Via.getManager().getConnection(uuid);
        return this.kickClient(s);
    }
    
    private boolean kickClient(final String msg) {
        return false;
    }
    
    @Override
    public boolean isPluginEnabled() {
        return true;
    }
    
    @Override
    public ViaAPI<UUID> getApi() {
        return this.api;
    }
    
    @Override
    public ViaVersionConfig getConf() {
        return this.config;
    }
    
    @Override
    public ConfigurationProvider getConfigurationProvider() {
        return this.config;
    }
    
    @Override
    public File getDataFolder() {
        return this.dataFolder;
    }
    
    @Override
    public void onReload() {
    }
    
    @Override
    public JsonObject getDump() {
        final JsonObject platformSpecific = new JsonObject();
        return platformSpecific;
    }
    
    @Override
    public boolean isOldClientsAllowed() {
        return true;
    }
    
    @Override
    public ViaConnectionManager getConnectionManager() {
        return this.connectionManager;
    }
}
