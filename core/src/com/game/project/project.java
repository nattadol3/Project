package com.game.project;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.game.screens.MainMenu;

public class project extends Game {
	public static SpriteBatch batch;
	protected MainMenu mainMenu;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		mainMenu = new MainMenu(this);
		setScreen(mainMenu);
	}

	@Override
	public void dispose() {
		mainMenu.dispose();
	}

	@Override
	public void resize(int width, int height) {
		mainMenu.resize(width, height);
	}
	
	
}
