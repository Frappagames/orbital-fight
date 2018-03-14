package com.frappagames.orbitalfight.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.frappagames.orbitalfight.Actors.Player;
import com.frappagames.orbitalfight.OrbitalFight;
import com.frappagames.orbitalfight.Utils.GameCamera;
import com.frappagames.orbitalfight.Utils.GameHud;

public class PlayScreen implements Screen {
    private Texture background;
    private OrbitalFight    game;

    private static final int ENERGY_RATE = 10;
    private static final int SHOOTING_RATE = 300;

    private World              world;
    private Box2DDebugRenderer debugRenderer;
    private GameCamera camera;
    private Player player;
    private GameHud hud;

    private Double energy = 100d;
    private int rocket = 5;
    private long lastShoot = 0;

    public PlayScreen(OrbitalFight game) {
        this.game = game;

        hud = new GameHud(game.batch);
        hud.setPlayerMaxLife(100);
        hud.setPlayerMaxShield(100);
        hud.setPlayerMaxFuel(100);
        hud.setPlayerMaxEnergy(energy.intValue());
        hud.setPlayerMaxRocket(rocket);
    }

    @Override
    public void show() {
        background = new Texture(Gdx.files.internal("background.jpg"));

        /* BOX 2D */
        Box2D.init();
        world = new World(new Vector2(0, 0), true);
        debugRenderer = new Box2DDebugRenderer();

        player = new Player(world);
    }

    private void update(float delta) {
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

        if (energy < 100) {
            energy += delta * ENERGY_RATE;
            hud.setPlayerCurrentEnergy(energy.intValue());
        }
    }

    @Override
    public void render(float delta) {
        this.update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.act();
        hud.stage.draw();

        game.batch.begin();
        debugRenderer.render(world, camera.combined);
//		game.batch.draw(background, 0, 0);
        game.batch.end();

        world.step(delta, 6, 2);
    }

    @Override
    public void resize(int width, int height) {
        camera.resize(width, height);
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
