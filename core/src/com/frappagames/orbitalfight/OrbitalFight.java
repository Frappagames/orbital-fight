package com.frappagames.orbitalfight;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.frappagames.orbitalfight.Screens.PlayScreen;

public class OrbitalFight extends Game {
	public static final String GAME_TITLE = "Orbital fight";
	public static final int GAME_WIDTH    = 1280;
	public static final int GAME_HEIGHT   = 800;

//	private static final int STAR_COUNT   = 4;

	public SpriteBatch batch;
//	private Animation<TextureRegion> starAnimation;
//	private float gateTime = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new PlayScreen(this));

//		// Animation de l'étoile
//		Texture           starSheet  = new Texture(Gdx.files.internal("etoile.png"));
//		TextureRegion[][] tmp        = TextureRegion.split(starSheet, starSheet.getWidth() / STAR_COUNT, starSheet.getHeight() / STAR_COUNT);
//		TextureRegion[]   coinFrames = new TextureRegion[STAR_COUNT];
//		int               index      = 0;
//		for (int i = 0; i < STAR_COUNT; i++) {
//			coinFrames[index++] = tmp[3][i];
//		}
//		starAnimation = new Animation<TextureRegion>(0.1f, coinFrames);
	}

	@Override
	public void render () {

//		gateTime += Gdx.graphics.getDeltaTime();
//		batch.draw(starAnimation.getKeyFrame(gateTime, true), 512, 272);

		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
