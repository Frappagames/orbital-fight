package com.frappagames.orbitalfight.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.frappagames.orbitalfight.OrbitalFight;


public class Player {
    private static final int ROTATION_SPEED = 270;
    private static final int MAX_SPEED = 400;
    private static final int ACCELERATION_RATE = 700;
    private static final int GRAVITY = 150;
    private static final int SHIP_WIDTH = 64;
    private static final int SHIP_HEIGHT = 57;
    private static final int BOUNDS_RADIUS = 22;

    private Float angle;
    private Circle bounds;
    private TextureRegion texture;
    private Vector2 position, velocity, acceleration;

    public Player(int x, int y, int shipColor) {
        angle        = 0f;
        position     = new Vector2(x, y);
        velocity     = new Vector2(0, 0);
        acceleration = new Vector2(0, ACCELERATION_RATE);
        texture      = new TextureRegion(new Texture(Gdx.files.internal("ships.png")), 0, shipColor * SHIP_HEIGHT, SHIP_WIDTH, SHIP_HEIGHT);
        bounds       = new Circle(x, y, BOUNDS_RADIUS);
    }

    public void dispose() {
    }

    public void forward(float delta) {
        acceleration = new Vector2(0, ACCELERATION_RATE).scl(delta).rotate(angle);
        velocity.add(acceleration);

        if (velocity.x > MAX_SPEED) {
            velocity.x = MAX_SPEED;
        } else if (velocity.x < -MAX_SPEED) {
            velocity.x = -MAX_SPEED;
        }

        if (velocity.y > MAX_SPEED) {
            velocity.y = MAX_SPEED;
        } else if (velocity.y < -MAX_SPEED) {
            velocity.y = -MAX_SPEED;
        }
    }

    public void turn(float delta) {
        angle += delta * ROTATION_SPEED;
        if (angle > 360) angle = angle - 360;
        if (angle < 0) angle = 360 + angle;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(
            texture,
            position.x - SHIP_WIDTH / 2,
            position.y - SHIP_HEIGHT / 2,
            SHIP_WIDTH / 2,
            SHIP_HEIGHT / 2,
            SHIP_WIDTH,
            SHIP_HEIGHT,
            1,
            1,
            angle
        );
    }

    public void update(float delta) {
//        float distance = position.dst(0, 0);
        float angle = position.angle();
        Vector2 gravity = new Vector2(
            MathUtils.cos(angle * MathUtils.degreesToRadians) * GRAVITY,
            MathUtils.sin(angle * MathUtils.degreesToRadians) * GRAVITY
        ).scl(-delta);
        velocity.add(gravity);

        Vector2 change = new Vector2(velocity).scl(delta);
        position.add(change);

        if (position.x > OrbitalFight.GAME_WIDTH / 2) position.x = -OrbitalFight.GAME_WIDTH / 2;
        if (position.x < -OrbitalFight.GAME_WIDTH / 2) position.x = OrbitalFight.GAME_WIDTH / 2;
        if (position.y > OrbitalFight.GAME_HEIGHT / 2) position.y = -OrbitalFight.GAME_HEIGHT / 2;
        if (position.y < -OrbitalFight.GAME_HEIGHT / 2) position.y = OrbitalFight.GAME_HEIGHT / 2;

        bounds.setPosition(position);
    }

    public Circle getBounds() {
        return bounds;
    }
}
