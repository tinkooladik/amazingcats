package com.tinkooladik.crazycats;

import com.badlogic.gdx.Game;
import com.tinkooladik.crazycats.Screens.MenuScreen;
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
	public  static int interstitialCount = 0;

    public AcidCat(IGoogleServices googleServices) {
    	super();
    	AcidCat.googleServices = googleServices;
    }
	
	@Override
	public void create () {
		catsAmnt = 5;
		score = 0; fishScore = 0; lives = 3;
		
		Settings.load();
		Assets.load();

		task = taskFactory.getNewTask();

	    //myRequestHandler.showAds(true);
	    setScreen(new MenuScreen(this));
	}

	@Override
	public void render () { 
		super.render();
	}
}
