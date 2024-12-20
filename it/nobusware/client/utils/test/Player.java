package it.nobusware.client.utils.test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.newdawn.slick.Music;

import it.nobusware.client.utils.ChatUtils;
import it.nobusware.client.utils.test.player.MP3Player;

public class Player {

	public static MP3Player player;
	// public static Song currentSong;
	public static boolean isPlaying = false;
	// public static List<Song> currentSongList = new CopyOnWriteArrayList<Song>();
	public static float vol;
	public static boolean paused;
	public static boolean playerStopped = true;
	public static boolean playerPlaying;

	public static void resume() {
		if (player != null) {
			setVolume(ass.volumeControl * 50);
			player.play();
			isPlaying = true;
			paused = false;
		}
	}

	public static void play(String url) {
		stop();

		URL u;
		try {
			u = new URL(url);
			player = new MP3Player(u);
		} catch (MalformedURLException e1) {
		}

		player.setRepeat(false);
		setVolume(ass.volumeControl * 50);
		new Thread() {
			@Override
			public void run() {
				try {
					player.play();
					isPlaying = true;
					paused = false;
				} catch (Exception e) {
				}
			}
		}.start();
	}

	public static void playFile(File f) {
		stop();
		player = new MP3Player(f);
		player.setRepeat(false);
		setVolume(ass.volumeControl * 50);
		new Thread() {
			@Override
			public void run() {
				try {
					player.play();
					isPlaying = true;
					paused = false;
				} catch (Exception e) {
				}
			}
		}.start();
	}

	public static void pause() {
		if (player != null) {
			player.pause();
			isPlaying = false;
			paused = true;
		}
	}

	/*
	 * public static void next() { if (player != null) { int curInd =
	 * currentSongList.indexOf(currentSong); if (curInd == currentSongList.size()-1)
	 * { stop(); return; } play(currentSongList, currentSongList.get(curInd+1)); } }
	 */

	/*
	 * public static void prev() { if (player != null) { int curInd =
	 * currentSongList.indexOf(currentSong); if (curInd == 0) { stop(); return; }
	 * play(currentSongList, currentSongList.get(curInd-1)); } }
	 */

	public static boolean isPlaying() {
		return playerPlaying;
	}

	public static boolean isStopped() {
		return playerStopped;
	}

	public static void stop() {
		if (player != null) {
			player.stop();
			player = null;
			// currentSong = null;
			isPlaying = false;
			paused = false;
		
		}
	}

	public static void setVolume(float volume) {
		// vol = volume;
		if (player != null) {
			player.setVolume((int) volume);
		}
	}

}
