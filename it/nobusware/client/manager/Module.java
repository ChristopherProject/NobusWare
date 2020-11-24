package it.nobusware.client.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import QuarantineAPI.EventAPI;
import it.nobusware.client.utils.value.Value;
import net.minecraft.client.Minecraft;

public class Module {

	protected static Minecraft mc;
	String nome_mod;
	int tasto;
	String nome_array_printed;
	Category categoria;
	boolean abilitato;
	private boolean dangerous;

	private List<it.nobusware.client.utils.value.Value> values = new ArrayList<>();

	static {
		mc = Minecraft.getMinecraft();
	}

	public Module(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		this.nome_mod = nome_mod;
		this.tasto = tasto;
		this.nome_array_printed = nome_array_printed;
		this.categoria = categoria;
		this.dangerous = false;
		System.out.println("Loaded -> " + this.nome_mod);
	}

	protected void addValues(it.nobusware.client.utils.value.Value... value) {
		this.values.addAll(Arrays.asList(value));
	}

	public it.nobusware.client.utils.value.Value find(String term) {
		for (it.nobusware.client.utils.value.Value value : this.values) {
			if (value.getLabel().equalsIgnoreCase(term))
				return value;
		}
		return null;
	}

	public boolean isDangerous() {
		return this.dangerous;
	}

	public void setDangerous(final boolean state) {
		this.dangerous = state;
	}

	public int getTasto() {
		return tasto;
	}

	public void setTasto(int tasto) {
		this.tasto = tasto;
	}

	public String getNome_mod() {
		return nome_mod;
	}

	public String getNome_array_printed() {
		return nome_array_printed;
	}

	public Category getCategoria() {
		return categoria;
	}

	public boolean isAbilitato() {
		return abilitato;
	}

	public void toggle() {
		this.abilitato = !this.abilitato;
		if (this.abilitato) {
			Abilitato();
			EventAPI.put(this);
		} else {
			Disabilitato();
			EventAPI.remove(this);
		}
	}

	public void Abilitato() {
		if (mc.theWorld != null) {
			EventAPI.put(this);
			System.out.println("[Client] " + this.getNome_mod() + " Abilitato.");
		}
	}

	public void Disabilitato() {
		EventAPI.remove(this);
		System.out.println("[Client] " + this.getNome_mod() + " Disabilitato");
	}

	public void setAbilitato(boolean isEnabled) {
		this.abilitato = isEnabled;
	}

	public enum Category {
		Combatti, Movimenti, Giocatore, Rendering, Exploits, Crashers, Funny,
	}

	public static Module getModbyAlias(String alias) {
		for (Module mod : mc.getNobita().getModManager().getHackDelClient()) {
			if (mod.getNome_mod().equalsIgnoreCase(alias))
				return mod;
		}
		return null;
	}
	
	  public void save(JsonObject directory, boolean key) {
		    if (key)
		      directory.addProperty("key", Integer.valueOf(getTasto())); 
		    directory.addProperty("enabled", Boolean.valueOf(isAbilitato()));
		    this.values.forEach(val -> directory.addProperty(val.getLabel(), val.getValue().toString()));
		  }
		  
		  public void load(JsonObject directory) {
		    directory.entrySet().forEach(data -> {
		          switch ((String)data.getKey()) {
		            case "name":
		              return;
		            case "key":
		              setTasto(((JsonElement)data.getValue()).getAsInt());
		              return;
		            case "enabled":
		              if ((!isAbilitato() || !((JsonElement)data.getValue()).getAsBoolean()) && (isAbilitato() || ((JsonElement)data.getValue()).getAsBoolean()))
		                setAbilitato(((JsonElement)data.getValue()).getAsBoolean()); 
		              return;
		          } 
		          Value val = find((String)data.getKey());
		          if (val != null)
		            val.setValue(((JsonElement)data.getValue()).getAsString()); 
		        });
		  }

	public List<it.nobusware.client.utils.value.Value> getValues() {
		return values;
	}

}