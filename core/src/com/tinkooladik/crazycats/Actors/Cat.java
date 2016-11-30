package com.tinkooladik.crazycats.Actors;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Cat extends Actor {
    private TextureRegion toDraw;
    Random rand;
    int pace;
    int steps, paceX, paceY, direction;

    public Cat(TextureRegion toDraw, int pace) {
        this.toDraw = toDraw;
        this.pace = pace;
   //     this.catCase = catCase;
        setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getWidth()/5);
        setPosition(100, 100);
        steps = pace;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor());
        batch.draw(toDraw, getX(), getY(), getWidth(), getHeight());
    }
    
    @Override
    public void act(float delta) {
        rand = new Random();
        if (pace == steps) {
        	paceX = rand.nextInt(4); paceY = rand.nextInt(6);
            if (rand.nextBoolean()) paceX = -paceX; 
            if (rand.nextBoolean()) paceY = -paceY; 
            if (getX()+paceX*pace+getWidth() > Gdx.graphics.getWidth() || getX()+paceX*pace < 0) { paceX = -paceX; }
            if (getY()+paceY*pace+getHeight() > Gdx.graphics.getHeight() || getY()+paceY*pace < 0) { paceY = -paceY; }
        	moveBy(paceX, paceY);       	
        	steps--;
        }
        if (steps < pace && steps > 0) {
        	moveBy(paceX, paceY); 
        	steps--;
        }
        if (steps == 0) {
        	steps = pace;
        }
    }
}