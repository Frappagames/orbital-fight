package com.frappagames.orbitalfight.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.frappagames.orbitalfight.Actors.Asteroid;
import com.frappagames.orbitalfight.Actors.Player;
import com.frappagames.orbitalfight.Actors.Sun;
import com.frappagames.orbitalfight.OrbitalFight;
import com.frappagames.orbitalfight.Utils.Assets;
import com.frappagames.orbitalfight.Utils.GameHud;

public class PlayScreen implements Screen {
    private OrbitalFight    game;
    private OrthographicCamera camera;
    private Viewport   viewport;
    private Player     player1, player2;
    private GameHud    hud;

    private int rocket = 5;

    private ParticleEffect starsEffect;
    private Sun            sun;

    private Asteroid asteroid1, asteroid2;

    public PlayScreen(OrbitalFight game) {
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new FitViewport(OrbitalFight.GAME_WIDTH, OrbitalFight.GAME_HEIGHT, camera);
        viewport.apply();

        // Special effect showing flashing stars
        starsEffect = new ParticleEffect();
        starsEffect.load(Gdx.files.internal("stars-effect.fx"), Gdx.files.internal(""));
        starsEffect.setPosition(0, 0);
        starsEffect.getEmitters().first().getSprite().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        sun = new Sun();
        asteroid1 = new Asteroid(1);
        asteroid2 = new Asteroid(2);
    }

    @Override
    public void show() {
        player1 = new Player(0);
        player2 = new Player(1);
        hud = new GameHud(game.batch, player1, player2);
    }

