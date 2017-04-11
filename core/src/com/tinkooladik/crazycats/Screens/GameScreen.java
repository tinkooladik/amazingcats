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
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.tinkooladik.crazycats.AcidCat;
import com.tinkooladik.crazycats.Actors.Cat;
import com.tinkooladik.crazycats.Actors.Fish;
import com.tinkooladik.crazycats.Actors.Plus;
import com.tinkooladik.crazycats.Actors.RewardedButton;
import com.tinkooladik.crazycats.Actors.TextureActor;
import com.tinkooladik.crazycats.Assets;
import com.tinkooladik.crazycats.Settings;
import gpservices.Achievements;
import java.util.Random;

class GameScreen extends ScreenAdapter {
  private static boolean catsCreated = false, helpShowed = false;
  private AcidCat game;
  private Stage stage;
  private int del, fishAdd, catAddLeft, catAddRight, lifeAddLeft, lifeAddRight, borderTimer,
      untchTimer;
  private SpriteBatch renBatch, dialogBatch;
  private Random rand = new Random();
  private Cat cat;
  private Texture background;
  private TextureActor pause, dialogBack, dialogBg, dialogBtn, dialogBtnTransp, lifeLost,
      borderWarning, help, pauseBg, untchActor;
  private boolean dialogExist = false, redScreen = false, borderWarningShowed = false;
  private Group dialog;
  private int width, height;
  private BitmapFont font, smallFont, bigFont;
  private GlyphLayout scoreLayout, dialogLayout;
  private int bg = 1, bgDel = 0;
  private float lastX, lastY;
  private Rectangle untchBounds;
  private RewardedButton rewardedBtn;
  private int rewardedRequest = 0;
  private boolean rewarding = false;
  private State state = State.READY;

  GameScreen(AcidCat game) {
    this.game = game;

    width = Gdx.graphics.getWidth();
    height = Gdx.graphics.getHeight();

    stage = new Stage(new StretchViewport(width, height));
    del = 0;
    fishAdd = 0;
    catAddLeft = 250;
    catAddRight = 500;
    lifeAddLeft = 200;
    lifeAddRight = 400;
    borderTimer = 0;
    untchTimer = 0;

    //Assets.load();
    renBatch = new SpriteBatch();
    dialogBatch = new SpriteBatch();

    font = new BitmapFont(Gdx.files.internal("data/font.fnt"), false);
    font.setColor(Color.BLACK);
    float x = width / 720f;
    float y = height / 1280f;
    font.getData().setScale(x, y);

    smallFont = new BitmapFont(Gdx.files.internal("data/font.fnt"), false);
    smallFont.setColor(Color.DARK_GRAY);
    smallFont.getData().setScale(0.65f * x, 0.65f * y);

    bigFont = new BitmapFont(Gdx.files.internal("data/font.fnt"), false);
    bigFont.setColor(Color.WHITE);
    bigFont.getData().setScale(1.5f * x, 1.5f * y);

    scoreLayout = new GlyphLayout();
    dialogLayout = new GlyphLayout();

    background = AcidCat.manager.get(Assets.txrGameBg, Texture.class);

    help = new TextureActor(new TextureRegion(AcidCat.manager.get(Assets.txrHelp, Texture.class)));
    help.setSize(width, height);
    help.setPosition(0, 0);
    help.addListener(new InputListener() {
      @Override
      public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        help.remove();
      }
    });

    lifeLost =
        new TextureActor(new TextureRegion(AcidCat.manager.get(Assets.txrLostLife, Texture.class)));
    lifeLost.setSize(width, height);
    lifeLost.setPosition(0, 0);

    borderWarning =
        new TextureActor(new TextureRegion(AcidCat.manager.get(Assets.txrLostLife, Texture.class)));
    borderWarning.setSize(width, height);
    borderWarning.setPosition(0, 0);

    dialogBack = new TextureActor(
        new TextureRegion(AcidCat.manager.get(Assets.txrDialogBack, Texture.class)));
    dialogBg =
        new TextureActor(new TextureRegion(AcidCat.manager.get(Assets.txrDialogBg, Texture.class)));
    dialogBtn = new TextureActor(
        new TextureRegion(AcidCat.manager.get(Assets.txrDialogBtn, Texture.class)));
    dialogBtnTransp = new TextureActor(
        new TextureRegion(AcidCat.manager.get(Assets.txrTranspBtn, Texture.class)));

