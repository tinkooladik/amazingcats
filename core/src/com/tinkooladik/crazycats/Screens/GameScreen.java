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
import com.tinkooladik.crazycats.Actors.TextureActor;
import com.tinkooladik.crazycats.Assets;
import com.tinkooladik.crazycats.Settings;
import gpservices.Achievements;
import java.util.Random;

class GameScreen extends ScreenAdapter {
    private AcidCat game;
	private Stage stage;
    private int del, fishAdd, catAddLeft, catAddRight, lifeAddLeft, lifeAddRight, 
    				borderTimer, untchTimer;
    private SpriteBatch renBatch, dialogBatch;
	private Random rand = new Random();
	private Cat cat;
	private Texture background;
	private TextureActor pause, dialog_back, dialog_bg, dialog_btn, dialog_btnTransp,
							lifeLost, borderWarning, help, pauseBg, untchActor;
	private boolean dialogExist = false, redScreen = false, borderWarningShowed = false; 
	private Group dialog;
	private static boolean catsCreated = false, helpShowed = false;
	private int width, height;
	private BitmapFont font, smallFont, bigFont;
	private GlyphLayout scoreLayout, dialogLayout;
	private int bg = 1, bgDel = 0;
	private float lastX, lastY;
	private Rectangle untchBounds;
	
    private enum State {
    	READY, RUNNING, UNTOUCHED, PAUSE, DIALOG, GAMEOVER
    }
    
    private State state = State.READY;

    GameScreen(AcidCat game) {
    	this.game = game;
    	
    	width = Gdx.graphics.getWidth(); height = Gdx.graphics.getHeight();
    	
			stage = new Stage(new StretchViewport(width, height));
   	 	del = 0;
   	 	fishAdd = 0;
   	 	catAddLeft = 150; catAddRight = 300;
   	 	lifeAddLeft = 200; lifeAddRight = 400; 
   	 	borderTimer = 0; untchTimer = 0;
        
   	 	//Assets.load();
			renBatch = new SpriteBatch();
			dialogBatch = new SpriteBatch();

			font = new BitmapFont(Gdx.files.internal("data/font.fnt"),false);
			font.setColor(Color.BLACK);
			//float x = 2 * (width / height); // float y = height / 1280f;
			float x = width / 720f; float y = height / 1280f;
			font.getData().setScale(x, y);
        
			smallFont = new BitmapFont(Gdx.files.internal("data/font.fnt"),false);
			smallFont.setColor(Color.DARK_GRAY);
			smallFont.getData().setScale(0.65f*x, 0.65f*y);
        
      bigFont = new BitmapFont(Gdx.files.internal("data/font.fnt"),false);
      bigFont.setColor(Color.WHITE);
      bigFont.getData().setScale(1.5f*x, 1.5f*y);
        
      scoreLayout = new GlyphLayout();
      dialogLayout = new GlyphLayout();

      background = AcidCat.manager.get(Assets.gameBg, Texture.class);
        
      help = new TextureActor(new TextureRegion(AcidCat.manager.get(Assets.txrHelp, Texture.class)));
      help.setSize(width, height);
      help.setPosition(0, 0);
      help.addListener(new InputListener() {
        	@Override
    		public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
            help.remove();
        	}
      });
        
      lifeLost = new TextureActor(new TextureRegion(
          AcidCat.manager.get(Assets.txrLostLife, Texture.class)));
      lifeLost.setSize(width, height);
      lifeLost.setPosition(0, 0);
        
      borderWarning = new TextureActor(new TextureRegion(
          AcidCat.manager.get(Assets.txrLostLife, Texture.class)));
      borderWarning.setSize(width, height);
      borderWarning.setPosition(0, 0);
        
      dialog_back = new TextureActor(new TextureRegion(
          AcidCat.manager.get(Assets.txrDialogBack, Texture.class)));
      dialog_bg = new TextureActor(new TextureRegion(
          AcidCat.manager.get(Assets.txrDialogBg, Texture.class)));
      dialog_btn = new TextureActor(new TextureRegion(
          AcidCat.manager.get(Assets.txrDialogBtn, Texture.class)));
      dialog_btnTransp = new TextureActor(new TextureRegion(
          AcidCat.manager.get(Assets.txrTranspBtn, Texture.class)));
        
      float pauseSize = width/10;
      pause = new TextureActor(new TextureRegion(AcidCat.manager.get(Assets.txrPause, Texture.class)));
      pause.setSize(pauseSize, pauseSize);
      pause.setPosition(width-5-pauseSize, height-5-pauseSize);
      pause.toFront();
      pause.addListener(new GoToPauseListener());
        
