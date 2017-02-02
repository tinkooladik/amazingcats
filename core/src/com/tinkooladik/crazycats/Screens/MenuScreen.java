package com.tinkooladik.crazycats.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.tinkooladik.crazycats.AcidCat;
import com.tinkooladik.crazycats.Actors.TextureActor;
import com.tinkooladik.crazycats.Assets;
import com.tinkooladik.crazycats.Settings;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MenuScreen extends ScreenAdapter {
//    private TextureActor gameButton;
//    private TextureActor recordButton, settButton, achieveButton;
	AcidCat game;
  private Texture background, bgText, bgText1, bgText2, bgText3;
  private Stage stage;
  private SpriteBatch bgBatch, settBatch, bonusBatch;
  private int text = 2, del = 0;
  private Rectangle playButton;//, menuCat;
  private TextureActor settBtn, audio, music, sound, vibro, musicOff,
    					leaderboard, achievements, rate;
  private float width, height;
  private Group settings;
  private boolean settShown = false;
  public boolean getBonus = false;
  public String today;

  public MenuScreen(AcidCat game) {
    this.game = game;
    //Settings.load();

		// save current date to compare and give bonuses
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		today = df.format(Calendar.getInstance().getTime());
		
		if (!Settings.lastBonusDay.equals(today))	{
			Settings.lastBonusDay = new String(today);
			getBonus = true;
    }
		//if (Settings.lastBonusDay == today)	getBonus = true;
		
		// stage staff    	
    width = Gdx.graphics.getWidth(); height = Gdx.graphics.getHeight();
    	
    background = AcidCat.manager.get(Assets.txrGameOver, Texture.class);
    bgText1 = AcidCat.manager.get(Assets.menuTxt1, Texture.class);
    bgText2 = AcidCat.manager.get(Assets.menuTxt2, Texture.class);
    bgText3 = AcidCat.manager.get(Assets.menuTxt3, Texture.class);
    bgText = bgText1;
    bgBatch = new SpriteBatch();
        
    settBatch = new SpriteBatch();
    bonusBatch = new SpriteBatch();
    	
    stage = new Stage(new StretchViewport(width, height));
        
    playButton = new Rectangle(width - width/2, height - width/4, width/2, width/4);

    // leaderboard
    leaderboard = new TextureActor(new TextureRegion(
						AcidCat.manager.get(Assets.txrLeaderboard, Texture.class)));
    leaderboard.setSize(width/6, width/6);
    leaderboard.setPosition(width - leaderboard.getWidth(), height - leaderboard.getHeight() * 2.5f);
    leaderboard.addListener(new ClickListener() {
			@Override
	    public void clicked(InputEvent event, float x, float y) {
		    AcidCat.googleServices.gameHelperOnStart();
				AcidCat.googleServices.showScores();
			}
    });
    stage.addActor(leaderboard);

    // achievements
    achievements = new TextureActor(new TextureRegion(
        AcidCat.manager.get(Assets.txrAchievements, Texture.class)));
    achievements.setSize(width/6, width/6);
    achievements.setPosition(width - achievements.getWidth(), height - achievements.getHeight() * 1.5f);
    achievements.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        AcidCat.googleServices.gameHelperOnStart();
        AcidCat.googleServices.showAchievements();
      }
    });
    stage.addActor(achievements);

    // rate
    rate = new TextureActor(new TextureRegion(
        AcidCat.manager.get(Assets.txrRate, Texture.class)));
    rate.setSize(width/6, width/6);
    rate.setPosition(width - rate.getWidth(), height - rate.getHeight() * 4.5f);
    rate.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        AcidCat.googleServices.gameHelperOnStart();
        AcidCat.googleServices.rateGame();
      }
    });
    stage.addActor(rate);

    // settings
    settBtn = new TextureActor(new TextureRegion(AcidCat.manager.get(Assets.txrSettings, Texture.class)));
    settBtn.setSize(width/6, width/6);
    settBtn.setPosition(width - settBtn.getWidth(), height - settBtn.getHeight() * 3.5f);
    settBtn.addListener(new ClickListener() {
			@Override
	    public void clicked(InputEvent event, float x, float y) {
			  if (!settShown) showSettings();
				else { settings.remove(); settShown = false; }
			}
    });
    stage.addActor(settBtn);
        
		// UNCOMMENT when create new packs
       // menuCat = new Rectangle(Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/6, 100, Gdx.graphics.getWidth()/6, Gdx.graphics.getWidth()/6);
    settings = new Group();
    audio = new TextureActor(new TextureRegion(AcidCat.manager.get(Assets.txrAudio, Texture.class)));
    musicOff = new TextureActor(new TextureRegion(AcidCat.manager.get(Assets.txrMusicOff, Texture.class)));
    music = new TextureActor(new TextureRegion(AcidCat.manager.get(Assets.txrTranspBtn, Texture.class))); music.setSize(width/8, width/10);
    sound = new TextureActor(new TextureRegion(AcidCat.manager.get(Assets.txrTranspBtn, Texture.class))); sound.setSize(width/8, width/10);
    vibro = new TextureActor(new TextureRegion(AcidCat.manager.get(Assets.txrTranspBtn, Texture.class))); vibro.setSize(width/8, width/10);
    	
    //audio.setSize(settBtn.getWidth() * 1.25f, settBtn.getHeight() * 3.75f);
    musicOff.setSize(width/8, width/8);

   	float distance = settBtn.getWidth() * 0.1f;
    	
   	audio.setSize(sound.getWidth() + distance * 4f, sound.getHeight() * 3f + distance * 4f);
   	audio.setPosition(settBtn.getX() - audio.getWidth() - distance * 2f, settBtn.getY() - audio.getHeight()/2 + settBtn.getHeight()/2);
   	settings.addActor(audio);

   	music.setPosition(audio.getX() + distance, audio.getTop() - music.getHeight() - distance * 2); settings.addActor(music);
   	sound.setPosition(audio.getX() + distance, audio.getTop() - sound.getHeight() * 2f - distance * 2f); settings.addActor(sound);
   	vibro.setPosition(sound.getX(), audio.getTop() - sound.getHeight() * 3f - distance * 2f); settings.addActor(vibro);
    	
   	music.addListener(new ClickListener() {
			@Override
	    public void clicked(InputEvent event, float x, float y) {
				Settings.musicEnabled = !Settings.musicEnabled;
				if (Settings.musicEnabled)
					Assets.music.play();
				else
					Assets.music.pause();
		  }
    });
    sound.addListener(new ClickListener() {
			@Override
      public void clicked(InputEvent event, float x, float y) {
				Settings.soundEnabled = !Settings.soundEnabled;
			}
    });
    vibro.addListener(new ClickListener() {
			@Override
	    public void clicked(InputEvent event, float x, float y) {
				Settings.vibroEnabled = !Settings.vibroEnabled;
			}
    });
    	
    //AcidCat.myRequestHandler.showAds(false);
    }

    // screen rendering
    @Override
    public void render(float delta) {
      Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clear screen
        
      if (del == 5) {
       	switch(text) {
       	case 1:
       		bgText = bgText1;
       		break;
       	case 2:
       		bgText = bgText2;
       		break;
       	case 3:
       		bgText = bgText3;
       		break;
       	}
       	del = 0;
      }
        
      del++;
      if (text<3) { text++; }
      else { text =1; }
        
      bgBatch.begin();
      bgBatch.draw(background, 0, 0, width, height);
      bgBatch.draw(bgText, 0, 0, width, height);
        
		// UNCOMMENT when create new packs
        //bgBatch.draw(Assets.menuCat, Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/6, Gdx.graphics.getHeight()-Gdx.graphics.getWidth()/6-100, Gdx.graphics.getWidth()/6, Gdx.graphics.getWidth()/6);
      bgBatch.end();
        
      stage.draw();
        
      settBatch.begin();
      if(!Settings.musicEnabled && settShown) settBatch.draw(new TextureRegion(
						AcidCat.manager.get(Assets.txrMusicOff, Texture.class)), music.getX(), music.getY(), music.getWidth(), music.getHeight());
		  if(!Settings.soundEnabled && settShown) settBatch.draw(new TextureRegion(
			    	AcidCat.manager.get(Assets.txrMusicOff, Texture.class)), sound.getX(), sound.getY(), sound.getWidth(), sound.getHeight());
		  if(!Settings.vibroEnabled && settShown) settBatch.draw(new TextureRegion(
    				AcidCat.manager.get(Assets.txrMusicOff, Texture.class)), vibro.getX(), vibro.getY(), sound.getWidth(), sound.getHeight());
		  settBatch.end();
		
		  if (getBonus) {
			  bonusBatch.begin();
			  bonusBatch.draw(new TextureRegion(AcidCat.manager.get(Assets.bonusBg, Texture.class)), 0, 0, width, height);
			  bonusBatch.draw(new TextureRegion(AcidCat.manager.get(Assets.bonusImg, Texture.class)), 20, (height - (width + 60))/2, width - 40, width + 60);
			  bonusBatch.end();
		  }

      if (Gdx.input.justTouched()) {
       	if (getBonus) {
       		Settings.scores[3] += 50;
       		Settings.lastBonusDay = today;
       		Settings.save();
       		getBonus = false;
       	}
       	else
       	if(playButton.contains(Gdx.input.getX(), Gdx.input.getY())) {
       		game.setScreen(new GameScreen(game));
       		dispose();
       	}
			// UNCOMMENT when create new packs
        /*
        	if(menuCat.contains(Gdx.input.getX(), Gdx.input.getY())) {
        		game.setScreen(new ChooseCats(game));
        		return;
        	}
        	*/
      }
    }

    
    private void showSettings() {
    	stage.addActor(settings);
    	settShown = true;
    }
    
    @Override
    public void resize(int width, int height) {}
    
    @Override
    public void show() {
		InputProcessor backProcessor = new InputAdapter() {
        	@Override
        	public boolean keyDown(int keycode) {
        		
        		if ((keycode == Keys.ESCAPE) || (keycode == Keys.BACK)) {
            		Gdx.app.exit();
        		}
        		return false;
        	}
        };
        
        InputMultiplexer multiplexer = new InputMultiplexer(stage, backProcessor);
        Gdx.input.setInputProcessor(multiplexer);
        
        Gdx.input.setCatchBackKey(true);
    }
    
    @Override
    public void hide() {}
    @Override
    public void pause() {
    	Settings.save();
    }
    @Override
    public void resume() {}
    @Override
    public void dispose() {
			super.dispose();

    	stage.dispose();

			bgBatch.dispose();
			settBatch.dispose();
			bonusBatch.dispose();
		}
}