package it.nobusware.client.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import it.nobusware.client.manager.Module;
import net.minecraft.client.Minecraft;

public class ConfigManager {
	
	private File dir;

	private ArrayList<Config> configs = new ArrayList<>();

	public Config currentConfig;

	public ConfigManager(File dir) {
		this.dir = dir;
	}

	public ArrayList<Config> getConfigs() {
		return this.configs;
	}

	public void clear() throws IOException {
		FileUtils.cleanDirectory(new File(this.dir + "/"));
	}

	public void setCurrentConfig(Config config) {
		this.currentConfig = config;
	}

	public void load() {
		if (!this.dir.exists())
			this.dir.mkdirs();
		if (this.dir != null) {
			File[] files = this.dir
					.listFiles(f -> (!f.isDirectory() && FilenameUtils.getExtension(f.getName()).equals("json")));
			for (File f : files) {
				Config config = new Config(FilenameUtils.removeExtension(f.getName()).replace(" ", ""));
				setCurrentConfig(config);
				this.configs.add(config);
			}
		}
	}

	public void deleteConfig(String cfgname) {
		File f = new File(this.dir, cfgname + ".json");
		if (f.exists())
			try {
				Files.delete(f.toPath());
			} catch (IOException iOException) {
			}
		Minecraft.getMinecraft().getNobita().getConfigManager().getConfigs().remove(getConfig(cfgname));
	}

	public void saveConfig(String cfgname, boolean key) {
		File f = new File(this.dir, cfgname + ".json");
		if (!f.exists())
			try {
				f.createNewFile();
			} catch (IOException iOException) {
			}
		JsonArray arr = new JsonArray();
		Minecraft.getMinecraft().getNobita().getModManager().getHackDelClient().forEach(module -> {
			JsonObject obj = new JsonObject();
			obj.addProperty("name", module.getNome_mod().toLowerCase());
			module.save(obj, key);
			arr.add((JsonElement) obj);
		});
		try (Writer writer = new FileWriter(f)) {
			writer.write((new GsonBuilder()).setPrettyPrinting().create().toJson((JsonElement) arr));
		} catch (IOException e) {
			f.delete();
		}
	}

	 public void loadConfig(String cfgname) {
		    File f = new File(this.dir, cfgname + ".json");
		    if (!f.exists())
		      return; 
		    try {
		      Reader reader = new FileReader(f);
		      JsonElement node = (new JsonParser()).parse(reader);
		      JsonArray arr = node.getAsJsonArray();
		      if (!arr.isJsonArray())
		        return; 
		      arr.forEach(element -> {
		            JsonObject subobj = element.getAsJsonObject();
		            String name = subobj.get("name").getAsString();
		            Module m = Minecraft.getMinecraft().getNobita().getModManager().getModByName(name);
		            if (m != null)
		              m.load(subobj); 
		          });
		    } catch (FileNotFoundException fileNotFoundException) {}
		  }

	public Config getPresetByName(String name) {
		for (Config p : this.configs) {
			if (p.getName().equals(name))
				return p;
		}
		return null;
	}

	public Config getConfig(String name) {
		for (Config cfg : this.configs) {
			if (cfg.getName().equalsIgnoreCase(name))
				return cfg;
		}
		return null;
	}

	public boolean isConfig(String name) {
		return (getConfig(name) != null);
	}
}
