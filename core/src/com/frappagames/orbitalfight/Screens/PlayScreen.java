package com.frappagames.orbitalfight.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.frappagames.orbitalfight.Actors.Player;
import com.frappagames.orbitalfight.OrbitalFight;
import com.frappagames.orbitalfight.Utils.GameCamera;
import com.frappagames.orbitalfight.Utils.GameHud;

public class PlayScreen implements Screen {
    private Texture background, star;
    private OrbitalFight    game;

    private static final int ENERGY_RATE = 10;
    private static final int SHOOTING_RATE = 300;

    private OrthographicCamera camera;
    private Viewport   viewport;
    private Player     player;
    private GameHud    hud;

    private Double energy = 100d;
    private int rocket = 5;
    private long lastShoot = 0;

    public PlayScreen(OrbitalFight game) {
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new FitViewport(OrbitalFight.GAME_WIDTH, OrbitalFight.GAME_HEIGHT, camera);
        viewport.apply();

        hud = new GameHud(game.batch);
        hud.setPlayerMaxLife(100);
        hud.setPlayerMaxShield(100);
        hud.setPlayerMaxFuel(100);
        hud.setPlayerMaxEnergy(energy.intValue());
        hud.setPlayerMaxRocket(rocket);
    }

    @Override
    public void show() {
        background = new Texture(Gdx.files.internal("background4.jpg"));
        star = new Texture(Gdx.files.internal("star3.png"));
        player = new Player();
    }

    private void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            long now = TimeUtils.millis();

            if (energy >= 10 && now > lastShoot + SHOOTING_RATE) {
                System.out.println("Tir de laser");
                energy -= 10;
                hud.setPlayerCurrentEnergy(energy.intValue());
                lastShoot = TimeUtils.millis();
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (rocket >= 1) {
                System.out.println("Tir de rocket");
                rocket--;
                hud.setPlayerCurrentRocket(rocket);
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.forward(delta);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.turn(delta);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.turn(-delta);
        }

        if (energy < 100) {
            energy += delta * ENERGY_RATE;
            hud.setPlayerCurrentEnergy(energy.intValue());
        }

        player.update(delta);
    }

    @Override
    public void render(float delta) {
        this.update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(background, -background.getWidth() / 2, -background.getHeight() / 2);
        game.batch.draw(star, -star.getWidth() / 2, -star.getHeight() / 2);
        player.draw(game.batch, delta);
        game.batch.end();

        // Show HUD
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.act();
        hud.stage.draw();
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
        background.dispose();
        player.dispose();
    }
}
