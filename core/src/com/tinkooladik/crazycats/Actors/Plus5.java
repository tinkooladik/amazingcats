package com.tinkooladik.crazycats.Actors;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tinkooladik.crazycats.AcidCat;
import com.tinkooladik.crazycats.Assets;

public class Plus5 extends Actor {

	
	AcidCat game;
	
    public Plus5(AcidCat game) {
    	this.game = game;
    	setSize(Gdx.graphics.getWidth()/9, Gdx.graphics.getWidth()/9);
		float x = Gdx.graphics.getWidth()/2 + this.getWidth() * 0.5f;
		float y = Gdx.graphics.getHeight() - this.getHeight();
		setPosition(x, y);;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor());
        Texture toDraw = Assets.plus5;
        batch.draw(toDraw, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        if(this.getY() < Gdx.graphics.getHeight()) this.setY(this.getY() + 1);
        else this.remove();
    }
}
