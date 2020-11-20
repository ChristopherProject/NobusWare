package it.nobusware.client.mods;

import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.lang3.RandomUtils;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.Timer;

public class StupidFlood extends Module {
	
	private Timer jotarogay;
	
	String message = "";
	String message1 = "";
	String message2 = "";
	String message3 = "";
	String message4 = "";
	
	private static ArrayList<String> asd = new ArrayList<String>(); 

	public StupidFlood(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
		this.jotarogay = new Timer();
		setDangerous(true);
	}
	
	
	public void Abilitato() {
		asd.add("asddasdadasdasdasdasdasdasdasdasdasdasdasdasdasdasdasd");
		asd.add("asdasdasdasdasdasdasdasdassdfsdfsdf");
		asd.add("asdasgdfgwe,hgwerhwegfjasdgfjasdgfjsdgfsdjgf");
		asd.add("jkjzdfhjsdfjhdfgklhjasnzsdfheflhjwelj");
		asd.add("34asdkdfasdfgasdjkgasdjgtasdjgdjhasgdjhg");
		asd.add("nsdvbasdfkasgkjzxbhchzxvcjzxhbgcasmhvfasjvgashvasncasv");
		asd.add("fsdfjsdlfkhjsdfnzxcvknhwehyrwehrkjwebrkherjwekjrh");
		asd.add("rnhthtklcfhckovn ghlffnjugòfgdfkgdkfjgllsddlsdjfl");
		asd.add("fsdfskjfrrusdkjsdkfhsdkjfbhzxdkygwgahawjdeghawj");
		asd.add("sdnvsdfj,bhgdfkjbncv,mbndfsigfbncvkjbhdfkbvjdfb,kn");
		asd.add("mfdfklgdfljkdflgjdfgerklgfjrklgjg,jgkjdfkg");
	}
	
	@Handler
	public void onGayMan(EventUpdate e) {
		if(jotarogay.aSecondi(10)) {
			message = asd.get(RandomUtils.nextInt(0, 10));
			mc.thePlayer.sendChatMessage(message);
		}
		if(jotarogay.aSecondi(20)) {
			message1 = asd.get(RandomUtils.nextInt(0, 10));
			mc.thePlayer.sendChatMessage(message1);
		}
		if(jotarogay.aSecondi(30)) {
			message2 = asd.get(RandomUtils.nextInt(0, 10));
			mc.thePlayer.sendChatMessage(message2);
		}
		if(jotarogay.aSecondi(40)) {
			message3 = asd.get(RandomUtils.nextInt(0, 10));
			mc.thePlayer.sendChatMessage(message3);
		}
		if(jotarogay.aSecondi(50)) {
			message4 = asd.get(RandomUtils.nextInt(0, 10));
			mc.thePlayer.sendChatMessage(message4);
			jotarogay.reset();
		}
	}
}