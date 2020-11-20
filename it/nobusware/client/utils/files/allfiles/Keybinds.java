package it.nobusware.client.utils.files.allfiles;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.core.net.Protocol;
import org.lwjgl.input.Keyboard;

import it.nobusware.client.NobusWare;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.files.FileUtils;
import net.minecraft.client.Minecraft;

public class Keybinds {
  public static void bindKeys() {
    List<String> file = FileUtils.readFile(NobusWare.nobitaDir + "\\keybinds.txt");
    for (String s : file) {
      String name = s.split(":")[0];
      Module mod = Module.getModbyAlias(name);
      if (mod != null) {
        Integer bind = Integer.valueOf(Keyboard.getKeyIndex(s.split(":")[1]));
        mod.setTasto(bind.intValue());
      } 
    } 
  }
  
  public static void setupBinds() {
    List<String> file = FileUtils.readFile(NobusWare.nobitaDir + "\\keybinds.txt");
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
        if (m.getNome_mod().equalsIgnoreCase("clickgui"))
          return; 
        newfile.add(String.valueOf(m.getNome_mod()) + ":" + Keyboard.getKeyName(m.getTasto()));
      } 
    } 
    FileUtils.writeFile("keybinds", newfile);
  }
  
  public static void resetBinds() {
    List<String> file = FileUtils.readFile(NobusWare.nobitaDir + "\\keybinds.txt");
    List<String> newfile = new ArrayList<>();
    for (Module m : Minecraft.getMinecraft().getNobita().getModManager().getHackDelClient()) 
      newfile.add(String.valueOf(m.getNome_mod()) + ":" + Keyboard.getKeyName(m.getTasto())); 
    FileUtils.writeFile(NobusWare.nobitaDir + "\\keybinds", newfile);
  }
  
  public static void bindKey(Module mod, int key) {
    List<String> file = FileUtils.readFile(NobusWare.nobitaDir + "\\keybinds.txt");
    List<String> newfile = new ArrayList<>();
    boolean exists = false;
    for (String s : file) {
      String modname = s.split(":")[0];
      if (modname.equalsIgnoreCase(mod.getNome_mod())) {
        exists = true;
        s = String.valueOf(mod.getNome_mod()) + ":" + Keyboard.getKeyName(key);
      } 
      newfile.add(s);
    } 
    if (!exists)
      newfile.add(String.valueOf(mod.getNome_mod()) + ":" + Keyboard.getKeyName(key)); 
    FileUtils.writeFile(NobusWare.nobitaDir + "\\keybinds", newfile);
    bindKeys();
  }
}
