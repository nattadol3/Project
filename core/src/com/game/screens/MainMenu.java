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

public class MainMenu implements Screen {
	enum Operation {
		NEWGAME, LOADGAME, OPTION, EXIT
	}

	private Game game2;

	private String[] mainmenubg;
	private int selectedOrder = 0;
	private int currentValue = 0;
	private Operation currentOperation = null;
	private int result = 0;
	Texture img;
	private final float WORLD_WIDTH = 1000f;
	private final float WORLD_HEIGHT = 720f;
	private Music music;
	// Camera and viewport
	private OrthographicCamera camera;
	private Viewport viewport;

	// Aspect Ratio
	private float aspectRatio;
	
	private Sound hoverSound, confirmSound;

	public MainMenu(Game game) {

		game2 = game;
		
		music = Gdx.audio.newMusic(Gdx.files.internal("mainmenusong.wav"));
		
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
				selectedOrder = mainmenubg.length - 1;
			}
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			selectedOrder++;
			hoverSound.play();
			if (selectedOrder >= mainmenubg.length) {
				selectedOrder = 0;
			}
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			if (selectedOrder == 0) {
				currentOperation = Operation.NEWGAME;
			} else if (selectedOrder == 1) {
				currentOperation = Operation.LOADGAME;
			} else if (selectedOrder == 2) {
				currentOperation = Operation.OPTION;
			} else if (selectedOrder == 3) {
				currentOperation = Operation.EXIT;
			}
			if (currentOperation == Operation.NEWGAME) {
				handleNewgame();
			} else if (currentOperation == Operation.LOADGAME) {
				handleLoadgame();
			} else if (currentOperation == Operation.OPTION) {
				handleOption();
			} else if (currentOperation == Operation.EXIT) {
				handleExit();
			}

			// Reset the current value and operation
			currentValue = 0;
			currentOperation = null;
		}

	}

	public void handleNewgame() {
		game2.setScreen(new GameScreen(game2));
		confirmSound.play();
		music.stop();
	}

	public void handleLoadgame() {
		confirmSound.play();
	}

	public void handleOption() {
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
		music.setVolume(0.5f);
		music.setLooping(true);
		music.play();
		mainmenubg = new String[4];
		mainmenubg[0] = new String("newgame.PNG");
		mainmenubg[1] = new String("loadgame.PNG");
		mainmenubg[2] = new String("option.PNG");
		mainmenubg[3] = new String("exit.PNG");
		detectmenuinput();
		img = new Texture(mainmenubg[selectedOrder]);
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
		music.dispose();
		hoverSound.dispose();
		confirmSound.dispose();
		img.dispose();
	}	
}