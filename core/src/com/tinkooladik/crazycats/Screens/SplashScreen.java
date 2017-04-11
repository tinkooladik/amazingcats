package com.tinkooladik.crazycats.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.async.AsyncResult;
import com.badlogic.gdx.utils.async.AsyncTask;
import com.tinkooladik.crazycats.AcidCat;

/**
 * Created by User on 31.01.2017.
 */

public class SplashScreen implements Screen {

  private SpriteBatch batch;
  private AcidCat game;
  private Texture texture;
  private long endTime = 0;

  AsyncExecutor asyncExecutor = new AsyncExecutor(10);
  AsyncResult<Void> asyncTask;

  public SplashScreen(AcidCat game) {
    this.game = game;
    batch = new SpriteBatch();
  }

  @Override public void show() {
    Gdx.app.log("myLog", "splash started");
    texture = new Texture("data/splash.png");
    Gdx.app.log("myLog", "Splash screen was started");
    game.loadAssets();
    //create our async task that runs our async method
    asyncTask = asyncExecutor.submit(new AsyncTask<Void>() {
      public Void call() {
        Gdx.app.log("myLog", "asyncTask");
        game.loadAssets();
        Gdx.app.log("myLog", "after load assets");
        return null;
      }
    });
  }

  @Override public void render(float delta) {
    // checks to see if the task is done.  If not draw the load screen
    if (!asyncTask.isDone()) {
      showLoading();
      return;
    } else {
      game.setScreen(new MenuScreen(game));
      Gdx.app.log("myLog", "set menu screen");
    }
  }

  public void showLoading() {
    batch.begin();
    batch.draw(texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    batch.end();
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
