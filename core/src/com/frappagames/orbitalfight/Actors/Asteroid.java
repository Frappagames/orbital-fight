package com.frappagames.orbitalfight.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.frappagames.orbitalfight.OrbitalFight;
import com.frappagames.orbitalfight.Utils.AbstractPhysicObject;
import com.frappagames.orbitalfight.Utils.Assets;

import java.util.Random;

/**
 * Question :
 * ==========
 *
 * 1. Les astéroids sont-il affectés par la gravité ?
 * 2. Les astéroids réapparessent-ils de l'autre coté de la map quand ils en sortent ?
 * 3. Peut-il y avoir plusieurs astéroids en même temps ?
 * 4. Sont-ils détruit lorsqu'un vaisseau s'écrase sur eux ?
 * 5. Sont-ils destructible par les armes ?
 * 5.1. Si oui, ont-ils de la "vie" ou sont t'il détruit dès le 1er impact ?
 */

public class Asteroid extends AbstractPhysicObject {
    private static final int ASTEROID1_SIZE = 32;
    private static final int ASTEROID_SPEED = 5;
    private static final int SPAWN_DISTANCE = 1200;
    private static final int GRAVITY = 0;

    private float     stateTime;
    private Animation animation;

    public Asteroid(int asteroidNumber) {
        stateTime = 0f;

        Random rand = new Random();

        // Set a random position
        int randomPositionAngle = rand.nextInt(360);
        int x = MathUtils.round(MathUtils.cosDeg(randomPositionAngle) * SPAWN_DISTANCE);
        int y = MathUtils.round(MathUtils.sinDeg(randomPositionAngle) * SPAWN_DISTANCE);

        // Set the direction to center +-45°
        int randomSpeedAngle = randomPositionAngle + 180 - rand.nextInt(90) + 45;
        float xVelocity = MathUtils.cosDeg(randomSpeedAngle) * ASTEROID_SPEED;
        float yVelocity = MathUtils.sinDeg(randomSpeedAngle) * ASTEROID_SPEED;

        this.setPosition(new Vector2(x, y), ASTEROID1_SIZE);
        this.setVelocity(new Vector2(xVelocity, yVelocity));
        this.setGravityValue(GRAVITY);
        this.setRespawn(false);

        switch (asteroidNumber) {
            case 2:
                animation = Assets.asteroid2Animation;
                break;
            case 3 :
                animation = Assets.asteroid3Animation;
                break;
            default :
                animation = Assets.asteroid1Animation;

        }
    }

    public void update(float delta) {
        super.update(delta);
    }

    public void draw(SpriteBatch batch) {
        update(Gdx.graphics.getDeltaTime());

        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = (TextureRegion) animation.getKeyFrame(stateTime, true);
        batch.draw(
            currentFrame,
            this.getPosition().x - currentFrame.getRegionWidth() / 2,
            this.getPosition().y - currentFrame.getRegionHeight() / 2
        );
    }
}
