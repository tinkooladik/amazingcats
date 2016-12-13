package com.tinkooladik.crazycats.Actors;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tinkooladik.crazycats.Assets;

public class Plus extends Actor {

	private Texture toDraw;
	
    public Plus(char type) {
    	setSize(Gdx.graphics.getWidth()/9, Gdx.graphics.getWidth()/9);
		float y = Gdx.graphics.getHeight() - this.getHeight();
		float x = 0;
		switch (type) {
			case 's':
				x = Gdx.graphics.getWidth()/2 + this.getWidth() * 0.5f;
				toDraw = Assets.plus5;
				break;
			case 'l':
				x = 5 + (Gdx.graphics.getWidth() / 15) * 3;
				toDraw = Assets.plusLife; // change to plus 1
				break;
		}

		setPosition(x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor());
        batch.draw(toDraw, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        if(this.getY() < Gdx.graphics.getHeight()) this.setY(this.getY() + 1);
        else this.remove();
    }
}
