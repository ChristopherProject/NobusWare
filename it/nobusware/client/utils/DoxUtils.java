package it.nobusware.client.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.json.JSONObject;

public class DoxUtils {

	private static final Pattern intPattern = Pattern.compile("-?\\d+");
	private static final ArrayList<String> OldUsername = new ArrayList<String>();

	public static void onSearch(String username) {
		ChatUtils.print("�bStart Searching �e" + username + "�b.. ");
		MinecraftOldNick(username);
	}

	private static void MinecraftOldNick(String username) {
		try {
			System.out.println("[Sistema] Search [" + username + "] Username History.");
			System.out.println("[Sistema] UUID [" + username + "] -> " + getUUID(username));
			String HistoryNameRozzi = httpRequest(new URL("https://api.mojang.com/user/profiles/" + getUUID(username) + "/names"));
			if (getUUID(username).equals("UNKNOWN_UUID")) {
				ChatUtils.print("Non � stato possible trovare la persona. ");
				System.out.println("[Warnings] NON_PREMIUM_USER");
			} else {
				String diocan = HistoryNameRozzi.replace(",", "\n").replace("{\"name\":", "").replace("}", "").replace("]", "").replace("[", "");
				String amore = diocan.replace(":", "");
				String amore_senza_change = amore.replace("\"changedToAt\"", "");
				String[] split = amore_senza_change.split("\n");
				StringBuilder b = new StringBuilder("");
				for (int i = 0; i < split.length; i++) {
					String l = split[i];
					if (!intPattern.matcher(l).matches()) {
						b.append("\n�bOld Nick�7: �e" + l);
						OldUsername.add(l.replace(" \" ",""));
					}
				}
				ChatUtils.print("Search.. " + b.toString());
				for(int g =0; g < OldUsername.size(); g++) {
					SocialMedia(OldUsername.get(g));
				}
				OldUsername.clear();
			}
		} catch (Exception e) {
			ChatUtils.print("Sembra Che Qualcosa Sia Andato Storto [INVALID_UUID] ");
		}
	}
	
	public static String getUUID(String nome) {
		try {
			URL link2 = new URL("https://api.mojang.com/users/profiles/minecraft/" + nome);
			HttpURLConnection connected2 = (HttpURLConnection) link2.openConnection();
			connected2.setRequestMethod("GET");
			connected2.setRequestProperty("Accept", "application/json");
			if (connected2.getResponseCode() == 200) {
				BufferedReader read_string = new BufferedReader(new InputStreamReader(connected2.getInputStream()));
				String pageText = read_string.lines().collect(Collectors.joining("\n"));
				JSONObject object = new JSONObject(pageText);
				read_string.close();
				connected2.disconnect();
				return object.getString("id").toString();
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return "UNKNOWN_UUID";
	}

	public static String httpRequest(URL url) {
		String result = "";
		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null)
			result = String.valueOf(String.valueOf(String.valueOf(result))) + line;
			rd.close();
		} catch (Exception ex) {
			ex.getMessage();
		}
		return result;
	}

	private static void SocialMedia(String username) {
		try {
			URL link1 = new URL("https://instagram.com/" + username);
			HttpURLConnection connected1 = (HttpURLConnection) link1.openConnection();
			connected1.setRequestMethod("GET");
			URL link2 = new URL("https://telegram.me/" + username);
			HttpURLConnection connected2 = (HttpURLConnection) link2.openConnection();
			connected2.setRequestMethod("GET");
			URL link3 = new URL("https://youtube.com/results?search_query=" + username);
			HttpURLConnection connected3 = (HttpURLConnection) link3.openConnection();
			connected3.setRequestMethod("GET");
			if (connected2.getResponseCode() == 200) {
				BufferedReader read_string = new BufferedReader(new InputStreamReader(connected2.getInputStream()));
				String pageText = read_string.lines().collect(Collectors.joining("\n"));
				if (pageText.contains("Sorry, this page isn't available.")) {
					ChatUtils.print("�4Utente non trovato su Telegram: " + username);
				}else {
					ChatUtils.print("Utente trovato su Telegram: " + username);
				}
			}else if (connected3.getResponseCode() == 200) {
				BufferedReader read_string = new BufferedReader(new InputStreamReader(connected3.getInputStream()));
				String pageText = read_string.lines().collect(Collectors.joining("\n"));
				if (pageText.contains("No results found")) {
					ChatUtils.print("�4Utente non trovato su Youtube: " + username);
				}else {
					ChatUtils.print("Utente trovato su Youtube: " + username);
				}
			}else if (connected1.getResponseCode() == 200) {
				BufferedReader read_string = new BufferedReader(new InputStreamReader(connected1.getInputStream()));
				String pageText = read_string.lines().collect(Collectors.joining("\n"));
				if (pageText.contains("Sorry, this page isn't available.")) {
					ChatUtils.print("�4Utente non trovato su Instagram: " + username);
				}else {
					ChatUtils.print("Utente trovato su Instagram: " + username);
				}
			}
		} catch (Exception e) {
			ChatUtils.print("�cFailed.");
		}
	}
}