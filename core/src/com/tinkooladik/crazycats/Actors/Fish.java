package com.tinkooladik.crazycats.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.tinkooladik.crazycats.AcidCat;
import com.tinkooladik.crazycats.Assets;
import com.tinkooladik.crazycats.Settings;

public class Fish extends Actor {
	
	private AcidCat game;
	
    public Fish(AcidCat game, float x, float y) {
    	this.game = game;
        setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getWidth()/5-40);
        setPosition(x, y);
        addListener(new EnterFishListener());
    }    

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor());
        batch.draw(AcidCat.manager.get(Assets.txrFish, Texture.class), getX(), getY(), getWidth(), getHeight());
    }
    

	private class EnterFishListener extends InputListener {
		@Override
		public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
    		if(pointer == 0) {
			remove();
			AcidCat.score+=5;
			AcidCat.fishScore++;
			Settings.scores[3]++;
			// REWRITE using events
			game.p5 = true;
    		}
		}
    }

}
