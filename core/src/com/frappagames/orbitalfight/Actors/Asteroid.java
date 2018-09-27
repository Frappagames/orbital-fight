package com.frappagames.orbitalfight.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.frappagames.orbitalfight.Utils.AbstractPhysicObject;
import com.frappagames.orbitalfight.Utils.Assets;

import java.util.Random;

/**
 * Question :
 * ==========
 *
 * --- 1. Les astéroids sont-il affectés par la gravité ? => NON ---
 * --- 2. Les astéroids réapparessent-ils de l'autre coté de la map quand ils en sortent ? => NON ---
 * --- 3. Peut-il y avoir plusieurs astéroids en même temps ? => OUI ---
 *  TODO => 3.1. Ajouter un pool d'astéroid + ajouter 1 ou 2 animations
 * --- 4. Sont-ils détruit lorsqu'un vaisseau s'écrase sur eux ? => NON ---
 * TODO : 5. Sont-ils destructible par les armes ? => Oui
 * TODO : 5.1. Si oui, ont-ils de la "vie" ou sont t'il détruit dès le 1er impact ? Oui, il ont de la vie
 * TODO : 5.2. Ajouter un SCALE de la taille de l'astéroid + l'appliquer à la vie dont il dispose
 * TODO : 6. Ils doivent être détruits lors d'un impact avec le soleil
 * TODO : 7. Ils doivent être détruits lors d'un impact avec un autre astéroid
 */

public class Asteroid extends AbstractPhysicObject {
    private static final int ASTEROID_SIZE  = 128;
    private static final int BOUNDS_SIZE    = 45;
    private static final int ASTEROID_SPEED = 5;
    private static final int SPAWN_DISTANCE = 1200;
    private static final int ASTEROID_LIFE  = 500;
    private static final int GRAVITY        = 0;

    private float     stateTime, scale, radius;
    private Animation animation;
    private int       maxLife, currentLife, size;

    public Asteroid(int asteroidNumber) {
        stateTime = 0f;

        Random rand = new Random();

        scale = rand.nextInt(100) / 100.0f + 0.5f;

        maxLife = MathUtils.round(ASTEROID_LIFE * scale);
        currentLife = maxLife;
        size = MathUtils.round(ASTEROID_SIZE * scale);
        radius = size / 2;

        // Set a random position
        int randomPositionAngle = rand.nextInt(360);
        int x = MathUtils.round(MathUtils.cosDeg(randomPositionAngle) * SPAWN_DISTANCE);
        int y = MathUtils.round(MathUtils.sinDeg(randomPositionAngle) * SPAWN_DISTANCE);

        // Set the direction to center +-45°
        int randomSpeedAngle = randomPositionAngle + 180 - rand.nextInt(90) + 45;
        float xVelocity = MathUtils.cosDeg(randomSpeedAngle) * ASTEROID_SPEED;
        float yVelocity = MathUtils.sinDeg(randomSpeedAngle) * ASTEROID_SPEED;

        this.setPosition(new Vector2(x, y), MathUtils.round(BOUNDS_SIZE * scale));
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
            this.getPosition().x - radius,
            this.getPosition().y - radius,
            size,
            size
        );
    }
}
