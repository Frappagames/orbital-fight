package com.frappagames.orbitalfight.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Assets management class
 *
 * Created by Jérémy MOREAU on 19/08/15.
 */
public class Assets {
    private static final int ASTEROID1_LINES = 8;
    private static final int ASTEROID1_ROWS = 4;
    private static final int EXPLOSION1_LINES = 8;
    private static final int EXPLOSION1_ROWS = 4;

    public static Texture star, background;
    public static Animation shipsAnim1, shipsAnim2, explodeAnimation, asteroid1Animation, asteroid2Animation;

    public static TextureRegion ship1, ship2;
    public static TextureRegionDrawable player1Stats, player2Stats, lifeSquare, shieldSquare, fuelSquare, emptySquare;

//    public static Sound clickSound, hitSound, pickSound;
//    public static  Music        music;
    private static TextureAtlas itemsAtlas;

//    public static Label.LabelStyle fontScore;

    public static void load() {
        // Fonts
//        BitmapFont souses20Font = new BitmapFont(Gdx.files.internal("fontScore.fnt"), false);
//        fontScore = new Label.LabelStyle(souses20Font, Color.WHITE);
//
        // Load Textures
        itemsAtlas  = new TextureAtlas(Gdx.files.internal("orbital-fight.pack"));
//        title       = new TextureRegionDrawable(itemsAtlas.findRegion("title"));
        player1Stats = new TextureRegionDrawable(itemsAtlas.findRegion("player1-stats"));
        player2Stats = new TextureRegionDrawable(itemsAtlas.findRegion("player2-stats"));
        lifeSquare = new TextureRegionDrawable(itemsAtlas.findRegion("life-square"));
        shieldSquare = new TextureRegionDrawable(itemsAtlas.findRegion("shield-square"));
        fuelSquare = new TextureRegionDrawable(itemsAtlas.findRegion("fuel-square"));
        emptySquare = new TextureRegionDrawable(itemsAtlas.findRegion("empty-square"));


        star = new Texture(Gdx.files.internal("star3.png"));
        background = new Texture(Gdx.files.internal("background4.jpg"));

        // Load Music and sounds
        // Music ♫
//        music = Gdx.audio.newMusic(Gdx.files.internal("music.ogg"));
//        music.setLooping(true);
//        music.setVolume(0.5f);

        // Sounds ♪
//        clickSound = Gdx.audio.newSound(Gdx.files.internal("sound-click.mp3"));


        // ANIMATIONS
        Texture sheet;

        // Ships animation
        sheet = new Texture(Gdx.files.internal("ships.png"));
        TextureRegion[][] tmp    = TextureRegion.split(sheet, sheet.getWidth() / 3, sheet.getHeight() / 2);

        ship1 = tmp[0][0];
        ship2 = tmp[1][0];

        TextureRegion[]   frames = new TextureRegion[2];
        frames[0] = tmp[0][1];
        frames[1] = tmp[0][2];
        shipsAnim1 = new Animation<TextureRegion>(0.05f, frames);

        frames = new TextureRegion[2];
        frames[0] = tmp[1][1];
        frames[1] = tmp[1][2];
        shipsAnim2 = new Animation<TextureRegion>(0.05f, frames);

        // Ship explosion animation
        sheet = new Texture(Gdx.files.internal("explosion1.png"));
        tmp = TextureRegion.split(sheet, sheet.getWidth() / EXPLOSION1_LINES, sheet.getHeight() / EXPLOSION1_ROWS);
        frames = new TextureRegion[EXPLOSION1_ROWS * EXPLOSION1_LINES];
        int index = 0;
        for (int i = 0; i < EXPLOSION1_ROWS; i++) {
            for (int j = 0; j < EXPLOSION1_LINES; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        explodeAnimation = new Animation<TextureRegion>(0.03125f, frames);

        // Asteroid animation
        sheet = new Texture(Gdx.files.internal("asteroid1-1.png"));
        tmp = TextureRegion.split(sheet, sheet.getWidth() / ASTEROID1_LINES, sheet.getHeight() / ASTEROID1_ROWS);
        frames = new TextureRegion[ASTEROID1_ROWS * ASTEROID1_LINES];
        index = 0;
        for (int i = 0; i < ASTEROID1_ROWS; i++) {
            for (int j = 0; j < ASTEROID1_LINES; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        asteroid1Animation = new Animation<TextureRegion>(0.0625f, frames);

        // Asteroid animation
        sheet = new Texture(Gdx.files.internal("asteroid1-2.png"));
        tmp = TextureRegion.split(sheet, sheet.getWidth() / ASTEROID1_LINES, sheet.getHeight() / ASTEROID1_ROWS);
        frames = new TextureRegion[ASTEROID1_ROWS * ASTEROID1_LINES];
        index = 0;
        for (int i = 0; i < ASTEROID1_ROWS; i++) {
            for (int j = 0; j < ASTEROID1_LINES; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        asteroid2Animation = new Animation<TextureRegion>(0.0625f, frames);
    }

    public static void playSound(Sound sound) {
//        if (Settings.soundEnabled) sound.play(1);
    }

    public static void dispose() {
        star.dispose();
        background.dispose();
//        itemsAtlas.dispose();
//        clickSound.dispose();
//        pickSound.dispose();
//        hitSound.dispose();
//        music.dispose();
    }
}
