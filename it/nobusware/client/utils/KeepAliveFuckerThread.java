package it.nobusware.client.utils;

import io.netty.util.NetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

public class KeepAliveFuckerThread extends Thread {
	
    private final long delay;
    private final Packet packet;

    public KeepAliveFuckerThread(Packet packet, long delay) {
        super("Disabler Thread (KeepAlive)");
        this.packet = packet;
        this.delay = delay;
    }

    public void run() {
        try {
            KeepAliveFuckerThread.sleep(this.delay);
            Minecraft.getMinecraft().thePlayer.sendQueue.noEventPacket(this.packet);
            System.out.println("Thread" + this.packet.toString());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
