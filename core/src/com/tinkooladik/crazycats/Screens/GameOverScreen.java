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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.tinkooladik.crazycats.AcidCat;
import com.tinkooladik.crazycats.Actors.TextureActor;
import com.tinkooladik.crazycats.Assets;
import com.tinkooladik.crazycats.Settings;
import gpservices.Achievements;

public class GameOverScreen extends ScreenAdapter {
	GlyphLayout layout;
	AcidCat game;
	private TextureActor replayButton, menuButton;
	private Stage stage;
	private String scoreText, maxScoreText, taskCompleted;
  private BitmapFont font, smallFont, bigFont, fontBestScore;
  private SpriteBatch renBatch, progressBarBatch;
  private Texture background;
  private int width, height;
	private int timerBestScore;
	private float positionYBestScore;
	private ProgressBar progressBar;

	public GameOverScreen(AcidCat game) {

		this.game = game;
		//	Assets.load();

		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		replayButton =
				new TextureActor(new TextureRegion(AcidCat.manager.get(Assets.txrReplay, Texture.class)));
		menuButton =
				new TextureActor(new TextureRegion(AcidCat.manager.get(Assets.txrMenu, Texture.class)));

		replayButton.setSize(width / 3, width / 9);
		menuButton.setSize(width / 3, width / 9);

		menuButton.setPosition(width - menuButton.getWidth() - 20, menuButton.getHeight() * 4);
		replayButton.setPosition(width - replayButton.getWidth() - 20, menuButton.getTop() + 20);

		replayButton.addListener(new GoToRestartListener());
		menuButton.addListener(new GoToMenuListener());

		stage = new Stage(new StretchViewport(width, height));
		stage.addActor(replayButton);
		stage.addActor(menuButton);

		maxScoreText = scoreText = "Huy";

		timerBestScore = 0;

		font = new BitmapFont(Gdx.files.internal("data/font.fnt"), false);
		font.setColor(Color.WHITE);
		float x = width / 720f;
		float y = height / 1280f;
		font.getData().setScale(0.9f * x, 0.9f * y);

		smallFont = new BitmapFont(Gdx.files.internal("data/font.fnt"), false);
		smallFont.setColor(Color.WHITE);
		smallFont.getData().setScale(0.65f * x, 0.65f * y);

		bigFont = new BitmapFont(Gdx.files.internal("data/font.fnt"), false);
		bigFont.setColor(Color.WHITE);
		bigFont.getData().setScale(4f * x, 4f * y);

		fontBestScore = new BitmapFont(Gdx.files.internal("data/font.fnt"), false);
		fontBestScore.setColor(Color.WHITE);
		fontBestScore.getData().setScale(1.5f * x, 1.5f * y);

		layout = new GlyphLayout();

		renBatch = new SpriteBatch();
		progressBarBatch = new SpriteBatch();

		background = AcidCat.manager.get(Assets.txrGameOver, Texture.class);

		// task

		if (AcidCat.task.isCompleted()) {
			Achievements.updateTasksAchievements();
			Settings.taskNum++;
			Settings.taskProgress = 0;
			AcidCat.task = AcidCat.taskFactory.getNewTask();

			//AcidCat.googleServices.showToast("Task " + Settings.taskNum + " completed!");
			taskCompleted = "Task " + Settings.taskNum + " completed!";
		} else {
			taskCompleted = "";
		}

		// ads

		if (AcidCat.interstitialCount >= 5) {
			AcidCat.googleServices.showInterstitial(new Runnable() {
				@Override public void run() {
					System.out.println("Interstitial app closed");
					Gdx.app.exit();
				}
			});
			AcidCat.interstitialCount = 0;
		} else {
			AcidCat.interstitialCount++;
		}

		if (AcidCat.googleServices.isNetworkAvailable()) {
			AcidCat.googleServices.showBannerAd();
		}

		// Progress bar
		ProgressBar.ProgressBarStyle pbs = new ProgressBar.ProgressBarStyle(new TextureRegionDrawable(
				new TextureRegion(AcidCat.manager.get(Assets.txrProgressBar, Texture.class))),
				new TextureRegionDrawable(
						new TextureRegion(AcidCat.manager.get(Assets.txrKnob, Texture.class))));
		pbs.knobBefore = pbs.knob;
		//pbs.knobBefore = new TextureRegionDrawable(Assets.progressBarTop);
		progressBar = new ProgressBar(0, AcidCat.task.target, 1, false, pbs);
		progressBar.setValue(Settings.taskProgress);
		progressBar.setSize(width - 100, 150);
		progressBar.setX(50);
		progressBar.setAnimateDuration(2);
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

	@Override public void render(float delta) {
		Gdx.gl.glClearColor(135 / 255f, 206 / 255f, 235 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clear screen
		update(delta);
		draw();
	}

	private void draw() {
		renBatch.begin();
		renBatch.draw(background, 0, 0, width, height);

		if (!Settings.newMaxScore) {
			drawScore();
		} else {
			if (timerBestScore < 55) {
				drawScore();
				timerBestScore++;
			} else if (timerBestScore < 100) {
				drawBestScore();
				timerBestScore++;
			} else {
				timerBestScore = 0;
			}
		}

		layout.setText(smallFont, maxScoreText);
		float x = 20;
		float y = layout.height * 10;
		smallFont.draw(renBatch, layout, x, y);

		layout.setText(font, Settings.taskNum + 1 + ". " + AcidCat.task.getDescription());
		y = height - 20;
		font.draw(renBatch, layout, x, y);

		//if (Settings.newMaxScore) renBatch.draw(Assets.newBestScore, (width-width/2)/2, replayButton.getTop()+100, width/2, width/6);
		renBatch.end();

		progressBarBatch.begin();
		progressBar.setY(height - layout.height * 2 - 150);
		progressBar.draw(progressBarBatch, 1);

		layout.setText(smallFont, String.valueOf(Settings.taskProgress));
		x = (width - layout.width) / 2;
		y = progressBar.getY() + (progressBar.getHeight() - layout.height) / 2 + layout.height;
		smallFont.draw(progressBarBatch, layout, x, y);

		layout.setText(font, taskCompleted);
		x = (width - layout.width) / 2;
		y = progressBar.getY() - layout.height * 2f;
		font.draw(progressBarBatch, layout, x, y);

		progressBarBatch.end();

		stage.draw();
	}

	private void update(float delta) {
		stage.act(delta);
		scoreText = "" + Settings.scores[0];
		maxScoreText = "Best: " + Settings.scores[1];
	}

	private void drawScore() {
		layout.setText(bigFont, scoreText);
		float x = (width - layout.width) / 2;
		float y = height / 2 + layout.height * 2f;
		positionYBestScore = y - layout.height / 2;
		bigFont.draw(renBatch, layout, x, y);
	}

	private void drawBestScore() {
		layout.setText(fontBestScore, "NEW BEST SCORE!");
		float x = (width - layout.width) / 2;
		fontBestScore.draw(renBatch, layout, x, positionYBestScore);
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
		super.dispose();
		stage.dispose();

		font.dispose();
		fontBestScore.dispose();
		bigFont.dispose();
		smallFont.dispose();

		renBatch.dispose();
		progressBarBatch.dispose();
	}

	public class GoToRestartListener extends ClickListener {
		@Override public void clicked(InputEvent event, float x, float y) {
			AcidCat.googleServices.hideBannerAd();
			game.setScreen(new GameScreen(game));
		}
	}

	public class GoToMenuListener extends ClickListener {
		@Override public void clicked(InputEvent event, float x, float y) {
			AcidCat.googleServices.hideBannerAd();
			game.setScreen(new MenuScreen(game));
			/////////////////AcidCat.googleServices.showRewarded();
		}
	}
}