      untchActor = new TextureActor(new TextureRegion(AcidCat.manager.get(Assets.txrTouch, Texture.class)));
      untchActor.setSize(pauseSize * 2f, pauseSize * 2f);
        
        
      if (game.catsAmnt>5) state = State.READY;
        
	    //AcidCat.myRequestHandler.showAds(false);

			AcidCat.rewardedAfter--;
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clear screen
        switch (state) {
        case READY:
        	if(!catsCreated) { stage.clear(); 
        						createCats(); catsCreated = true; 
        						if(!helpShowed) { stage.addActor(help); helpShowed = true; }
        				    	dialogExist = false;
        				    	stage.addActor(pause); 
        				    	Settings.addScore(AcidCat.score, AcidCat.fishScore); Settings.save();
        				    	del = 0; borderTimer = 0; untchTimer= 0;
        	}
            if (Gdx.input.justTouched()) {
		    	help.remove();
		    	untchTimer = 0;
		    	lastX = Gdx.input.getX(); lastY = Gdx.input.getY();
            	state = State.RUNNING;
        	}
        	break;
        case RUNNING:
        	if (game.lives > 0 && borderTimer <= 50 && untchTimer <= 100) {
        		update();
        	}
        	else {
        		state = State.GAMEOVER;
        	}
        	break;
        case UNTOUCHED:
        	if (untchTimer < 100) {
        		if (Gdx.input.isTouched(0) && untchBounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
        			state = State.RUNNING;
        			untchActor.remove();
                	untchTimer = 0;
        		}
        		else untchTimer++;
        	}
        	else state = State.GAMEOVER;
        	break;
        case PAUSE:
        	// do nothing
        	break;
        case DIALOG:
        	if (!dialogExist) showDialog();
        	break;
        case GAMEOVER:
        	if(Settings.scores[3]>=5) state = State.DIALOG;
    		else gameOver();
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
    	}
    	else bg = 1;

		// ADD LIFE
		if (AcidCat.score > lifeAddLeft && AcidCat.score < lifeAddRight && game.lives < 3) {
			game.lives++; lifeAddLeft += 200; lifeAddRight +=200;
			Plus pLifeActor = new Plus('l');
			stage.addActor(pLifeActor);
		}

		// ADDITIONAL CATS

    	if (AcidCat.score > catAddLeft && AcidCat.score < catAddRight && ++game.catsAmnt<=13) {
        	cat = new Cat(Assets.getCat(game.catsAmnt), rand.nextInt(70)+20);
        	cat.setPosition(0, height-width/4);
        	cat.addListener(new CatListener());
        	stage.addActor(cat);
    		catAddLeft += 150; catAddRight += 150;
    	}
    	
    	// BORDERS
    	boolean border = Gdx.input.getX() <=10 || Gdx.input.getX() >= width-10 || Gdx.input.getY() >= height-10;
    	boolean leftCorner = Gdx.input.getX() <= 50 && Gdx.input.getY() >= height-50;
    	boolean rightCorner = Gdx.input.getX() >= width - 50 && Gdx.input.getY() >= height - 50;
    	if (border || leftCorner || rightCorner) {
    		if (!borderWarningShowed) {
    			stage.addActor(borderWarning);
    			borderWarningShowed = true;
    		}
    		borderTimer++;
    	}
    	else if (borderTimer != 0) {
    		borderWarningShowed = false;
        	borderWarning.remove();
        	borderTimer = 0;
    	}
    	
