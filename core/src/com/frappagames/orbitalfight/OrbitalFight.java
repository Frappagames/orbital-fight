package com.frappagames.orbitalfight;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OrbitalFight extends Game {
	public static final String GAME_TITLE = "Orbital fight";
	public static final int GAME_WIDTH    = 1280;
	public static final int GAME_HEIGHT   = 800;

	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
