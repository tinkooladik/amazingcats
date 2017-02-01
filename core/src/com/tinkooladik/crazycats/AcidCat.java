package com.tinkooladik.crazycats;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.tinkooladik.crazycats.Screens.SplashScreen;
import com.tinkooladik.crazycats.Tasks.Task;
import com.tinkooladik.crazycats.Tasks.TaskFactory;
import gpservices.IGoogleServices;

public class AcidCat extends Game {
  public static TaskFactory taskFactory = new TaskFactory();
  public static Task task;
  public int catsAmnt;
  public int lives;
  public static int score, fishScore;
  public static IGoogleServices googleServices;
  public boolean p5 = false;
  public static int interstitialCount = 0;
  public static AssetManager manager;

  public AcidCat(IGoogleServices googleServices) {
    super();
    AcidCat.googleServices = googleServices;
  }

  @Override public void create() {

    Gdx.app.log("myLog", "app started");

    manager = new AssetManager();


    catsAmnt = 5;
    score = 0;
    fishScore = 0;
    lives = 3;

    Settings.load();

    task = taskFactory.getNewTask();

    setScreen(new SplashScreen(this));

   // Settings.load();
   // Assets.load();



  //  setScreen(new MenuScreen(this));

  }

  public void loadAssets() {
    Gdx.app.log("myLog", "loading method in app");
    Assets.load();
  }

  @Override public void render() {
    super.render();
  }
}
