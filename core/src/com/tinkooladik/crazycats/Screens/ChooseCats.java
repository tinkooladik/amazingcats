package com.tinkooladik.crazycats.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.tinkooladik.crazycats.AcidCat;
import com.tinkooladik.crazycats.Actors.TextureActor;
import com.tinkooladik.crazycats.Assets;
import com.tinkooladik.crazycats.Settings;

public class ChooseCats extends ScreenAdapter {
  AcidCat game;
  private Stage stage;
  private Texture background;
  private SpriteBatch sBatch;
  private Label acidPrice, alivePrice, yourFishes, bought;
  private Rectangle acidCats; //, aliveCats;
  private TextureActor buyAlive, chooseAlive, cantBuy, play, home;
  private BitmapFont font;
  private float width, height;

  public ChooseCats(final AcidCat game) {
    this.game = game;
    width = Gdx.graphics.getWidth();
    height = Gdx.graphics.getHeight();
    stage = new Stage(new StretchViewport(width, height));

    background = new Texture("data/pause_bg.png");

    sBatch = new SpriteBatch();

    font = new BitmapFont(Gdx.files.internal("data/font.fnt"), false);
    font.setColor(Color.BLACK);
    float x = width / 720f;
    float y = height / 1280f;
    font.getData().setScale(x, y);

    Label.LabelStyle settStyle = new Label.LabelStyle();
    settStyle.font = font;
    settStyle.fontColor = Color.DARK_GRAY;

    acidPrice = new Label("free", settStyle);
    acidPrice.setPosition(width - width / 5 - 50 - acidPrice.getWidth(),
        height / 4 * 3 - 140 + (width / 5 - 40) / 2 + 20);
    stage.addActor(acidPrice);

    bought = new Label("bought", settStyle);
    bought.setPosition(width - width / 5 - 50 - bought.getWidth(), height / 4 * 3 - 360 + 60);

    chooseAlive = new TextureActor(
        new TextureRegion(AcidCat.manager.get(Assets.txrTranspBtn, Texture.class)));
    chooseAlive.setSize(width - 60, 200);
    chooseAlive.setPosition(30, height / 4 * 3 - 360);
    chooseAlive.addListener(new ClickListener() {
      @Override public void clicked(InputEvent event, float x, float y) {
        Settings.catsPack = 1;
        Settings.save();
        //////Assets.loadCatsAtlas();
        //////////////////GameScreen.catsCreated = false;
      }
    });

    stage.addActor(chooseAlive);

    alivePrice = new Label("250", settStyle);
    alivePrice.setPosition(width - width / 5 - 50 - alivePrice.getWidth(),
        height / 4 * 3 - 360 + 60);

    cantBuy = new TextureActor(
        new TextureRegion(AcidCat.manager.get(Assets.txrDialogBtnPassive, Texture.class)));
    cantBuy.setSize(width - 60, 200);
    cantBuy.setPosition(30, height / 4 * 3 - 360);

    buyAlive = new TextureActor(
        new TextureRegion(AcidCat.manager.get(Assets.txrTranspBtn, Texture.class)));
    buyAlive.setSize(width - 60, 200);
    buyAlive.setPosition(30, height / 4 * 3 - 360);
    buyAlive.addListener(new ClickListener() {
      @Override public void clicked(InputEvent event, float x, float y) {
        Settings.aliveBought = true;
        Settings.scores[3] -= 250;
        Settings.save();
        buyAlive.remove();
        alivePrice.remove();
      }
    });

    if (!Settings.aliveBought) {
      stage.addActor(alivePrice);
      stage.addActor(buyAlive);
    }

    yourFishes = new Label("You have 000 fishes", settStyle);
    yourFishes.setPosition((width - yourFishes.getWidth()) / 2,
        height - yourFishes.getHeight() * 3f);
    stage.addActor(yourFishes);

    //play = new TextureActor(Assets.settPlay);
    play.setPosition(width / 3 - play.getWidth() / 2, play.getHeight());
    play.addListener(new ClickListener() {
      @Override public void clicked(InputEvent event, float x, float y) {
        dispose();
        game.setScreen(new GameScreen(game));
      }
    });
    stage.addActor(play);

    //home = new TextureActor(Assets.home);
    home.setPosition(width / 3 * 2 - home.getWidth() / 2, home.getHeight());
    home.addListener(new ClickListener() {
      @Override public void clicked(InputEvent event, float x, float y) {
        dispose();
        game.setScreen(new MenuScreen(game));
      }
    });
    stage.addActor(home);

    acidCats = new Rectangle(30, height - (height / 4 * 3 - 140) - 200, width - 60, 200);
    //	aliveCats = new Rectangle(30, Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/4*3-360)-200, Gdx.graphics.getWidth()-60, 200);
  }

