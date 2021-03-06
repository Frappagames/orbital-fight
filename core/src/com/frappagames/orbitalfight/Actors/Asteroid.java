package com.frappagames.orbitalfight.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
 * --- : 5. Sont-ils destructible par les armes ? => Oui
 * --- : 5.1. Si oui, ont-ils de la "vie" ou sont t'il détruit dès le 1er impact ? Oui, il ont de la vie
 * --- : 5.2. Ajouter un SCALE de la taille de l'astéroid + l'appliquer à la vie dont il dispose
 * --- : 6. Ils doivent être détruits lors d'un impact avec le soleil
 * --- : 7. Ils doivent être détruits lors d'un impact avec un autre astéroid
 */

public class Asteroid extends AbstractPhysicObject {
    private static final int ASTEROID_SIZE  = 128;
    private static final int BOUNDS_SIZE    = 45;
    private static final int ASTEROID_SPEED = 5;
    private static final int SPAWN_DISTANCE = 1200;
    private static final int ASTEROID_LIFE  = 500;
    private static final int GRAVITY        = 0;
    private static final int MAX_LIFETIME   = 8;

    public enum Status {MOVING, EXPLODING}

    private Status    asteroidStatus;
    private float     stateTime;
    private float     radius;
    private Animation animation, explosion;
    private int       currentLife;
    private int       size;
    private float     lifetime;

    public Asteroid(int asteroidNumber) {
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

        explosion = Assets.shipExplosion;

        this.init();
    }

    private void init() {
        asteroidStatus = Status.MOVING;
        stateTime = 0f;
        lifetime = 0.0f;
        Random rand = new Random();

        float scale = rand.nextInt(100) / 100.0f + 0.5f;

        this.currentLife     = MathUtils.round(ASTEROID_LIFE * scale);

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
        this.setVelocity(new Vector2(xVelocity, yVelocity));

        this.setPosition(new Vector2(x, y), MathUtils.round(BOUNDS_SIZE * scale));
    }

    public void update(float delta) {
        super.update(delta);

        // If the asteroid has been around for too long, regenerate it
        lifetime += delta;
        if (lifetime > MAX_LIFETIME) {
            this.init();
        }

        // Reinitialize the asteroid when totaly exploded
        if (asteroidStatus == Status.EXPLODING && explosion.isAnimationFinished(stateTime)) {
            this.init();
        }
    }

    public void draw(SpriteBatch batch) {
        update(Gdx.graphics.getDeltaTime());

        stateTime += Gdx.graphics.getDeltaTime();
        if (asteroidStatus == Status.EXPLODING) {
            TextureRegion currentFrame = (TextureRegion) explosion.getKeyFrame(stateTime, false);
            batch.draw(
                    currentFrame,
                    this.getPosition().x - radius,
                    this.getPosition().y - radius,
                    size,
                    size
            );
        } else {
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

    public Status getAsteroidStatus() {
        return asteroidStatus;
    }

    public int getCurrentLife() {
        return currentLife;
    }

    public void applyDamages(int damageValue) {
        this.currentLife = this.currentLife - damageValue;

        if (this.getCurrentLife() <= 0) {
            asteroidStatus = Status.EXPLODING;
            stateTime = 0f;
            this.setVelocity(new Vector2(0, 0));
        }
    }
}
