package it.nobusware.client.utils;

import net.minecraft.client.Minecraft;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.stream.Collectors;

public class Checker {
	
	public static void PayLoad() {
/*		getUpdates();
		getManitence();
		SystemHWID();*/
	}

	private static void SystemHWID() {
	    try {
	      String sb = getInfoHwid();
	      if (!get("https://pastebin.com/raw/MMvPbV7H").contains(sb)) {
	        JTextArea text = new JTextArea("Copy This And Send To Developer -> " + sb);
	        JOptionPane.showMessageDialog(null, text, "@AdrianCodee On Telegram", 1);
	        Minecraft.getMinecraft().shutdownMinecraftApplet();
	        System.exit(0);
	      } else {
	    	  System.out.println("Verified.");
	      }
	    } catch (IOException| NoSuchAlgorithmException el) {
	      Minecraft.getMinecraft().shutdownMinecraftApplet();
	      el.printStackTrace();
	    } 
	  }
	
	private static void getUpdates() {
		try {
			URL link2 = new URL("https://nobitaclient.000webhostapp.com/Updates.json");
			HttpURLConnection connected2 = (HttpURLConnection) link2.openConnection();
			connected2.setRequestMethod("GET");
			connected2.setRequestProperty("Accept", "application/json");
			if (connected2.getResponseCode() == 200) {
				BufferedReader read_string = new BufferedReader(new InputStreamReader(connected2.getInputStream()));
				String pageText = read_string.lines().collect(Collectors.joining("\n"));
				JSONObject object = new JSONObject(pageText);
				if (object.getBoolean("update") == true) {
					JOptionPane.showMessageDialog(null, "Nuovo Update Disponibile!!", "Avviso ~ AdrianCode >.<", 1);
					Minecraft.getMinecraft().shutdown();
				}
				read_string.close();
				connected2.disconnect();
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}

	private static void getManitence() {
		try {
			URL link2 = new URL("https://nobitaclient.000webhostapp.com/Updates.json");
			HttpURLConnection connected2 = (HttpURLConnection) link2.openConnection();
			connected2.setRequestMethod("GET");
			connected2.setRequestProperty("Accept", "application/json");
			if (connected2.getResponseCode() == 200) {
				BufferedReader read_string = new BufferedReader(new InputStreamReader(connected2.getInputStream()));
				String pageText = read_string.lines().collect(Collectors.joining("\n"));
				JSONObject object = new JSONObject(pageText);
				if (object.getBoolean("manitence") == true) {
					JOptionPane.showMessageDialog(null, "Siamo Attualmente in Manutenzione.", "Avviso ~ AdrianCode >.<",1);
					Minecraft.getMinecraft().shutdown();
				}
				read_string.close();
				connected2.disconnect();
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}

	private static String getInfoHwid() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		StringBuilder s = new StringBuilder();
		String main = String.valueOf(System.getenv("PROCESSOR_IDENTIFIER")) + System.getenv("COMPUTERNAME")
				+ System.getProperty("user.name").trim();
		byte[] bytes = main.getBytes("UTF-8");
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		byte[] md5 = messageDigest.digest(bytes);
		int i = 0;
		byte[] arrayOfByte1;
		int j = (arrayOfByte1 = md5).length;
		for (int a = 0; i < j; i++) {
			byte b = arrayOfByte1[i];
			s.append(Integer.toHexString(b & 0xFF | 0x300), 0, 3);
			if (i != md5.length - 1)
				s.append("-");
			i++;
		}
		return s.toString();
	}

	private static String get(String url) throws IOException {
		HttpURLConnection con = (HttpURLConnection) (new URL(url)).openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		StringBuilder response = new StringBuilder();
		String inputLine;
		while ((inputLine = in.readLine()) != null)
			response.append(inputLine).append("\n");
		in.close();
		return response.toString();
	}

	private static String post(String url, Map<String, String> requestMap, String body) throws IOException {
		HttpURLConnection con = (HttpURLConnection) (new URL(url)).openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		if (requestMap != null)
			requestMap.forEach(con::setRequestProperty);
		con.setDoOutput(true);
		con.setDoInput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(body);
		wr.flush();
		wr.close();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		StringBuilder response = new StringBuilder();
		String inputLine;
		while ((inputLine = in.readLine()) != null)
			response.append(inputLine).append("\n");
		in.close();
		return response.toString();
	}
}