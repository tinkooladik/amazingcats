package com.tinkooladik.crazycats.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tinkooladik.crazycats.AcidCat;
import com.tinkooladik.crazycats.Assets;

/**
 * Created by User on 03.02.2017.
 */

public class RewardedButton extends Actor {

  private AcidCat game;
  private float size, maxSize, minSize;
  private boolean increase = true;

  public RewardedButton(AcidCat game, float dialogTop) {
    this.game = game;
    size = Gdx.graphics.getWidth()/5;
    setSize(size, size);
    float x = Gdx.graphics.getWidth() - size * 1.2f;
    float y = dialogTop - size;
    setPosition(x, y);
    addListener(new EnterBtnListener());
    maxSize = size * 1.05f;
    minSize = size * 0.95f;
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    batch.setColor(getColor());
    batch.draw(AcidCat.manager.get(Assets.txrRewarded, Texture.class), getX(), getY(), getWidth(), getHeight());
  }

  @Override
  public void act(float delta) {
    if(increase) {
      if(size < maxSize) {
        size++;
        setSize(size, size);
      }
      else increase = false;
    }
    else {
      if(size > minSize) {
        size--;
        setSize(size, size);
      }
      else increase = true;
    }
  }

  private class EnterBtnListener extends ClickListener {
    @Override
    public void clicked(InputEvent event, float x, float y) {
      AcidCat.rewardedAfter = 10;
      AcidCat.googleServices.showRewarded();
    }
  }

}
