package com.frappagames.orbitalfight.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.frappagames.orbitalfight.OrbitalFight;


public class Player {
    private static final int ROTATION_SPEED = 270;
    private static final int MAX_SPEED = 400;
    private static final int ACCELERATION_RATE = 700;
    private static final int SUN_SIZE = 156;
    private static final int GRAVITY_DISTANCE = 300;
    private static final int GRAVITY = 5;
    private CircleShape circle;
    private Float angle, speed, xSpeed, ySpeed;

    private Body body;

    private TextureRegion texture;
    private int x, y;


    private Vector2 position, velocity, acceleration;

    public Player() {
        x = 960;
        y = 200;
        xSpeed = 0f;
        ySpeed = 0f;
        angle = 0f;

        position = new Vector2(0, 0);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, ACCELERATION_RATE);

        texture = new TextureRegion(new Texture(Gdx.files.internal("ship.png")));
    }

    public void dispose() {
        circle.dispose();
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

//        float xSpeedAcceleration = -ACCELERATION_RATE * MathUtils.sin(angle * MathUtils.degreesToRadians) * delta;
//        float ySpeedAcceleration =  ACCELERATION_RATE * MathUtils.cos(angle * MathUtils.degreesToRadians) * delta;
//
//        if (Math.abs(xSpeed + xSpeedAcceleration) <= MAX_SPEED) {
//            xSpeed += xSpeedAcceleration;
//        }
//
//        if (Math.abs(ySpeed + ySpeedAcceleration) <= MAX_SPEED) {
//            ySpeed += ySpeedAcceleration;
//        }
    }

    public void turn(float delta) {
        angle += delta * ROTATION_SPEED;
        if (angle > 360) angle = angle - 360;
        if (angle < 0) angle = 360 + angle;
    }

    public void draw(SpriteBatch batch, float delta) {
        batch.draw(texture, position.x, position.y, texture.getTexture().getWidth() / 2, texture.getTexture().getHeight() / 2, texture.getTexture().getWidth(), texture.getTexture().getHeight(), 1, 1, angle);
    }

    public void update(float delta) {
        Integer gravityX = ((position.x > SUN_SIZE / 2 && position.x < GRAVITY_DISTANCE) ? - GRAVITY : ((position.x < -SUN_SIZE / 2 && position.x > -GRAVITY_DISTANCE) ? GRAVITY : 0 ));
        Integer gravityY = ((position.y > SUN_SIZE / 2 && position.x < GRAVITY_DISTANCE) ? - GRAVITY : ((position.y < -SUN_SIZE / 2 && position.x > -GRAVITY_DISTANCE) ? GRAVITY : 0 ));
        velocity.add(gravityX, gravityY);

        Vector2 change = new Vector2(velocity).scl(delta);
        position.add(change);

//        Vector2 centerPosition = new Vector2(OrbitalFight.GAME_WIDTH / 2, OrbitalFight.GAME_HEIGHT / 2);
//        Vector2 playerPosition = new Vector2(x, y);
//        Vector2 vectDirection = centerPosition.sub(playerPosition).nor();
//
//        xSpeed += GRAVITY_SPEED * vectDirection.x * delta;
//        ySpeed += GRAVITY_SPEED * vectDirection.y * delta;
//
//        x += xSpeed * delta;
//        y += ySpeed * delta;

        if (position.x > OrbitalFight.GAME_WIDTH / 2) position.x = -OrbitalFight.GAME_WIDTH / 2;
        if (position.x < -OrbitalFight.GAME_WIDTH / 2) position.x = OrbitalFight.GAME_WIDTH / 2;
        if (position.y > OrbitalFight.GAME_HEIGHT / 2) position.y = -OrbitalFight.GAME_HEIGHT / 2;
        if (position.y < -OrbitalFight.GAME_HEIGHT / 2) position.y = OrbitalFight.GAME_HEIGHT / 2;
    }
}
