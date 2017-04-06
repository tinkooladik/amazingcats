package com.tinkooladik.crazycats.Actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TextureActor extends Actor {
  private TextureRegion toDraw;

  public TextureActor(TextureRegion toDraw) {
    this.toDraw = toDraw;
    setPosition(100, 100);
    setSize(100, 100);
  }

  @Override public void draw(Batch batch, float parentAlpha) {
    batch.setColor(getColor());
    batch.draw(toDraw, getX(), getY(), getWidth(), getHeight());
  }
}