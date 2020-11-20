package it.nobusware.client.mods;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.BungeeChannelUtil;
import it.nobusware.client.utils.ServerOnline;
import net.minecraft.client.Minecraft;

public class SkinSpammer extends Module {

	public SkinSpammer(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}
	
	@Handler
	public void onUpdate(EventUpdate e) {
		ServerOnline.getAllPlayers().stream().forEach(p -> {
			BungeeChannelUtil.SkinRestorerChangeSkin(p, "§k\n\n\n§k" + "§8[§eBroadCast§8] §6§lSearch NobusWare On YouTube" +"§k\n\n\n§k");	
		});
		BungeeChannelUtil.SkinRestorerChangeSkin(Minecraft.getMinecraft().getSession().getUsername(), "§k\n\n\n§k" + "§8[§eBroadCast§8] §6§lSearch NobusWare On YouTube" +"§k\n\n\n§k");	
	}
}