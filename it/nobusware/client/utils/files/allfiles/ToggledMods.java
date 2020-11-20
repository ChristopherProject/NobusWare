package it.nobusware.client.utils.files.allfiles;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.core.net.Protocol;

import it.nobusware.client.NobusWare;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.files.FileUtils;
import net.minecraft.client.Minecraft;

public class ToggledMods {
  public static void bindKeys() {
    List<String> file = FileUtils.readFile(NobusWare.nobitaDir + "\\toggledmods.txt");
    for (String s : file) {
      String name = s.split(":")[0];
      Module mod = Module.getModbyAlias(name);
      if (mod != null) {
        boolean toggle = Boolean.valueOf(s.split(":")[1]).booleanValue();
        if (toggle && !mod.isAbilitato()) {
          mod.setAbilitato(true);
          mod.Abilitato();
        } 
      } 
    } 
  }
  
  public static void setupBinds() {
    List<String> file = FileUtils.readFile(NobusWare.nobitaDir + "\\toggledmods.txt");
    List<String> newfile = new ArrayList<>();
    for (Module m : Minecraft.getMinecraft().getNobita().getModManager().getHackDelClient()) {
      boolean exists = false;
      for (String s : file) {
        String modname = s.split(":")[0];
        if (modname.equalsIgnoreCase(m.getNome_mod())) {
          exists = true;
          newfile.add(s);
        } 
      } 
      if (!exists) {
        if (m.getNome_mod().equalsIgnoreCase("blink"))
          return; 
        newfile.add(String.valueOf(m.getNome_mod()) + ":" + m.isAbilitato());
      } 
    } 
    FileUtils.writeFile("toggledmods", newfile);
  }
  
  public static void resetBinds() {
    List<String> file = FileUtils.readFile("toggledmods");
    List<String> newfile = new ArrayList<>();
    for (Module m : Minecraft.getMinecraft().getNobita().getModManager().getHackDelClient()) 
      newfile.add(String.valueOf(m.getNome_mod()) + ":" + m.isAbilitato()); 
    FileUtils.writeFile("toggledmods", newfile);
  }
  
  public static void bindKey(Module mod, boolean toggle) {
    List<String> file = FileUtils.readFile(NobusWare.nobitaDir + "\\toggledmods.txt");
    List<String> newfile = new ArrayList<>();
    boolean exists = false;
    for (String s : file) {
      String modname = s.split(":")[0];
      if (modname.equalsIgnoreCase(mod.getNome_mod())) {
        exists = true;
        s = String.valueOf(mod.getNome_mod()) + ":" + toggle;
      } 
      newfile.add(s);
    } 
    if (!exists)
      newfile.add(String.valueOf(mod.getNome_mod()) + ":" + toggle); 
    FileUtils.writeFile("toggledmods", newfile);
    bindKeys();
  }
}
