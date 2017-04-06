package com.tinkooladik.crazycats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {

  public static String txrPlus5, txrPlusLife, txrDialogBack, txrDialogBg, txrDialogBtn,
      txrDialogBtnPassive, txrTranspBtn, txrFish, txrLife, txrLostLife, txrPause, txrPauseBack,
      txrPauseBg, txrHelp, txrTouch, txrMusicOff, txrAudio, txrSettings, txrLeaderboard,
      txrAchievements, txrRate, txrReplay, txrMenu, txrProgressBar, txrKnob, txrGameOver, menuTxt1,
      menuTxt2, menuTxt3, txrBonusImg, txrBonusBg, txrGameBg, txrRewarded;
  public static Music music;
  public static Sound sound;

  public static TextureAtlas atlas;

  static void load() {

    Gdx.app.log("myLog", "loading started");

    AcidCat.manager.load("data/for_pause.pack", TextureAtlas.class);
    AcidCat.manager.finishLoading();
    atlas = AcidCat.manager.get("data/for_pause.pack", TextureAtlas.class);

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

    txrRewarded = "data/rewarded.png";
    AcidCat.manager.load(txrRewarded, Texture.class);

    // settings items
    txrMusicOff = "data/music_off.png";
    AcidCat.manager.load(txrMusicOff, Texture.class);

    txrAudio = "data/audio.png";
    AcidCat.manager.load(txrAudio, Texture.class);

    txrSettings = "data/settings.png";
    AcidCat.manager.load(txrSettings, Texture.class);

    txrLeaderboard = "data/leaderboard.png";
    AcidCat.manager.load(txrLeaderboard, Texture.class);

    txrAchievements = "data/achievements.png";
    AcidCat.manager.load(txrAchievements, Texture.class);

    txrRate = "data/rate.png";
    AcidCat.manager.load(txrRate, Texture.class);

    txrReplay = "data/replay.png";
    AcidCat.manager.load(txrReplay, Texture.class);

    txrMenu = "data/menu.png";
    AcidCat.manager.load(txrMenu, Texture.class);

    txrProgressBar = "data/progress_bar.png";
    AcidCat.manager.load(txrProgressBar, Texture.class);

    txrKnob = "data/knob.png";
    AcidCat.manager.load(txrKnob, Texture.class);

    for (int i = 1; i <= 8; i++) {
      AcidCat.manager.load("data/" + i + ".jpg", Texture.class);
    }

    txrGameBg = "data/background.jpg";
    AcidCat.manager.load(txrGameBg, Texture.class);

    // menu screen
    txrGameOver = "data/game_over_bg.jpg";
    AcidCat.manager.load(txrGameOver, Texture.class);

    menuTxt1 = "data/menu_txt_1.png";
    AcidCat.manager.load(menuTxt1, Texture.class);

    menuTxt2 = "data/menu_txt_2.png";
    AcidCat.manager.load(menuTxt2, Texture.class);

    menuTxt3 = "data/menu_txt_3.png";
    AcidCat.manager.load(menuTxt3, Texture.class);

    txrBonusImg = "data/bonus_img.png";
    AcidCat.manager.load(txrBonusImg, Texture.class);

    txrBonusBg = "data/bonus_bg.png";
    AcidCat.manager.load(txrBonusBg, Texture.class);

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
    for (int i = 1; i <= 13; i++) {
      AcidCat.manager.load("data/alive/cat" + i + ".png", Texture.class);
    }
    // cats1
    for (int i = 1; i <= 13; i++) {
      AcidCat.manager.load("data/cats1/cat" + i + ".png", Texture.class);
    }

    AcidCat.manager.finishLoading();
  }

  public static void loadMusic() {
    // music
    music = Gdx.audio.newMusic(Gdx.files.internal("data/music.mp3"));
    music.setLooping(true);
    music.setVolume(1.0f);
    if (Settings.musicEnabled) music.play();

    sound = Gdx.audio.newSound(Gdx.files.internal("data/sound.mp3"));
  }

  public static TextureRegion getCat(int i) {
    TextureRegion cats = null;
    switch (Settings.catsPack) {
      case 0:
        cats = new TextureRegion(AcidCat.manager.get("data/cats1/cat" + i + ".png", Texture.class));
        break;
      case 1:
        cats = new TextureRegion(AcidCat.manager.get("data/alive/cat" + i + ".png", Texture.class));
        break;
      default:
        cats = new TextureRegion(AcidCat.manager.get("data/cats1/cat" + i + ".png", Texture.class));
        break;
    }
    return cats;
  }
}
