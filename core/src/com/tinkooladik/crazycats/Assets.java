package com.tinkooladik.crazycats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	
	private static TextureAtlas catsAtlas;
	public static TextureAtlas items;
	public static TextureRegion cat;
	public static TextureRegion dialog_back, dialog_bg, dialog_btn, dialog_btnPassive, transp_btn;
	public static TextureRegion fish, life, lostLife, pause, pause_back, pauseBg, help, touch;
	public static TextureRegion musicOff, audio, settings, leaderboard;
	public static TextureRegion replay, menu, newBestScore; 
	public static Texture plus5;
	public static Music musicPlay;
	public static Sound soundPlay;
	
	public static void load() {
		loadCatsAtlas();
        items = new TextureAtlas("data/items.pack");
        
        dialog_back = items.findRegion("dialog_back");
        dialog_bg = items.findRegion("dialog_bg");
        dialog_btn = items.findRegion("dialog_btn");
        dialog_btnPassive = items.findRegion("dialog_btnPassive");
        transp_btn = items.findRegion("transp_btn");
        
        fish = items.findRegion("fish");
        life = items.findRegion("life");
        lostLife = items.findRegion("lostLife");
        touch = items.findRegion("touch");
        plus5 = new Texture("data/p5.png");
        
        pause = items.findRegion("pause");
        pause_back = items.findRegion("pause_back");
        pauseBg = items.findRegion("pauseBg");
        
        menu = items.findRegion("menu");
        replay = items.findRegion("replay");
        newBestScore = items.findRegion("newBS");

        settings = items.findRegion("settings");
        audio = items.findRegion("audio");
        musicOff = items.findRegion("off");
        leaderboard = items.findRegion("leaderboard");
        
        help = new TextureRegion(new Texture("data/help.png"));
        
        musicPlay = Gdx.audio.newMusic(Gdx.files.internal("data/music.mp3"));
		musicPlay.setLooping(true);
		musicPlay.setVolume(1.0f);
		if(Settings.musicEnabled) musicPlay.play();
		
		soundPlay = Gdx.audio.newSound(Gdx.files.internal("data/sound.mp3"));
	}
	
	public static TextureRegion getCat(int i) {
        cat = catsAtlas.findRegion("cat"+i); 
		return cat;
	}
	
	public static void loadCatsAtlas() {
		/*
		 * switch(Settings.catsPack) {
		case 0: 
	        catsAtlas = new TextureAtlas("data/cats.pack");
	        break;
		case 1:
	        catsAtlas = new TextureAtlas("data/cats_alive.pack");
	        break;
		}*/
		catsAtlas = new TextureAtlas("data/aliveCats.pack");
	}

}