    float pauseSize = width / 10;
    pause =
        new TextureActor(new TextureRegion(AcidCat.manager.get(Assets.txrPause, Texture.class)));
    pause.setSize(pauseSize, pauseSize);
    pause.setPosition(width - 5 - pauseSize, height - 5 - pauseSize);
    pause.toFront();
    pause.addListener(new GoToPauseListener());

    untchActor =
        new TextureActor(new TextureRegion(AcidCat.manager.get(Assets.txrTouch, Texture.class)));
    untchActor.setSize(pauseSize * 2f, pauseSize * 2f);

    rewardedBtn = new RewardedButton(game);

    if (game.catsAmnt > 5) state = State.READY;

    AcidCat.rewardedAfter--;

    Settings.scores[3] += 200;
  }

  @Override public void render(float delta) {
    Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clear screen
    switch (state) {
      case READY:
        if (!catsCreated) {
          stage.clear();
          createCats();
          catsCreated = true;
          if (!helpShowed) {
            stage.addActor(help);
            helpShowed = true;
          }
          dialogExist = false;
          stage.addActor(pause);
          Settings.addScore(AcidCat.score, AcidCat.fishScore);
          Settings.save();
          del = 0;
          borderTimer = 0;
          untchTimer = 0;
        }
        if (Gdx.input.justTouched()) {
          help.remove();
          untchTimer = 0;
          lastX = Gdx.input.getX();
          lastY = Gdx.input.getY();
          state = State.RUNNING;
        }
        break;
      case RUNNING:
        if (game.lives > 0 && borderTimer <= 50 && untchTimer <= 100) {
          update();
        } else {
          state = State.GAMEOVER;
        }
        break;
      case UNTOUCHED:
        if (untchTimer < 100) {
          if (Gdx.input.isTouched(0) && untchBounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
            state = State.RUNNING;
            untchActor.remove();
            untchTimer = 0;
          } else {
            untchTimer++;
          }
        } else {
          state = State.GAMEOVER;
        }
        break;
      case PAUSE:
        // do nothing
        break;
      case DIALOG:
        if (!dialogExist) showDialog();

        /* if rewarded is possible */
        if (rewarding) {
          rewardedBtn.act(delta);
          rewardedRequest++;

          /* check if video finished */
          if (rewardedRequest == 50) {
            rewardedRequest = 0;
            if (AcidCat.googleServices.isRewarded()) {
              AcidCat.googleServices.resetRewardedSuccess();
              game.lives += 3;
              untchActor.remove();

              dialog.remove();
              rewardedBtn.remove();
              state = State.READY;
              dialogExist = false;
              rewarding = false;
            }
          }
        }

        break;
      case GAMEOVER:
        if (Settings.scores[3] >= 5) {
          state = State.DIALOG;
        } else {
          gameOver();
        }
        break;
    }
    draw();
  }

  private void update() {
    del++;
    if (del == 25) {
      AcidCat.score++;
      fishAdd++;
      del = 0;
    }
    if (fishAdd == 7) {
      addFish();
      fishAdd = 0;
    }

    // BACKGROUND CHANGES
    if (bg <= 8) {
      bgDel++;
      if (bgDel == 2000) {
        bgDel = 0;
        background = AcidCat.manager.get("data/" + bg + ".jpg", Texture.class);
        bg++;
      }
    } else {
      bg = 1;
    }

    // ADD LIFE
    if (AcidCat.score > lifeAddLeft && AcidCat.score < lifeAddRight && game.lives < 3) {
      game.lives++;
      lifeAddLeft += 200;
      lifeAddRight += 200;
      Plus pLifeActor = new Plus('l');
      stage.addActor(pLifeActor);
    }

    // ADDITIONAL CATS

    if (AcidCat.score > catAddLeft && AcidCat.score < catAddRight && ++game.catsAmnt <= 13) {
      cat = new Cat(Assets.getCat(game.catsAmnt), rand.nextInt(70) + 20);
      cat.setPosition(0, height - width / 4);
      cat.addListener(new CatListener());
      stage.addActor(cat);
      catAddLeft += 250;
      catAddRight += 250;
    }

    // BORDERS
    boolean border = Gdx.input.getX() <= 25
        || Gdx.input.getX() >= width - 25
        || Gdx.input.getY() >= height - 25
        || Gdx.input.getY() <= 25;
    boolean leftCorner = Gdx.input.getX() <= 75 && Gdx.input.getY() >= height - 75;
    boolean rightCorner = Gdx.input.getX() >= width - 75 && Gdx.input.getY() >= height - 75;
    if (border || leftCorner || rightCorner) {
      if (!borderWarningShowed) {
        stage.addActor(borderWarning);
        borderWarningShowed = true;
      }
      borderTimer++;
    } else if (borderTimer != 0) {
      borderWarningShowed = false;
      borderWarning.remove();
      borderTimer = 0;
    }

    // UNTOUCHED TIMER
    if (Gdx.input.isTouched(0)) {
      lastX = Gdx.input.getX();
      lastY = Gdx.input.getY();
    } else {
        /* add timer actor */
      float untchSize = untchActor.getWidth();
      untchActor.setPosition(lastX - untchSize / 2, height - lastY - untchSize / 2);
      stage.addActor(untchActor);
      untchBounds =
          new Rectangle(untchActor.getX(), height - untchActor.getTop(), untchSize, untchSize);
      state = State.UNTOUCHED;
    }

    // PLUS 5 FOR FISH
    if (game.p5) {
      game.p5 = false;
      Plus p5actor = new Plus('s');
      stage.addActor(p5actor);
    }

    stage.act();
  }

  private void draw() {
    renBatch.begin();
    renBatch.draw(background, 0, 0, width, height);
    if (game.lives >= 1) {
      renBatch.draw(AcidCat.manager.get(Assets.txrLife, Texture.class), 5, height - 5 - width / 15,
          width / 15, width / 15);
    }
    if (game.lives >= 2) {
      renBatch.draw(AcidCat.manager.get(Assets.txrLife, Texture.class), 5 + width / 15,
          height - 5 - width / 15, width / 15, width / 15);
    }
    if (game.lives == 3) {
      renBatch.draw(AcidCat.manager.get(Assets.txrLife, Texture.class), 5 + (width / 15) * 2,
          height - 5 - width / 15, width / 15, width / 15);
    }
    // score
    scoreLayout.setText(font, String.valueOf(AcidCat.score));
    float x = (width - scoreLayout.width) / 2;
    float y = height - scoreLayout.height * 0.5f;
    font.draw(renBatch, scoreLayout, x, y);
    // fishes score
    String fishesAmnt = AcidCat.fishScore + " / " + Settings.scores[3];
    y -= scoreLayout.height * 1.5f;
    scoreLayout.setText(smallFont, fishesAmnt);
    x = (width - scoreLayout.width) / 2;
    smallFont.draw(renBatch, scoreLayout, x, y);
    renBatch.end();

    stage.draw();

    dialogBatch.begin();
    if (dialogExist) {
      // second chance
      dialogLayout.setText(font, "Second chance?");
      x = (width - dialogLayout.width) / 2;
      y = dialogBg.getTop() - dialogLayout.height;
      font.draw(dialogBatch, dialogLayout, x, y);
      // fishes amount
      y -= dialogLayout.height * 2.5f;
      dialogLayout.setText(smallFont, "You have " + Settings.scores[3] + " fishes");
      x = dialogBg.getX() + 50;
      smallFont.draw(dialogBatch, dialogLayout, x, y);
      // no thanks button
      dialogLayout.setText(font, "No, thanks");
      x = (width - dialogLayout.width) / 2;
      y = dialogBtn.getTop() - (dialogBtn.getHeight() - dialogLayout.height) / 2;
      font.draw(dialogBatch, dialogLayout, x, y);
    }
    dialogBatch.end();

    if (redScreen) {
      lifeLost.remove();
    }
  }

  private void createCats() {
    for (int i = 1; i <= game.catsAmnt; i++) {
      cat = new Cat(Assets.getCat(i), rand.nextInt(70) + 20);
      cat.setPosition(rand.nextInt(width - width / 4), rand.nextInt(height - width / 4));
      cat.addListener(new CatListener());
      stage.addActor(cat);
    }
  }

  private void addFish() {
    float x = rand.nextInt(width - width / 5);
    float y = rand.nextInt(height - width / 5 - 40);
    Fish fish = new Fish(game, x, y);
    stage.addActor(fish);
    fish.toBack();
  }

  private void gameOver() {
    stage.clear();
    Settings.addScore(AcidCat.score, AcidCat.fishScore);
    Settings.gamesPlayed++;
    AcidCat.task.update();
    Achievements.updateAchievements(AcidCat.fishScore, AcidCat.score);
    Settings.save();
    catsCreated = false;
    game.catsAmnt = 5;
    AcidCat.score = 0;
    AcidCat.fishScore = 0;
    game.lives = 3;
    state = State.READY;
    game.setScreen(new GameOverScreen(game));
    dispose();
  }

  private void showPause() {
    pauseBg =
        new TextureActor(new TextureRegion(AcidCat.manager.get(Assets.txrPauseBg, Texture.class)));
    pauseBg.setPosition(0, 0);
    pauseBg.setSize(width, height);
    stage.addActor(pauseBg);

    Skin skin = new Skin();
    skin.addRegions(Assets.atlas);

    TextButtonStyle textButtonStyle = new TextButtonStyle();
    textButtonStyle.font = bigFont;
    textButtonStyle.up = skin.getDrawable("for_pause");
    textButtonStyle.down = skin.getDrawable("for_pause");
    textButtonStyle.checked = skin.getDrawable("for_pause");

    final TextButton button = new TextButton("RESUME", textButtonStyle);
    float x = (width - button.getWidth()) / 2;
    float y = (height - button.getHeight()) / 2;
    button.setPosition(x, y);
    button.addListener(new ClickListener() {
      @Override public void clicked(InputEvent event, float x, float y) {
        button.remove();
        pauseBg.remove();
        state = State.READY;
      }
    });
    stage.addActor(button);
  }

  private void showDialog() {

    game.lives = 0;
    dialog = new Group();
    int[] price = new int[] { 50, 90, 130 };

    // PASSIVE

    // background

    dialogBack.setPosition(0, 0);
    dialogBack.setSize(width, height);
    dialog.addActor(dialogBack);

    float dialogSize = width / 15;
    dialogBg.setPosition(dialogSize, (height - width + (dialogSize) * 2) / 2);
    dialogBg.setSize(width - (dialogSize) * 2, width - (dialogSize) * 2);
    dialog.addActor(dialogBg);

    // button

    dialogBtn.setSize(width - dialogBg.getWidth() / 2, width / 8);
    dialogBtn.setPosition(dialogBg.getX() * 3, dialogBg.getY() + dialogBtn.getHeight() / 2);
    dialogBtn.addListener(new GOListener());
    dialog.addActor(dialogBtn);

    Label.LabelStyle dialogStyle = new Label.LabelStyle();
    dialogStyle.font = font;
    dialogStyle.fontColor = Color.BLACK;

    // buttons with lives' price text

    int j = 2;
    for (int i = 0; i <= 2; i++) {
      TextureActor addButton = new TextureActor(
          new TextureRegion(AcidCat.manager.get(Assets.txrDialogBtn, Texture.class)));
      addButton.setSize(width - (width / 12) * 2, dialogBtn.getHeight());
      addButton.setPosition((width - addButton.getWidth()) / 2,
          dialogBtn.getTop() + dialogBtn.getHeight() / 2 + dialogBtn.getHeight() / 10 * i
          + addButton.getHeight() * i);
      dialog.addActor(addButton);

      Label buttonText = new Label("" + price[j], dialogStyle);
      buttonText.setPosition(addButton.getRight() - buttonText.getWidth() - 50,
          (addButton.getTop() + addButton.getY()) / 2 - buttonText.getHeight() / 2);
      dialog.addActor(buttonText);

      j--;
    }

    // pictures

    // ACTIVE

    // "no" button

    dialogBtnTransp.setPosition(dialogBtn.getX(), dialogBtn.getY());
    dialogBtnTransp.setSize(dialogBtn.getWidth(), dialogBtn.getHeight());
    dialogBtnTransp.addListener(new GOListener());
    dialog.addActor(dialogBtnTransp);

    // add-life buttons
    j = 2;
    for (int i = 0; i <= 2; i++) {
      addLifePic(i, j - 1);
      if (Settings.scores[3] >= price[j]) {
        activeButton(i, j);
      } else {
        passiveButton(i);
      }
      j--;
    }

    stage.addActor(dialog);

    if (AcidCat.rewardedAfter <= 0 && AcidCat.googleServices.isRewardedReady()) {
      //AcidCat.rewardedAfter <= 0 &&
      // add rewarded button
      rewarding = true;
      rewardedBtn.setY(dialogBg.getTop() - Gdx.graphics.getWidth() / 5);
      stage.addActor(rewardedBtn);
    }

    dialogExist = true;
  }

  private void activeButton(int i, int j) {
    final int addLives = j + 1;
    TextureActor addButton = new TextureActor(
        new TextureRegion(AcidCat.manager.get(Assets.txrTranspBtn, Texture.class)));
    addButton.setSize(width - (width / 12) * 2, dialogBtn.getHeight());
    addButton.setPosition((width - addButton.getWidth()) / 2,
        dialogBtn.getTop() + dialogBtn.getHeight() / 2 + dialogBtn.getHeight() / 10 * i
        + addButton.getHeight() * i);
    addButton.addListener(new ClickListener() {
      @Override public void clicked(InputEvent event, float x, float y) {
        game.lives += addLives;
        switch (addLives) {
          case 1:
            Settings.scores[3] -= 50;
            break;
          case 2:
            Settings.scores[3] -= 90;
            break;
          case 3:
            Settings.scores[3] -= 130;
            break;
        }
        untchActor.remove();
        dialog.remove();
        borderWarning.remove();
        borderTimer = 0;
        rewardedBtn.remove();
        state = State.READY;
        dialogExist = false;
      }
    });
    dialog.addActor(addButton);
  }

  private void passiveButton(int i) {
    TextureActor addButton = new TextureActor(
        new TextureRegion(AcidCat.manager.get(Assets.txrDialogBtnPassive, Texture.class)));
    addButton.setSize(width - (width / 12) * 2, dialogBtn.getHeight());
    addButton.setPosition((width - addButton.getWidth()) / 2,
        dialogBtn.getTop() + dialogBtn.getHeight() / 2 + dialogBtn.getHeight() / 10 * i
        + addButton.getHeight() * i);
    dialog.addActor(addButton);
  }

  private void addLifePic(int i, int j) {
    for (int k = 0; k <= j + 1; k++) {
      TextureActor addPic =
          new TextureActor(new TextureRegion(AcidCat.manager.get(Assets.txrLife, Texture.class)));
      addPic.setSize(dialogBtn.getHeight(), dialogBtn.getHeight());
      addPic.setPosition(dialogBg.getX() * 2 + addPic.getWidth() * k,
          dialogBtn.getTop() + dialogBtn.getHeight() / 2 + dialogBtn.getHeight() / 10 * i
          + addPic.getHeight() * i);
      dialog.addActor(addPic);
    }
  }

  @Override public void resize(int width, int height) {

  }

  @Override public void show() {
    InputProcessor backProcessor = new InputAdapter() {
      @Override public boolean keyDown(int keycode) {

        if ((keycode == Keys.ESCAPE) || (keycode == Keys.BACK)) {
          gameOver();
          game.setScreen(new MenuScreen(game));
        }
        return false;
      }
    };

    InputMultiplexer multiplexer = new InputMultiplexer(stage, backProcessor);
    Gdx.input.setInputProcessor(multiplexer);

    Gdx.input.setCatchBackKey(true);
  }

  @Override public void hide() {

  }

  @Override public void pause() {
    if (state == State.RUNNING || state == State.UNTOUCHED) {
      state = State.PAUSE;
      showPause();
    }
  }

  @Override public void resume() {

  }

  @Override public void dispose() {
    stage.dispose();

    renBatch.dispose();
    dialogBatch.dispose();

    font.dispose();
    smallFont.dispose();
    bigFont.dispose();
    super.dispose();
  }

  private enum State {
    READY, RUNNING, UNTOUCHED, PAUSE, DIALOG, GAMEOVER
  }

  private class GOListener extends ClickListener {
    @Override public void clicked(InputEvent event, float x, float y) {
      gameOver();
    }
  }

  private class CatListener extends InputListener {
    @Override public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
      if (pointer == 0) {
        if (game.lives == 0) {
          state = State.DIALOG;
        } else {
          game.lives--;
          if (Settings.vibroEnabled) Gdx.input.vibrate(100);
          stage.addActor(lifeLost);
          redScreen = true;
          if (Settings.soundEnabled) Assets.sound.play();
        }
      }
    }
  }

  private class GoToPauseListener extends InputListener {
    @Override public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
      state = State.PAUSE;
      showPause();
    }
  }
}