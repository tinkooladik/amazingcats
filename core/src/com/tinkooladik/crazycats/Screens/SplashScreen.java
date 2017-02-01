package com.tinkooladik.crazycats.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tinkooladik.crazycats.AcidCat;

/**
 * Created by User on 31.01.2017.
 */

public class SplashScreen implements Screen {

  private SpriteBatch batch;
  private AcidCat game;
  private Texture texture;

  public SplashScreen(AcidCat game) {
    this.game = game;
    batch = new SpriteBatch();
  }

  @Override public void show() {
    Gdx.app.log("myLog", "splash started");
    texture = new Texture("data/splash.png");
    Gdx.app.log("myLog", "Splash screen was started");
    game.loadAssets();
  }

  @Override public void render(float delta) {

    batch.begin();
    batch.draw(texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    batch.end();

    if (AcidCat.manager.update()) game.setScreen(new MenuScreen(game));


    Gdx.app.log("myLog", "Progress: " + String.valueOf(AcidCat.manager.getProgress()));
  }

  @Override public void resize(int width, int height) {

  }

  @Override public void pause() {

  }

  @Override public void resume() {

  }

  @Override public void hide() {

  }

  @Override public void dispose() {
    texture.dispose();
    batch.dispose();
  }
}
