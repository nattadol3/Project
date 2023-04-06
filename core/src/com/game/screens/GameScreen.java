package com.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.characters.Player;
import com.game.characters.Player.State;
import com.game.project.project;

public class GameScreen implements Screen {
	// World limit
	private final float WORLD_WIDTH = 480f;
	private final float WORLD_HEIGHT = 480f;
			
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
	
	// Boundaries check
	private float boundX, boundY;
	
	public GameScreen(Game game) {
		// Calculating aspect ratio
		aspectRatio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
				
		// Creating camera and viewport
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(VIEWPORT_WIDTH * aspectRatio, VIEWPORT_HEIGHT, camera);
		
		// Load map
		TmxMapLoader loader = new TmxMapLoader();
						
		map = loader.load("Map/testmap01.tmx");
						
		// Add it to the renderer
		renderer = new OrthogonalTiledMapRenderer(map);
		
		player = new Player(100, 128, new Texture("Player Sprite/Back Movement.png"), new Texture("Player Sprite/Front Movement.png"), 
						new Texture("Player Sprite/Side Movement Left.png"), new Texture("Player Sprite/Side Movement Right.png"), 
						new Texture("Player Sprite/Back ConsecutiveSlash.png"), new Texture("Player Sprite/Front ConsecutiveSlash.png"), 
						new Texture("Player Sprite/Right ConsecutiveSlash.png"), new Texture("Player Sprite/Left ConsecutiveSlash.png"));
		
		player.setSize(64f, 64f);
		
		player.setBounds(player.getX(), player.getY(), 64f, 64f);
			
		player.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
			
		camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);	
		
		player.setCurrentState(State.IDLE_DOWN);
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
		
				
		boundX = MathUtils.clamp(player.getX(), player.getWidth() / 2, WORLD_WIDTH  - player.getWidth());
		boundY = MathUtils.clamp(player.getY(), player.getHeight() / 2, WORLD_HEIGHT  - player.getHeight());

		player.setPosition(boundX, boundY);

		camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);

		camera.position.x = MathUtils.clamp(camera.position.x, camera.viewportWidth / 2, WORLD_WIDTH - camera.viewportWidth / 2);
		camera.position.y = MathUtils.clamp(camera.position.y, camera.viewportHeight / 2, WORLD_HEIGHT - camera.viewportHeight / 2);
									
		
		project.batch.begin();		
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
