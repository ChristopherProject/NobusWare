package it.nobusware.client.utils.fontmanager;

import java.awt.Font;
import java.io.InputStream;
import java.util.HashMap;
import net.minecraft.client.Minecraft;

public class FontManager {
  private HashMap<String, HashMap<Float, UnicodeFontRenderer>> fonts = new HashMap<>();
  
  public UnicodeFontRenderer lemonMilkBold60 = getFont("LemonMilkbold", 60.0F, true);
  
  public UnicodeFontRenderer lemonMilkBold75 = getFont("LemonMilkbold", 75.0F, true);
  
  public UnicodeFontRenderer lemonMilkBold80 = getFont("LemonMilkbold", 80.0F, true);
  
  public UnicodeFontRenderer lemonMilkBold90 = getFont("LemonMilkbold", 90.0F, true);
  
  public UnicodeFontRenderer lemonMilkBold100 = getFont("LemonMilkbold", 100.0F, true);
  
  public UnicodeFontRenderer lemonMilkBold110 = getFont("LemonMilkbold", 110.0F, true);
  
  public UnicodeFontRenderer lemonMilkBold120 = getFont("LemonMilkbold", 120.0F, true);
  
  public UnicodeFontRenderer lemonMilk60 = getFont("LemonMilk", 60.0F, true);
  
  public UnicodeFontRenderer lemonMilk75 = getFont("LemonMilk", 75.0F, true);
  
  public UnicodeFontRenderer lemonMilk80 = getFont("LemonMilk", 80.0F, true);
  
  public UnicodeFontRenderer lemonMilk90 = getFont("LemonMilk", 90.0F, true);
  
  public UnicodeFontRenderer lemonMilk100 = getFont("LemonMilk", 100.0F, true);
  
  public UnicodeFontRenderer lemonMilk110 = getFont("LemonMilk", 110.0F, true);
  
  public UnicodeFontRenderer lemonMilk120 = getFont("LemonMilk", 120.0F, true);
  
  public UnicodeFontRenderer lemonMilklight60 = getFont("LemonMilklight", 60.0F, true);
  
  public UnicodeFontRenderer lemonMilklight75 = getFont("LemonMilklight", 75.0F, true);
  
  public UnicodeFontRenderer lemonMilkLight80 = getFont("LemonMilklight", 80.0F, true);
  
  public UnicodeFontRenderer lemonMilkLight90 = getFont("LemonMilklight", 90.0F, true);
  
  public UnicodeFontRenderer lemonMilkLight100 = getFont("LemonMilklight", 100.0F, true);
  
  public UnicodeFontRenderer lemonMilkLight110 = getFont("LemonMilklight", 110.0F, true);
  
  public UnicodeFontRenderer lemonMilkLight120 = getFont("LemonMilklight", 120.0F, true);
  
  public UnicodeFontRenderer comfortaa10 = getFont("comfortaa", 10.0F);
  
  public UnicodeFontRenderer comfortaa11 = getFont("comfortaa", 11.0F);
  
  public UnicodeFontRenderer comfortaa12 = getFont("comfortaa", 12.0F);
  
  public UnicodeFontRenderer comfortaa13 = getFont("comfortaa", 13.0F);
  
  public UnicodeFontRenderer comfortaa15 = getFont("comfortaa", 15.0F);
  
  public UnicodeFontRenderer comfortaa16 = getFont("comfortaa", 16.0F);
  
  public UnicodeFontRenderer comfortaa17 = getFont("comfortaa", 17.0F);
  
  public UnicodeFontRenderer comfortaa18 = getFont("comfortaa", 18.0F);
  
  public UnicodeFontRenderer comfortaa20 = getFont("comfortaa", 20.0F);
  
  public UnicodeFontRenderer comfortaa25 = getFont("comfortaa", 25.0F);
  
  public UnicodeFontRenderer comfortaa30 = getFont("comfortaa", 30.0F);
  
  public UnicodeFontRenderer comfortaa35 = getFont("comfortaa", 35.0F);
  
  public UnicodeFontRenderer comfortaa40 = getFont("comfortaa", 40.0F);
  
  public UnicodeFontRenderer comfortaa45 = getFont("comfortaa", 45.0F);
  
  public UnicodeFontRenderer comfortaa50 = getFont("comfortaa", 50.0F);
  
  public UnicodeFontRenderer comfortaa70 = getFont("comfortaa", 70.0F);
  
  public UnicodeFontRenderer simpleton10 = getFont("simpleton", 10.0F, true);
  
  public UnicodeFontRenderer simpleton11 = getFont("simpleton", 11.0F, true);
  
  public UnicodeFontRenderer simpleton12 = getFont("simpleton", 12.0F, true);
  
