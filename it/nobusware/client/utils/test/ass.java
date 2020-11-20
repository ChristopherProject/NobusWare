package it.nobusware.client.utils.test;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.youtube.Channel;
import com.youtube.WebUtils;
import com.youtube.search.Search;
import com.youtube.search.Item;

public class ass {

	public static float volumeControl = 1;
	
	public static Search currentSearch;
	
	private static String api_key = "AIzaSyBQJoDZKMI-4-Iy1AqHkBksTbpCvhp-IWw";
	
	private static int max = 1;
	
	public static int currentSongLength;
	
	public static void diocane(String repo) {
		WebUtils w = new WebUtils();
		makeSearch(repo);
		for(Item v : currentSearch.getItems()){
			System.out.println("https://www.youtube.com/watch?v=" + v.getId().getVideoId());
			System.out.println("Time " + currentSongLength);
			System.out.println("Try To Play This");
			w.playMusicLink(v.getId().getVideoId());	
		}
		System.out.println("end");
	}
	
	
	public static void makeSearch(String query){
		Gson gson = new Gson();
		Search request = gson.fromJson(WebUtils.visitSite("https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults="+ max + "&q=" + query.replace(" ", "%20") + "&type=video&key="+api_key), Search.class);
		currentSearch = request;
	}
}