    private void update(float delta) {
        // Sortie du jeu
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        //****************************************************************************************//
        //                                     CONTRÔLE DES TIRS                                  //
        //****************************************************************************************//
        // Joueur 1 : Lasers
        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT)) {
            player1.laserShot();
        }

        // Joueur 1 : Rockettes
        if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_RIGHT)) {
            if (rocket >= 1) {
                System.out.println("Joueur 1 : Tir de rocket");
                rocket--;
            }
        }

        // Joueur 2 : Lasers
        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            player2.laserShot();
        }

        // Joueur 2 : Rockettes
        if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) {
            if (rocket >= 1) {
                System.out.println("Joueur 2 : Tir de rocket");
                rocket--;
            }
        }

        //****************************************************************************************//
        //                                CONTRÔLE DES DÉPLACEMENTS                               //
        //****************************************************************************************//
        // Joueur 1
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player1.setShipStatus(Player.Status.MOVING);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player1.forward(delta);
        } else {
            player1.setShipStatus(Player.Status.IDLE);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player1.turn(delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player1.turn(-delta);
        }

        // Joueur 2
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            player2.setShipStatus(Player.Status.MOVING);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            player2.forward(delta);
        } else {
            player2.setShipStatus(Player.Status.IDLE);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            player2.turn(delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player2.turn(-delta);
        }


        //****************************************************************************************//
        //                                 CONTRÔLE DES COLLISIONS                                //
        //****************************************************************************************//

        // Check colisions for player 1 (if not already exploding)...
        if (player1.getShipStatus() != Player.Status.EXPLODING) {
            /// With sun
            if (player1.getBounds().overlaps(sun.getBounds())) player1.applyDamages(Integer.MAX_VALUE);

            // With Asteroid 1
            if (asteroid1.getAsteroidStatus() != Asteroid.Status.EXPLODING && player1.getBounds().overlaps(asteroid1.getBounds())) {
                int playerLife   = Double.valueOf(player1.getCurrentLife() + player1.getCurrentShield()).intValue();
                int asteroidLife = Double.valueOf(asteroid1.getCurrentLife()).intValue();
                asteroid1.applyDamages(playerLife);
                player1.applyDamages(asteroidLife);
            }

            // With Asteroid 2
            if (asteroid2.getAsteroidStatus() != Asteroid.Status.EXPLODING && player1.getBounds().overlaps(asteroid2.getBounds())) {
                int playerLife   = Double.valueOf(player1.getCurrentLife() + player1.getCurrentShield()).intValue();
                int asteroidLife = Double.valueOf(asteroid2.getCurrentLife()).intValue();
                asteroid2.applyDamages(playerLife);
                player1.applyDamages(asteroidLife);
            }
        }

        // Check colisions for player 2 (if not already exploding)...
        if (player2.getShipStatus() != Player.Status.EXPLODING) {
            /// With sun
            if (player2.getBounds().overlaps(sun.getBounds())) player2.applyDamages(Integer.MAX_VALUE);

            // With Asteroid 1
            if (asteroid1.getAsteroidStatus() != Asteroid.Status.EXPLODING && player2.getBounds().overlaps(asteroid1.getBounds())) {
                int playerLife   = Double.valueOf(player2.getCurrentLife() + player2.getCurrentShield()).intValue();
                int asteroidLife = Double.valueOf(asteroid1.getCurrentLife()).intValue();
                asteroid1.applyDamages(playerLife);
                player2.applyDamages(asteroidLife);
            }

            // With Asteroid 2
            if (asteroid2.getAsteroidStatus() != Asteroid.Status.EXPLODING && player2.getBounds().overlaps(asteroid2.getBounds())) {
                int playerLife   = Double.valueOf(player2.getCurrentLife() + player2.getCurrentShield()).intValue();
                int asteroidLife = Double.valueOf(asteroid2.getCurrentLife()).intValue();
                asteroid2.applyDamages(playerLife);
                player2.applyDamages(asteroidLife);
            }
        }

        // Check colisions between the two players
        if (player1.getShipStatus() != Player.Status.EXPLODING
                && player2.getShipStatus() != Player.Status.EXPLODING
                && player1.getBounds().overlaps(player2.getBounds())) {
            int player1Life   = Double.valueOf(player1.getCurrentLife() + player1.getCurrentShield()).intValue();
            int player2Life   = Double.valueOf(player2.getCurrentLife() + player2.getCurrentShield()).intValue();
            player1.applyDamages(player2Life);
            player2.applyDamages(player1Life);
        }

        // Check colisions between asteroid1 and sun
        if (asteroid1.getBounds().overlaps(sun.getBounds()) && asteroid1.getAsteroidStatus() != Asteroid.Status.EXPLODING) {
            asteroid1.applyDamages(Integer.MAX_VALUE);
        }

        // Check colisions between asteroid2 and sun
        if (asteroid2.getBounds().overlaps(sun.getBounds()) && asteroid2.getAsteroidStatus() != Asteroid.Status.EXPLODING) {
            asteroid2.applyDamages(Integer.MAX_VALUE);
        }

        // Check colisions between asteroids
        if (asteroid1.getAsteroidStatus() != Asteroid.Status.EXPLODING && asteroid2.getAsteroidStatus() != Asteroid.Status.EXPLODING
                && asteroid1.getBounds().overlaps(asteroid2.getBounds())) {
            int asteroid1Life   = Double.valueOf(asteroid1.getCurrentLife()).intValue();
            int asteroid2Life   = Double.valueOf(asteroid2.getCurrentLife()).intValue();
            asteroid1.applyDamages(asteroid2Life);
            asteroid2.applyDamages(asteroid1Life);
        }

    }

    @Override
    public void render(float delta) {
        this.update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL30.GL_COVERAGE_BUFFER_BIT_NV : 0));

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // === Begin rendering ===
        game.batch.begin();

        // BACKGROUND
        game.batch.draw(Assets.backgroundTexture, -Assets.backgroundTexture.getWidth() / 2, -Assets.backgroundTexture.getHeight() / 2);

        // EFFECTS
        starsEffect.draw(game.batch, delta);

        // PLAYERS
        player1.draw(game.batch);
        player2.draw(game.batch);

        // ASTEROIDS
        asteroid1.draw(game.batch);
        asteroid2.draw(game.batch);

        // SUN
        sun.draw(game.batch);

        // === End rendering ===
        game.batch.end();

        // Show HUD
        hud.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        player1.dispose();
        player2.dispose();
        starsEffect.dispose();
    }
}
