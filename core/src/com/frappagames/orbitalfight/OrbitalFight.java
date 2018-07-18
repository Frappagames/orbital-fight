package com.frappagames.orbitalfight;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.frappagames.orbitalfight.Screens.PlayScreen;
import com.frappagames.orbitalfight.Utils.Assets;

public class OrbitalFight extends Game {
	public static final String GAME_TITLE = "Orbital fight";
	public static final int GAME_WIDTH    = 1920;
	public static final int GAME_HEIGHT   = 1080;

	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		Assets.load();

		this.setScreen(new PlayScreen(this));
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
