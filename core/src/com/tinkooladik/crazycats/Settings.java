package com.tinkooladik.crazycats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Settings {
	public final static String file = ".acidcat";
	public static int catsPack = 1;
	public static int[] scores = new int[] {0, 0, 0, 0};
	public static boolean newMaxScore = false;
	public static boolean musicEnabled = true;
	public static boolean soundEnabled = true;
	public static boolean vibroEnabled = true;
	public static boolean aliveBought = false;
	public static boolean firstRun = true;
	public static String lastBonusDay = "15.01.1997";
	public static int taskNum = 0;
	public static int taskProgress = 0;
	public static int gamesPlayed = 0;
	
	static void load () {
		try {
			FileHandle filehandle = Gdx.files.local(file);
			
			String[] strings = filehandle.readString().split("\n");
			
			// UNCOMMENT when create new packs
			//catsPack = Integer.parseInt(strings[0]);
			
			for (int i = 0; i <= 3; i++) { // 3
				scores[i] = Integer.parseInt(strings[i+1]);
			}
			musicEnabled = Boolean.parseBoolean(strings[5]);
			soundEnabled = Boolean.parseBoolean(strings[6]);
			vibroEnabled = Boolean.parseBoolean(strings[7]);
			//aliveBought = Boolean.parseBoolean(strings[8]);
			lastBonusDay = strings[8];
			taskNum = Integer.parseInt(strings[9]);
			taskProgress = Integer.parseInt(strings[10]);
			gamesPlayed = Integer.parseInt(strings[11]);
			firstRun = Boolean.parseBoolean(strings[12]);
		} catch (Throwable e) {
			// :( It's ok we have defaults
		}
	}

	public static void save () {
		try {
			FileHandle filehandle = Gdx.files.local(file);
			

			filehandle.writeString(Integer.toString(catsPack)+"\n", false);
			for (int i = 0; i<=3; i++) {
				filehandle.writeString(Integer.toString(scores[i])+"\n", true); // score
			}
			filehandle.writeString(Boolean.toString(musicEnabled)+"\n", true);
			filehandle.writeString(Boolean.toString(soundEnabled)+"\n", true);
			filehandle.writeString(Boolean.toString(vibroEnabled)+"\n", true);
			//filehandle.writeString(Boolean.toString(aliveBought)+"\n", true);
			filehandle.writeString(lastBonusDay+"\n", true);
			filehandle.writeString(Integer.toString(taskNum)+"\n", true);
			filehandle.writeString(Integer.toString(taskProgress)+"\n", true);
			filehandle.writeString(Integer.toString(gamesPlayed)+"\n", true);
			filehandle.writeString(Boolean.toString(firstRun)+"\n", true);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public static void addScore (int score, int fishScore) {
		scores[0] = score;
		if (scores[1]< score) {
			scores[1] = score;
			newMaxScore = true;
	    	AcidCat.googleServices.submitScore(score);
		}
		else newMaxScore = false;
		scores[2] = fishScore;
	}
	// 0 - score
	// 1 - maxScore
	// 2 - fishScore
	// 3 - fishesAmount
}
