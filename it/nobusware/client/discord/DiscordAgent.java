package it.nobusware.client.discord;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;

public class DiscordAgent {

	private static boolean running = true;
	private static long created = 0;

	public static void start() {

		created = System.currentTimeMillis();

		DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback() {
			@Override
			public void apply(DiscordUser usr) {
				System.out.println("NobusWare - Verificato Per " + usr.username + "#" + usr.discriminator);
			}
		}).build();
		DiscordRPC.discordInitialize("783738589275488287", handlers, true);

		new Thread("Discord RPC") {

			@Override
			public void run() {
				while (running) {
					DiscordRPC.discordRunCallbacks();
				}
			}

		}.start();
		;
	}

	public static void shutdown() {
		running = false;
		DiscordRPC.discordShutdown();
	}

	public void update(String first, String sec) {
		DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(sec);
		b.setBigImage("suneo", "");
		b.setDetails(first);
		b.setStartTimestamps(created);
		DiscordRPC.discordUpdatePresence(b.build());
	}
}
