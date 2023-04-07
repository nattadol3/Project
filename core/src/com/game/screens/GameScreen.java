package com.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.characters.Chalie;
import com.game.characters.Player;
import com.game.characters.Player.State;
import com.game.project.project;

public class GameScreen implements Screen {
	
	//
	private Game game3;
	
	//
	private MainMenu mainMenu;
	
	//
	private Sound chalieDeath;
	
	// World limit
	private final float WORLD_WIDTH = 480f;
	private final float WORLD_HEIGHT = 640f;
			
	// viewport limit
	private final float VIEWPORT_WIDTH = 240f;
	private final float VIEWPORT_HEIGHT = 240f;
			
	// Camera and viewport
	private OrthographicCamera camera;
	private Viewport viewport;
			
	// Aspect ratio
	private float aspectRatio;
			
	// Tiled map
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
		
	// Player
	private Player player;
	
	// Chalie
	private Chalie ch1;
	
	// Boundaries check
	private float boundX, boundY;
	
	//
	private Rectangle ch1Rect, playerRect;
	
	public GameScreen(Game game) {
		//
		game3 = game;
		
		//
		chalieDeath = Gdx.audio.newSound(Gdx.files.internal("SFX/24_orc_death_spin.wav"));
		
		// Calculating aspect ratio
		aspectRatio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
				
		// Creating camera and viewport
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(VIEWPORT_WIDTH * aspectRatio, VIEWPORT_HEIGHT, camera);
		
		// Load map
		TmxMapLoader loader = new TmxMapLoader();
						
		map = loader.load("Demomap.tmx");
						
		// Add it to the renderer
		renderer = new OrthogonalTiledMapRenderer(map);
		
		player = new Player(100, 128, 25, new Texture("Player Sprite/Back Movement.png"), new Texture("Player Sprite/Front Movement.png"), 
						new Texture("Player Sprite/Side Movement Left.png"), new Texture("Player Sprite/Side Movement Right.png"), 
						new Texture("Player Sprite/Back ConsecutiveSlash.png"), new Texture("Player Sprite/Front ConsecutiveSlash.png"), 
						new Texture("Player Sprite/Left ConsecutiveSlash.png"), new Texture("Player Sprite/Right ConsecutiveSlash.png"));
		
		player.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
			
		camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);	
		
		player.setCurrentState(State.IDLE_DOWN);
		
		ch1 = new Chalie(7500, 25, 24, new Texture("Chalie/charlieTheCapybaraAnimationSheet Right.png"), 
					new Texture("Chalie/charlieTheCapybaraAnimationSheet Left.png"));
		
		ch1.setSize(32f, 32f);
		
		ch1.setPosition(210f, 500f);
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		// clear the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
								
		// update the camera
		camera.update();
								
		// Render the map
		renderer.setView(camera);
		renderer.render();
		
		if (player.isAttacking) {
		    player.setBounds(player.getX(), player.getY(), 40f, 40f); // set the size to 64x64 when attacking
		} else {
		    player.setBounds(player.getX(), player.getY(), 32f, 32f); // set the size to 32x32 when not attacking
		}
	
		ch1.setBounds(ch1.getX(), ch1.getY(), 32f, 32f);
		
		ch1Rect = ch1.getBoundingRectangle();
		
		playerRect = player.getBoundingRectangle();

		boundX = MathUtils.clamp(player.getX(), player.getWidth() / 2, WORLD_WIDTH  - player.getWidth());
		boundY = MathUtils.clamp(player.getY(), player.getHeight() / 2, WORLD_HEIGHT  - player.getHeight());

		player.setPosition(boundX, boundY);

		camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);

		camera.position.x = MathUtils.clamp(camera.position.x, camera.viewportWidth / 2, WORLD_WIDTH - camera.viewportWidth / 2);
		camera.position.y = MathUtils.clamp(camera.position.y, camera.viewportHeight / 2, WORLD_HEIGHT - camera.viewportHeight / 2);
		
		project.batch.begin();	
		 if (ch1Rect.overlaps(playerRect) && ch1.isMunching) {
			player.getHit(ch1.getDamage());
			if (player.getHp() <= 0) {
				game3.setScreen(project.mainMenu);
			}
		} else if (ch1Rect.overlaps(playerRect) && player.isAttacking && !ch1.isMunching) {
			ch1.getHit(player.getDamage());
			if (ch1.getHp() <= 0) {
				ch1.died();
				chalieDeath.play(0.5f);
			}
		} else if (ch1Rect.overlaps(playerRect) && !player.isAttacking && !ch1.isMunching) {
			if (player.currentState == State.WALK_UP) {
				if (player.getY() < ch1.getY()) {
			        // The player is above ch1, so move the player down
			        player.setY(ch1.getY() - ch1.getHeight());
				}
			} else if (player.currentState == State.WALK_LEFT || player.currentState == State.WALK_LEFT_UP || player.currentState == State.WALK_LEFT_UP) {
				if (player.getX() > ch1.getX()) {
			        // The player is above ch1, so move the player down
			        player.setX(ch1.getX() + ch1.getWidth());
				}
			} else if (player.currentState == State.WALK_RIGHT || player.currentState == State.WALK_RIGHT_UP || player.currentState == State.WALK_RIGHT_UP) {
				if (player.getX() < ch1.getX()) {
			        // The player is above ch1, so move the player down
					player.setX(ch1.getX() - ch1.getWidth());
				}
			} else if (player.currentState == State.WALK_DOWN) {
				if (player.getY() > ch1.getY()) {
			        // The player is above ch1, so move the player down
					player.setY(ch1.getY() + ch1.getHeight());
				}
			}
		}
		
		ch1.update(delta);
		ch1.draw();
		player.update(delta);
		player.draw();
		project.batch.setProjectionMatrix(camera.combined);
		project.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		project.batch.setProjectionMatrix(camera.combined);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