  public UnicodeFontRenderer simpleton13 = getFont("simpleton", 13.0F, true);
  
  public UnicodeFontRenderer simpleton15 = getFont("simpleton", 15.0F, true);
  
  public UnicodeFontRenderer simpleton16 = getFont("simpleton", 16.0F, true);
  
  public UnicodeFontRenderer simpleton17 = getFont("simpleton", 17.0F, true);
  
  public UnicodeFontRenderer simpleton18 = getFont("simpleton", 18.0F, true);
  
  public UnicodeFontRenderer simpleton20 = getFont("simpleton", 20.0F, true);
  
  public UnicodeFontRenderer simpleton25 = getFont("simpleton", 25.0F, true);
  
  public UnicodeFontRenderer simpleton30 = getFont("simpleton", 30.0F, true);
  
  public UnicodeFontRenderer simpleton35 = getFont("simpleton", 35.0F, true);
  
  public UnicodeFontRenderer simpleton40 = getFont("simpleton", 40.0F, true);
  
  public UnicodeFontRenderer simpleton45 = getFont("simpleton", 45.0F, true);
  
  public UnicodeFontRenderer simpleton50 = getFont("simpleton", 50.0F, true);
  
  public UnicodeFontRenderer simpleton70 = getFont("simpleton", 70.0F, true);
  
  public UnicodeFontRenderer payback10 = getFont("payback", 10.0F);
  
  public UnicodeFontRenderer payback11 = getFont("payback", 11.0F);
  
  public UnicodeFontRenderer payback12 = getFont("payback", 12.0F);
  
  public UnicodeFontRenderer payback13 = getFont("payback", 13.0F);
  
  public UnicodeFontRenderer payback15 = getFont("payback", 15.0F);
  
  public UnicodeFontRenderer payback16 = getFont("payback", 16.0F);
  
  public UnicodeFontRenderer payback17 = getFont("payback", 17.0F);
  
  public UnicodeFontRenderer payback18 = getFont("payback", 18.0F);
  
  public UnicodeFontRenderer payback20 = getFont("payback", 20.0F);
  
  public UnicodeFontRenderer payback25 = getFont("payback", 25.0F);
  
  public UnicodeFontRenderer payback30 = getFont("payback", 30.0F);
  
  public UnicodeFontRenderer payback35 = getFont("payback", 35.0F);
  
  public UnicodeFontRenderer payback40 = getFont("payback", 40.0F);
  
  public UnicodeFontRenderer payback45 = getFont("payback", 45.0F);
  
  public UnicodeFontRenderer payback50 = getFont("payback", 50.0F);
  
  public UnicodeFontRenderer payback70 = getFont("payback", 70.0F);
  
  public UnicodeFontRenderer VERDANA10 = getFont("VERDANA", 10.0F);
  
  public UnicodeFontRenderer VERDANA2 = getFont("VERDANA", 2.0F);
  
  public UnicodeFontRenderer VERDANA11 = getFont("VERDANA", 11.0F);
  
  public UnicodeFontRenderer VERDANA12 = getFont("VERDANA", 12.0F);
  
  public UnicodeFontRenderer VERDANA13 = getFont("VERDANA", 13.0F);
  
  public UnicodeFontRenderer VERDANA15 = getFont("VERDANA", 15.0F);
  
  public UnicodeFontRenderer VERDANA16 = getFont("VERDANA", 16.0F);
  
  public UnicodeFontRenderer VERDANA17 = getFont("VERDANA", 17.0F);
  
  public UnicodeFontRenderer VERDANA18 = getFont("VERDANA", 18.0F);
  
  public UnicodeFontRenderer VERDANA20 = getFont("VERDANA", 20.0F);
  
  public UnicodeFontRenderer VERDANA25 = getFont("VERDANA", 25.0F);
  
  public UnicodeFontRenderer VERDANA30 = getFont("VERDANA", 30.0F);
  
  public UnicodeFontRenderer VERDANA35 = getFont("VERDANA", 35.0F);
  
  public UnicodeFontRenderer VERDANA40 = getFont("VERDANA", 40.0F);
  
  public UnicodeFontRenderer VERDANA45 = getFont("VERDANA", 45.0F);
  
  public UnicodeFontRenderer VERDANA50 = getFont("VERDANA", 50.0F);
  
  public UnicodeFontRenderer arialBold10 = getFont("arialBold", 10.0F);
  
  public UnicodeFontRenderer arialBold11 = getFont("arialBold", 11.0F);
  
  public UnicodeFontRenderer arialBold12 = getFont("arialBold", 12.0F);
  
  public UnicodeFontRenderer arialBold13 = getFont("arialBold", 13.0F);
  
