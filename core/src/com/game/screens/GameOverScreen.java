package com.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.project.project;

public class GameOverScreen implements Screen {
	enum Operation {
		CONTINUE, MAINMENU, EXIT
	}

	private Game game2;

	private String[] GameOverbg;
	private int selectedOrder = 0;
	private int currentValue = 0;
	private Operation currentOperation = null;
	private int result = 0;
	Texture img;
	private final float WORLD_WIDTH = 1200f;
	private final float WORLD_HEIGHT = 920f;
	// Camera and viewport
	private OrthographicCamera camera;
	private Viewport viewport;

	// Aspect Ratio
	private float aspectRatio;
	
	private Sound hoverSound, confirmSound;

	public GameOverScreen(Game game) {

		game2 = game;
		
		
		
		hoverSound = Gdx.audio.newSound(Gdx.files.internal("SFX/001_Hover_01.wav"));
		
		confirmSound = Gdx.audio.newSound(Gdx.files.internal("SFX/013_Confirm_03.wav"));
		
		// Calculating aspect ratio
		aspectRatio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();

		// Creating camera and viewport
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(WORLD_WIDTH * aspectRatio, WORLD_HEIGHT, camera);

		// Set the camera position to the center of the screen
		camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);

	}

	public void detectmenuinput() {

		if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			selectedOrder--;
			hoverSound.play();
			if (selectedOrder < 0) {
				selectedOrder = GameOverbg.length - 1;
			}
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			selectedOrder++;
			hoverSound.play();
			if (selectedOrder >= GameOverbg.length) {
				selectedOrder = 0;
			}
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			if (selectedOrder == 0) {
				currentOperation = Operation.CONTINUE;
			} else if (selectedOrder == 1) {
				currentOperation = Operation.MAINMENU;
			} else if (selectedOrder == 2) {
				currentOperation =  Operation.EXIT;
			} 
			if (currentOperation == Operation.CONTINUE) {
				handleContinue();
			} else if (currentOperation == Operation.MAINMENU) {
				handleMAINMENU();
			} else if (currentOperation == Operation.EXIT) {
				handleExit();
			}

			// Reset the current value and operation
			currentValue = 0;
			currentOperation = null;
		}

	}

	public void handleContinue() {
		game2.setScreen(new GameScreen(game2));
		confirmSound.play();
	}

	public void handleMAINMENU() {
        game2.setScreen(new MainMenu(game2));
		confirmSound.play();
	}

	public void handleExit() {
		Gdx.app.exit();
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		project.batch.setProjectionMatrix(camera.combined);
		GameOverbg = new String[3];
		GameOverbg[0] = new String("gameovercon.PNG");
		GameOverbg[1] = new String("gameovermain.PNG");
		GameOverbg[2] = new String("gameoverexit.PNG");
		
		detectmenuinput();
		img = new Texture(GameOverbg[selectedOrder]);
		project.batch.begin();
		project.batch.draw(img, 0, 0);
		project.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		project.batch.setProjectionMatrix(camera.combined);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		project.batch.dispose();
		
		hoverSound.dispose();
		confirmSound.dispose();
		img.dispose();
	}	
}