    	// UNTOUCH TIMER
    	if (Gdx.input.isTouched(0)) {
    		lastX = Gdx.input.getX(); lastY = Gdx.input.getY(); 
    	}
    	else { 
    		/* add timer actor */ 
    		float untchSize = untchActor.getWidth();
    		untchActor.setPosition(lastX - untchSize/2, height - lastY - untchSize/2);
    		stage.addActor(untchActor);
    		untchBounds = new Rectangle(untchActor.getX(), height - untchActor.getTop(), untchSize, untchSize);
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
    	if (game.lives >= 1) { renBatch.draw(AcidCat.manager.get(Assets.txrLife, Texture.class), 5, height-5-width/15, width/15, width/15); }
    	if (game.lives >= 2) { renBatch.draw(
					AcidCat.manager.get(Assets.txrLife, Texture.class), 5+width/15, height-5-width/15, width/15, width/15); }
    	if (game.lives == 3) { renBatch.draw(
					AcidCat.manager.get(Assets.txrLife, Texture.class), 5+(width/15)*2, height-5-width/15, width/15, width/15); }
    	// score
        scoreLayout.setText(font, String.valueOf(AcidCat.score));
    	float x = (width-scoreLayout.width)/2; float y = height - scoreLayout.height * 0.5f;
    	font.draw(renBatch, scoreLayout, x, y);
    	// fishes score
    	String fishesAmnt = AcidCat.fishScore + " / " + Settings.scores[3];
    	y -= scoreLayout.height * 1.5f;
    	scoreLayout.setText(smallFont, fishesAmnt);
    	x = (width-scoreLayout.width)/2;
    	smallFont.draw(renBatch, scoreLayout, x, y);
    	renBatch.end();
    	
    	stage.draw(); 
    	
    	dialogBatch.begin();
    	if (dialogExist){
    		// second chance
    		dialogLayout.setText(font, "Second chance?"); 
    		x = (width-dialogLayout.width)/2; y = dialog_bg.getTop() - dialogLayout.height;
    		font.draw(dialogBatch, dialogLayout, x, y);
    		// fishes amount
    		y -= dialogLayout.height * 2.5f;
    		dialogLayout.setText(smallFont, "You have " + Settings.scores[3] + " fishes");
    		x = dialog_bg.getX() + 50; 
    		smallFont.draw(dialogBatch, dialogLayout, x, y);
    		// no thanks button
    		dialogLayout.setText(font, "No, thanks");
    		x = (width - dialogLayout.width)/2; y = dialog_btn.getTop() - (dialog_btn.getHeight() - dialogLayout.height)/2;
    		font.draw(dialogBatch, dialogLayout, x, y);
    	}
    	dialogBatch.end();
    	
    	if(redScreen) {
    		lifeLost.remove();
    	}
    }
    
    private void createCats() {
        for(int i = 1; i<=game.catsAmnt; i++) {
        	cat = new Cat(Assets.getCat(i), rand.nextInt(70)+20);
        	cat.setPosition(rand.nextInt(width-width/4), rand.nextInt(height-width/4));
        	cat.addListener(new CatListener());
        	stage.addActor(cat);
        }
       
    }
    
    private void addFish() {
    	float x = rand.nextInt(width-width/5); float y = rand.nextInt(height-width/5-40);
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
    }
	
    private void showPause() {
			pauseBg = new TextureActor(new TextureRegion(AcidCat.manager.get(Assets.txrPauseBg, Texture.class)));
		pauseBg.setPosition(0, 0);
		pauseBg.setSize(width, height);
		stage.addActor(pauseBg);
		
	    Skin skin = new Skin();
	    skin.addRegions(Assets.atlas);
	    
	    TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = bigFont;
        textButtonStyle.up = skin.getDrawable("dialog_btn");
        textButtonStyle.down = skin.getDrawable("dialog_btn");
        textButtonStyle.checked = skin.getDrawable("dialog_btn");
        
        final TextButton button = new TextButton("RESUME", textButtonStyle);
        float x = (width - button.getWidth())/2; float y = (height - button.getHeight())/2;
        button.setPosition(x, y);
        button.addListener(new ClickListener() {
			@Override
	        public void clicked(InputEvent event, float x, float y) {
	        	button.remove();
	        	pauseBg.remove();
	        	state = State.READY; }});
        stage.addActor(button);
	    
    }
    
