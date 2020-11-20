package it.nobusware.client.render.override;

import java.awt.Color;
import java.util.List;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.realmsclient.gui.ChatFormatting;

import it.nobusware.client.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;

public class GuiScoreBoardOverlay extends GuiIngame  {
	

	public GuiScoreBoardOverlay(Minecraft mcIn) {
		super(mcIn);
		 this.x = offsetX;
		 this.y = offsetY;
	}
	
	  private int offsetX;
	  
	  private int offsetY;
	  
	  private int width;
	  
	  private int height;
	  	
	  
	  public static final ScaledResolution getScaledResolution() {
		    return new ScaledResolution(Minecraft.getMinecraft(), (Minecraft.getMinecraft()).displayWidth, (Minecraft.getMinecraft()).displayHeight);
		  }
	  
	  public int getX() {
	    ScaledResolution resolution = getScaledResolution();
	    return resolution.getScaledWidth() - this.offsetX - this.width;
	  }
	  
	  public int getY() {
	    return this.offsetY;
	  }
	  
	  public int getDragY() {
	    return 0;
	  }
	  
	  public int getDragX() {
	    return 0;
	  }
	  
	  public void setDragX() {}
	  
	  public void setDragY() {}
	  
	  public int getWidth() {
	    return this.width;
	  }
	  
	  public int getHeight() {
	    return this.height;
	  }
	  
	  public void setPosition(int x, int y) {
		  ScaledResolution resolution = getScaledResolution();
	    this.offsetX = resolution.getScaledWidth() - x;
	    this.offsetY = y;
	  }
	  
	  public boolean isDragging() {
	    return false;
	  }
	  
	  public void setDragging(boolean drag) {}
	  
	  public void render() {
	    Scoreboard scoreboard = Minecraft.getMinecraft().theWorld.getScoreboard();
	    ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(Minecraft.getMinecraft().thePlayer.getName());
	    ScoreObjective scoreObjective = null;
	    if (scoreplayerteam != null) {
	    	//scoreplayerteam.getChatFormat().getColorIndex();
	      int colorIndex = Integer.parseInt(scoreplayerteam.getColorPrefix());
	      if (colorIndex >= 0)
	        scoreObjective = scoreboard.getObjectiveInDisplaySlot(3 + colorIndex); 
	    } 
	    scoreObjective = (scoreObjective != null) ? scoreObjective : scoreboard.getObjectiveInDisplaySlot(1);
	    if (scoreObjective == null) {
	      this.width = 0;
	      this.height = 0;
	      return;
	    } 
	    renderScoreboard(scoreObjective);
	  }
	  
	  private void renderScoreboard(ScoreObjective objective) {
		  ScaledResolution resolution = getScaledResolution();
	    Scoreboard scoreboard = objective.getScoreboard();
	    Collection<Score> collection = scoreboard.getSortedScores(objective);
	    List<Score> list = Lists.newArrayList((Iterable)collection.stream().filter(score -> !score.getPlayerName().startsWith("#")).collect(Collectors.toList()));
	    collection = (list.size() > 15) ? Lists.newArrayList(Iterables.skip(list, collection.size() - 15)) : list;
	    int stringWidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth(objective.getDisplayName());
	    for (Score score : collection) {
	      ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(score.getPlayerName());
	      String line = ScorePlayerTeam.formatPlayerName((Team)scoreplayerteam, score.getPlayerName()) + ": " + ChatFormatting.RED + score.getScorePoints();
	      stringWidth = Math.max(stringWidth, Minecraft.getMinecraft().fontRendererObj.getStringWidth(line));
	    } 
	    int height = collection.size() * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
	    int right = resolution.getScaledWidth() - this.offsetX - 2;
	    int left = right - stringWidth - 3;
	    int width = right - left;
	    int top = this.offsetY;
	    int bottom = top + height;
	    this.width = width;
	    this.height = height;
	    List<Score> sorted = Lists.newArrayList(collection);
	    Collections.reverse(sorted);
	    String title = objective.getDisplayName();
	    RenderUtils.drawRectangle(left, top, right, (top + 15), new Color(0, 0, 0, 100));
	    RenderUtils.drawRectangle(right, top, (right + 2), (top + 15), new Color(0, 0, 0, 100));
	    Minecraft.getMinecraft().fontRendererObj.drawString(title, left + 5, top + 5, -1, false);
	  }
	}