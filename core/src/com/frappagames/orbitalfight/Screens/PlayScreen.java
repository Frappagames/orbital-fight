package com.frappagames.orbitalfight.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Circle;
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
    private static final int STAR_RADIUS = 75;

    private OrthographicCamera camera;
    private Viewport   viewport;
    private Player     player1, player2;
    private GameHud    hud;

    private Double energy = 100d;
    private int rocket = 5;
    private long lastShoot = 0;

    private ParticleEffect starsEffect;
    private Circle starBounds;

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

        // Special effect showing flashing stars
        starsEffect = new ParticleEffect();
        starsEffect.load(Gdx.files.internal("stars-effect.fx"), Gdx.files.internal(""));
        starsEffect.setPosition(0, 0);

        starBounds = new Circle(0, 0, STAR_RADIUS);
    }

    @Override
    public void show() {
        background = new Texture(Gdx.files.internal("background4.jpg"));
        star = new Texture(Gdx.files.internal("star3.png"));
        player1 = new Player(0);
        player2 = new Player(1);
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
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player1.turn(-delta);
        }

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
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player2.turn(-delta);
        }

        if (energy < 100) {
            energy += delta * ENERGY_RATE;
            hud.setPlayerCurrentEnergy(energy.intValue());
        }

        player1.update(delta);
        player2.update(delta);

        if (player1.getShipStatus() != Player.Status.EXPLODING
                && player1.getBounds().overlaps(starBounds)) {
            player1.setShipStatus(Player.Status.EXPLODING);
            System.out.println("BOOM le Player 1 !!!");
        }

        if (player2.getShipStatus() != Player.Status.EXPLODING
                && player2.getBounds().overlaps(starBounds)) {
            player2.setShipStatus(Player.Status.EXPLODING);
            System.out.println("BOOM le Player 2 !!!");
        }

        if (player1.getShipStatus() != Player.Status.EXPLODING
                && player2.getShipStatus() != Player.Status.EXPLODING
                && player1.getBounds().overlaps(player2.getBounds())) {
            player1.setShipStatus(Player.Status.EXPLODING);
            player2.setShipStatus(Player.Status.EXPLODING);
            System.out.println("BOOM MEGA-BOOM les 2 !!!");
        }
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
        starsEffect.draw(game.batch, delta);
        player1.draw(game.batch);
        player2.draw(game.batch);
        game.batch.draw(star, -star.getWidth() / 2, -star.getHeight() / 2);
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
        player1.dispose();
        player2.dispose();
        starsEffect.dispose();
    }
}