  public UnicodeFontRenderer arialBold15 = getFont("arialBold", 15.0F);
  
  public UnicodeFontRenderer arialBold16 = getFont("arialBold", 16.0F);
  
  public UnicodeFontRenderer arialBold17 = getFont("arialBold", 17.0F);
  
  public UnicodeFontRenderer arialBold18 = getFont("arialBold", 18.0F);
  
  public UnicodeFontRenderer arialBold20 = getFont("arialBold", 20.0F);
  
  public UnicodeFontRenderer arialBold25 = getFont("arialBold", 25.0F);
  
  public UnicodeFontRenderer arialBold30 = getFont("arialBold", 30.0F);
  
  public UnicodeFontRenderer arialBold35 = getFont("arialBold", 35.0F);
  
  public UnicodeFontRenderer arialBold40 = getFont("arialBold", 40.0F);
  
  public UnicodeFontRenderer arialBold45 = getFont("arialBold", 45.0F);
  
  public UnicodeFontRenderer arialBold50 = getFont("arialBold", 50.0F);
  
  public UnicodeFontRenderer robotobold10 = getFont("robotobold", 10.0F);
  
  public UnicodeFontRenderer robotobold11 = getFont("robotobold", 11.0F);
  
  public UnicodeFontRenderer robotobold12 = getFont("robotobold", 12.0F);
  
  public UnicodeFontRenderer robotobold13 = getFont("robotobold", 13.0F);
  
  public UnicodeFontRenderer robotobold15 = getFont("robotobold", 15.0F);
  
  public UnicodeFontRenderer robotobold16 = getFont("robotobold", 16.0F);
  
  public UnicodeFontRenderer robotobold17 = getFont("robotobold", 17.0F);
  
  public UnicodeFontRenderer robotobold18 = getFont("robotobold", 18.0F);
  
  public UnicodeFontRenderer robotobold20 = getFont("robotobold", 20.0F);
  
  public UnicodeFontRenderer robotobold25 = getFont("robotobold", 25.0F);
  
  public UnicodeFontRenderer robotobold30 = getFont("robotobold", 30.0F);
  
  public UnicodeFontRenderer robotobold35 = getFont("robotobold", 35.0F);
  
  public UnicodeFontRenderer robotobold40 = getFont("robotobold", 40.0F);
  
  public UnicodeFontRenderer robotobold45 = getFont("robotobold", 45.0F);
  
  public UnicodeFontRenderer robotobold50 = getFont("robotobold", 50.0F);
  
  public UnicodeFontRenderer getFont(String name, float size) {
    UnicodeFontRenderer unicodeFont = null;
    try {
      if (this.fonts.containsKey(name) && (
        (HashMap)this.fonts.get(name)).containsKey(Float.valueOf(size)))
        return (UnicodeFontRenderer)((HashMap)this.fonts.get(name)).get(Float.valueOf(size)); 
      InputStream inputStream = getClass().getResourceAsStream("fonts/" + name + ".ttf");
      Font font = null;
      font = Font.createFont(0, inputStream);
      unicodeFont = new UnicodeFontRenderer(font.deriveFont(size));
      unicodeFont.setUnicodeFlag(true);
      unicodeFont.setBidiFlag((Minecraft.getMinecraft()).mcLanguageManager.isCurrentLanguageBidirectional());
      HashMap<Float, UnicodeFontRenderer> map = new HashMap<>();
      if (this.fonts.containsKey(name))
        map.putAll(this.fonts.get(name)); 
      map.put(Float.valueOf(size), unicodeFont);
      this.fonts.put(name, map);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return unicodeFont;
  }
  
  public UnicodeFontRenderer getFont(String name, float size, boolean b) {
    UnicodeFontRenderer unicodeFont = null;
    try {
      if (this.fonts.containsKey(name) && (
        (HashMap)this.fonts.get(name)).containsKey(Float.valueOf(size)))
        return (UnicodeFontRenderer)((HashMap)this.fonts.get(name)).get(Float.valueOf(size)); 
      InputStream inputStream = getClass().getResourceAsStream("fonts/" + name + ".otf");
      Font font = null;
      font = Font.createFont(0, inputStream);
      unicodeFont = new UnicodeFontRenderer(font.deriveFont(size));
      unicodeFont.setUnicodeFlag(true);
      unicodeFont.setBidiFlag((Minecraft.getMinecraft()).mcLanguageManager.isCurrentLanguageBidirectional());
      HashMap<Float, UnicodeFontRenderer> map = new HashMap<>();
      if (this.fonts.containsKey(name))
        map.putAll(this.fonts.get(name)); 
      map.put(Float.valueOf(size), unicodeFont);
      this.fonts.put(name, map);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return unicodeFont;
  }
}