  @Override public void render(float delta) {

    Gdx.gl.glClearColor(135 / 255f, 206 / 255f, 235 / 255f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    yourFishes.setText("You have " + Settings.scores[3] + " fishes");

    if (!Settings.aliveBought && Settings.scores[3] < 250) stage.addActor(cantBuy);

    if (Settings.aliveBought) stage.addActor(bought);

    sBatch.begin();
    sBatch.draw(background, 0, 0, width, height);
    // buttons background
    sBatch.draw(AcidCat.manager.get(Assets.txrPauseBack, Texture.class), 15, 20, width - 30,
        height - 20 - 15);
    sBatch.draw(AcidCat.manager.get(Assets.txrPauseBack, Texture.class), 30, height / 4 * 3 - 140,
        width - 60, 200);
    sBatch.draw(AcidCat.manager.get(Assets.txrPauseBack, Texture.class), 30, height / 4 * 3 - 360,
        width - 60, 200);

    // show what pack is used
    if (Settings.catsPack == 0) {
      sBatch.draw(AcidCat.manager.get(Assets.txrPauseBack, Texture.class), 30, height / 4 * 3 - 140,
          width - 60, 200);
    }
    if (Settings.catsPack == 1) {
      sBatch.draw(AcidCat.manager.get(Assets.txrPauseBack, Texture.class), 30, height / 4 * 3 - 360,
          width - 60, 200);
    }

    // pictures on packs
    //sBatch.draw(Assets.acidCat, 50, height/4*3-140+50, 100, 100);
    //sBatch.draw(Assets.aliveCat, 50, height/4*3-360+50, 100,100);

    sBatch.end();

    stage.draw();

    if (Gdx.input.justTouched()) {
      // choose acid pack
      if (acidCats.contains(Gdx.input.getX(), Gdx.input.getY())) {
        Settings.catsPack = 0;
        Settings.save();
        ////////////Assets.loadCatsAtlas();
        ////////////////////////GameScreen.catsCreated = false;
      }

    /*    	if(aliveCats.contains(Gdx.input.getX(), Gdx.input.getY())) {
        		Settings.catsPack = 1;
            	Settings.save();
        		Assets.loadCatsAtlas();
        		GameScreen.catsCreated = false;
        	}
       */
    }
  }

  @Override public void show() {

    InputProcessor backProcessor = new InputAdapter() {
      @Override public boolean keyDown(int keycode) {

        if ((keycode == Keys.ESCAPE) || (keycode == Keys.BACK)) {
          game.setScreen(new MenuScreen(game));
        }
        return false;
      }
    };

    InputMultiplexer multiplexer = new InputMultiplexer(stage, backProcessor);
    Gdx.input.setInputProcessor(multiplexer);

    Gdx.input.setCatchBackKey(true);
  }

  @Override public void resize(int width, int height) {
    // TODO Auto-generated method stub

  }

  @Override public void pause() {
    // TODO Auto-generated method stub

  }

  @Override public void resume() {
    // TODO Auto-generated method stub

  }

  @Override public void hide() {
    // TODO Auto-generated method stub

  }

  @Override public void dispose() {
    // TODO Auto-generated method stub
    stage.dispose();
  }
}
