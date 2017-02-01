package com.tinkooladik.crazycats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {

  public static String txrPlus5, txrPlusLife, txrDialogBack, txrDialogBg, txrDialogBtn, txrDialogBtnPassive,
                txrTranspBtn, txrFish, txrLife, txrLostLife, txrPause, txrPauseBack, txrPauseBg,
                txrHelp, txrTouch, txrMusicOff, txrAudio, txrSettings, txrLeaderboard, txrReplay,
                txrMenu, txrProgressBar, txrKnob;
  public static Music music;
  public static Sound sound;

  public static TextureAtlas atlas;
/*
    private static TextureAtlas catsAtlas;
    public static TextureAtlas items;
    public static TextureRegion dialog_back, dialog_bg, dialog_btn, dialog_btnPassive, transp_btn;
    public static TextureRegion fish, life, lostLife, pause, pause_back, pauseBg, help, touch;
    public static TextureRegion musicOff, audio, settings, leaderboard;
    public static TextureRegion replay, menu;
    public static TextureRegion progressBar, knob;
    public static Texture plus5, plusLife;*/
   // public static Music musicPlay;
   // public static Sound soundPlay;

  static void load() {

    Gdx.app.log("myLog", "loading started");

    AcidCat.manager.load("data/items.pack", TextureAtlas.class);
    AcidCat.manager.finishLoading();
    atlas = AcidCat.manager.get("data/items.pack", TextureAtlas.class);

    // game items
    txrPlus5 = "data/p5.png";
    AcidCat.manager.load(txrPlus5, Texture.class);

    txrPlusLife = "data/p_life.png";
    AcidCat.manager.load(txrPlusLife, Texture.class);

    txrFish = "data/fish.png";
    AcidCat.manager.load(txrFish, Texture.class);

    txrLife = "data/life.png";
    AcidCat.manager.load(txrLife, Texture.class);

    txrLostLife = "data/lost_life.png";
    AcidCat.manager.load(txrLostLife, Texture.class);

    txrPause = "data/pause.png";
    AcidCat.manager.load(txrPause, Texture.class);

    txrPauseBack = "data/pause_back.png";
    AcidCat.manager.load(txrPauseBack, Texture.class);

    txrPauseBg = "data/pause_bg.png";
    AcidCat.manager.load(txrPauseBg, Texture.class);

    txrHelp = "data/help.png";
    AcidCat.manager.load(txrHelp, Texture.class);

    txrTouch = "data/touch.png";
    AcidCat.manager.load(txrTouch, Texture.class);

    // settings items
    txrMusicOff = "data/music_off.png";
    AcidCat.manager.load(txrMusicOff, Texture.class);

    txrAudio = "data/audio.png";
    AcidCat.manager.load(txrAudio, Texture.class);

    txrSettings = "data/settings.png";
    AcidCat.manager.load(txrSettings, Texture.class);

    txrLeaderboard = "data/leaderboard.png";
    AcidCat.manager.load(txrLeaderboard, Texture.class);

    txrReplay = "data/replay.png";
    AcidCat.manager.load(txrReplay, Texture.class);

    txrMenu = "data/menu.png";
    AcidCat.manager.load(txrMenu, Texture.class);

    txrProgressBar = "data/progress_bar.png";
    AcidCat.manager.load(txrProgressBar, Texture.class);

    txrKnob = "data/knob.png";
    AcidCat.manager.load(txrKnob, Texture.class);


    // dialog textures
    txrDialogBack = "data/dialog_back.png";
    AcidCat.manager.load(txrDialogBack, Texture.class);

    txrDialogBg = "data/dialog_bg.png";
    AcidCat.manager.load(txrDialogBg, Texture.class);

    txrDialogBtn = "data/dialog_btn.png";
    AcidCat.manager.load(txrDialogBtn, Texture.class);

    txrDialogBtnPassive = "data/dialog_btn_passive.png";
    AcidCat.manager.load(txrDialogBtnPassive, Texture.class);

    txrTranspBtn = "data/transp_btn.png";
    AcidCat.manager.load(txrTranspBtn, Texture.class);

    // alive cats
    for(int i = 1; i <= 13; i++) {
      AcidCat.manager.load("data/alive/cat" + i + ".png", Texture.class);
    }


    // music
    music = Gdx.audio.newMusic(Gdx.files.internal("data/music.mp3"));
    music.setLooping(true);
    music.setVolume(1.0f);
    if (Settings.musicEnabled) music.play();

    sound = Gdx.audio.newSound(Gdx.files.internal("data/sound.mp3"));

    AcidCat.manager.finishLoading();

  }

  public static TextureRegion getCat(int i) {
    return new TextureRegion(AcidCat.manager.get("data/alive/cat" + i + ".png", Texture.class));
  }

/*
    static void initialize() {

        Gdx.app.log("myLogs", "start of manager loading");

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
        //plus5 = new Texture("data/p5.png");
        //plus5 = AcidCat.manager.get("data/p5.png", Texture.class);
        //plusLife = new Texture("data/pLife.png");
        //plusLife = AcidCat.manager.get("data/pLife.png", Texture.class);

        pause = items.findRegion("pause");
        pause_back = items.findRegion("pause_back");
        pauseBg = items.findRegion("pauseBg");

        menu = items.findRegion("menu");
        replay = items.findRegion("replay");;

        settings = items.findRegion("settings");
        audio = items.findRegion("audio");
        musicOff = items.findRegion("off");
        leaderboard = items.findRegion("leaderboard");

        help = new TextureRegion(new Texture("data/help.png"));

        musicPlay = Gdx.audio.newMusic(Gdx.files.internal("data/music.mp3"));
        musicPlay.setLooping(true);
        musicPlay.setVolume(1.0f);
        if (Settings.musicEnabled) musicPlay.play();

        soundPlay = Gdx.audio.newSound(Gdx.files.internal("data/sound.mp3"));

        progressBar = new TextureRegion(new Texture("data/pb.png"));
        knob = new TextureRegion(new Texture("data/knob.png"));

        Gdx.app.log("myLogs", "end of manager loading");
    }
*/
    /*public static TextureRegion getCat(int i) {
        return catsAtlas.findRegion("cat" + i);
    }*/
/*
    public static void loadCatsAtlas() {
        /*
         * switch(Settings.catsPack) {
		case 0: 
	        catsAtlas = new TextureAtlas("data/cats.pack");
	        break;
		case 1:
	        catsAtlas = new TextureAtlas("data/cats_alive.pack");
	        break;
		}
        catsAtlas = new TextureAtlas("data/aliveCats.pack");
    }
*/
}