	private void showDialog() {
		
		game.lives = 0;
		dialog = new Group();
		int[] price = new int[] { 50, 90, 130};
		
		// PASSIVE
		
		
		// background
		
		dialog_back.setPosition(0, 0);
		dialog_back.setSize(width, height);
		dialog.addActor(dialog_back);
		
		float dialogSize = width/15;
		dialog_bg.setPosition(dialogSize, (height-width+(dialogSize)*2)/2);
		dialog_bg.setSize(width-(dialogSize)*2, width-(dialogSize)*2);
		dialog.addActor(dialog_bg);
		
		// button

		dialog_btn.setSize(width-dialog_bg.getWidth()/2, width/8);
		dialog_btn.setPosition(dialog_bg.getX()*3, dialog_bg.getY()+dialog_btn.getHeight()/2);
		dialog_btn.addListener(new GOListener());
		dialog.addActor(dialog_btn);
		 
		Label.LabelStyle dialogStyle = new Label.LabelStyle();
		dialogStyle.font = font;
		dialogStyle.fontColor = Color.BLACK;	
		
		
		// buttons with lives' price text
		 
		int j = 2;
		for(int i = 0; i <= 2; i++) {
			TextureActor addButton = new TextureActor(new TextureRegion(
					AcidCat.manager.get(Assets.txrDialogBtn, Texture.class)));
			addButton.setSize(width-(width/12)*2, dialog_btn.getHeight());
			addButton.setPosition((width-addButton.getWidth())/2, dialog_btn.getTop()+dialog_btn.getHeight()/2+dialog_btn.getHeight()/10*i+addButton.getHeight()*i);
			dialog.addActor(addButton);
			
			Label buttonText = new Label(""+price[j], dialogStyle);
			buttonText.setPosition(addButton.getRight()-buttonText.getWidth()-50, (addButton.getTop()+addButton.getY())/2 - buttonText.getHeight()/2);
			dialog.addActor(buttonText);
			
			j--;
		}
		
		// pictures
		
		
		
		// ACTIVE
		
		// "no" button		
		
		dialog_btnTransp.setPosition(dialog_btn.getX(), dialog_btn.getY());
		dialog_btnTransp.setSize(dialog_btn.getWidth(), dialog_btn.getHeight());
		dialog_btnTransp.addListener(new GOListener());
		dialog.addActor(dialog_btnTransp);
		
		// add-life buttons
		j = 2;
		for (int i = 0; i <= 2; i++) {
			addLifePic(i, j-1);
			if (Settings.scores[3] >= price[j]) activeButton(i, j);
			else passiveButton(i);
			j--;
		}
		
		stage.addActor(dialog);

		if(AcidCat.rewardedAfter == 0) {
			AcidCat.rewardedAfter = 10;
			AcidCat.googleServices.showRewarded();
		}

		dialogExist = true;
	}
	
	private void activeButton(int i, int j) {
		final int addLives = j+1;
		TextureActor addButton = new TextureActor(new TextureRegion(
				AcidCat.manager.get(Assets.txrTranspBtn, Texture.class)));
		addButton.setSize(width-(width/12)*2, dialog_btn.getHeight());
		addButton.setPosition((width-addButton.getWidth())/2, dialog_btn.getTop()+dialog_btn.getHeight()/2+dialog_btn.getHeight()/10*i+addButton.getHeight()*i);
		addButton.addListener(new ClickListener() {
			@Override
	        public void clicked(InputEvent event, float x, float y) {
	        	game.lives += addLives;
	        	switch(addLives) {
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
	        	state = State.READY;
	        	dialogExist = false;
			}
		});
		dialog.addActor(addButton);
	}
	
	private void passiveButton(int i) {
		TextureActor addButton = new TextureActor(new TextureRegion(
				AcidCat.manager.get(Assets.txrDialogBtnPassive, Texture.class)));
		addButton.setSize(width-(width/12)*2, dialog_btn.getHeight());
		addButton.setPosition((width-addButton.getWidth())/2, dialog_btn.getTop()+dialog_btn.getHeight()/2+dialog_btn.getHeight()/10*i+addButton.getHeight()*i);
		dialog.addActor(addButton);
	}
	
	private void addLifePic(int i, int j) {
		for (int k = 0; k <= j+1; k++) {
		TextureActor
				addPic = new TextureActor(new TextureRegion(AcidCat.manager.get(Assets.txrLife, Texture.class)));
		addPic.setSize(dialog_btn.getHeight(), dialog_btn.getHeight());
		addPic.setPosition(dialog_bg.getX()*2 + addPic.getWidth()*k, dialog_btn.getTop()+dialog_btn.getHeight()/2+dialog_btn.getHeight()/10*i+addPic.getHeight()*i);
		dialog.addActor(addPic);
		}
		
	}
	
	
    @Override
    public void resize(int width, int height) {}

    @Override
    public void show() {
        
        InputProcessor backProcessor = new InputAdapter() {
        	@Override
        	public boolean keyDown(int keycode) {
        		
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
    
    @Override
    public void hide() { }
    
    @Override
    public void pause() { 
    	state = State.PAUSE;
    	showPause();
    	}

    @Override
    public void resume() {}
    
    private class GOListener extends ClickListener {
        @Override
        public void clicked(InputEvent event, float x, float y) {
        	gameOver();
        }
    }

    private class CatListener extends InputListener {
		@Override
		public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
			if (pointer == 0) {
				if (game.lives == 0) state = State.DIALOG;
				else {
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
        @Override
        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
        	state = State.PAUSE;
        	showPause();
        }
    }

	@Override
	public void dispose() {
		super.dispose();
		stage.dispose();

    renBatch.dispose();
    dialogBatch.dispose();

    font.dispose();
    smallFont.dispose();
    bigFont.dispose();

	}
}