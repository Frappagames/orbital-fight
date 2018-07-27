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
//    public static TextureRegionDrawable title;
    public static Animation shipsAnim1, shipsAnim2, explodeAnimation;

    public static TextureRegion ship1, ship2;

//    public static Sound clickSound, hitSound, pickSound;
//    public static  Music        music;
//    private static TextureAtlas itemsAtlas;

//    public static Label.LabelStyle fontScore;

    public static void load() {
        // Fonts
//        BitmapFont souses20Font = new BitmapFont(Gdx.files.internal("fontScore.fnt"), false);
//        fontScore = new Label.LabelStyle(souses20Font, Color.WHITE);
//
//        // Load Textures
//        itemsAtlas  = new TextureAtlas(Gdx.files.internal("snowflakes.pack"));
//        title       = new TextureRegionDrawable(itemsAtlas.findRegion("title"));

        // Load Music and sounds
        // Music ♫
//        music = Gdx.audio.newMusic(Gdx.files.internal("music.ogg"));
//        music.setLooping(true);
//        music.setVolume(0.5f);

        // Sounds ♪
//        clickSound = Gdx.audio.newSound(Gdx.files.internal("sound-click.mp3"));

        // Ships animation
        Texture sheet = new Texture(Gdx.files.internal("ships.png"));
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


        sheet = new Texture(Gdx.files.internal("explosion1.png"));
        tmp = TextureRegion.split(sheet, sheet.getWidth() / 8, sheet.getHeight() / 4);
        frames = new TextureRegion[32];
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        explodeAnimation = new Animation<TextureRegion>(0.03125f, frames);

    }

    public static void playSound(Sound sound) {
//        if (Settings.soundEnabled) sound.play(1);
    }

    public static void dispose() {
//        itemsAtlas.dispose();
//        clickSound.dispose();
//        pickSound.dispose();
//        hitSound.dispose();
//        music.dispose();
    }
}
