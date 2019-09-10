package com.frappagames.orbitalfight.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.frappagames.orbitalfight.OrbitalFight;
import com.frappagames.orbitalfight.Utils.Assets;

import java.util.Random;

import static com.frappagames.orbitalfight.OrbitalFight.GAME_HEIGHT;
import static com.frappagames.orbitalfight.OrbitalFight.GAME_WIDTH;


public class Player {
    private static final int ROTATION_SPEED = 270;
    private static final int MAX_SPEED = 400;
    private static final int ACCELERATION_RATE = 700;
    private static final int GRAVITY = 150;
    private static final int SHIP_WIDTH = 64;
    private static final int SHIP_HEIGHT = 57;
    private static final int EXPLOSION_WIDTH = 64;
    private static final int EXPLOSION_HEIGHT = 64;
    private static final int BOUNDS_RADIUS = 22;
    public static final int ENERGY_RATE = 10;
    public static final int SHOOTING_RATE = 300;

    private static final int FUEL_RATE = 10;
    private static final int SHIELD_RESTORATION_RATE = 10;

    private static final int MAX_LIFE = 1500;
    private static final int MAX_SHIELD = 10000;
    private static final int MAX_FUEL = 1000;
    private static final double MAX_ENERGIE = 100d;

    private Float angle;
    private Circle bounds;
    private TextureRegion texture, currentFrame;
    private Vector2 position, velocity, acceleration;
    private Animation shipAnimation, explodeAnimation;

    public enum Status {IDLE, MOVING, EXPLODING}

    private Status shipStatus;
    private float  stateTime;
    private int shipNumber;

    private double currentLife;
    private double currentShield;
    private double currentFuel;
    private double currentEnergie;
    private double maxLife;
    private double maxShield;
    private double maxFuel;
    private double maxEnergie;
    private long lastShoot = 0;

    public Player(int shipNumber) {
        this.shipNumber = shipNumber;
        this.setMaxLife(MAX_LIFE);
        this.setMaxShield(MAX_SHIELD);
        this.setMaxFuel(MAX_FUEL);
        this.setMaxEnergie(MAX_ENERGIE);
        texture          = shipNumber == 0 ? Assets.ship1 : Assets.ship2;
        shipAnimation    = shipNumber == 0 ? Assets.shipsAnim1 : Assets.shipsAnim2;
        explodeAnimation = Assets.shipExplosion;
        spawn();
    }

    public double getCurrentLife() {
        return currentLife;
    }

    public void setCurrentLife(double currentLife) {
        this.currentLife = currentLife;
    }

    public double getCurrentShield() {
        return currentShield;
    }

    public void setCurrentShield(double currentShield) {
        this.currentShield = currentShield;
    }

    public double getCurrentFuel() {
        return currentFuel;
    }

    public void setCurrentFuel(double currentFuel) {
        this.currentFuel = currentFuel;
    }

    public double getMaxLife() {
        return maxLife;
    }

    public void setMaxLife(double maxLife) {
        this.maxLife = maxLife;
    }

    public double getMaxShield() {
        return maxShield;
    }

    public void setMaxShield(double maxShield) {
        this.maxShield = maxShield;
    }

    public double getMaxFuel() {
        return maxFuel;
    }

    public void setMaxFuel(double maxFuel) {
        this.maxFuel = maxFuel;
    }

    public void setCurrentEnergie(double currentEnergie) {
        this.currentEnergie = currentEnergie;
    }

    public void setMaxEnergie(double maxEnergie) {
        this.maxEnergie = maxEnergie;
    }

    public void laserShot() {
        long now = TimeUtils.millis();

        if (this.currentEnergie >= ENERGY_RATE && now > this.lastShoot + Player.SHOOTING_RATE) {
            System.out.println("Joueur " + this.shipNumber + " : Tir de laser");
            this.currentEnergie -= ENERGY_RATE;
            this.lastShoot = TimeUtils.millis();
        }
    }

    private void spawn() {
        stateTime        = 0f;
        angle            = 0f;
        shipStatus       = Status.IDLE;
        velocity         = new Vector2(0, 0);
        acceleration     = new Vector2(0, ACCELERATION_RATE);
        this.setCurrentLife(MAX_LIFE);
        this.setCurrentShield(MAX_SHIELD);
        this.setCurrentFuel(MAX_FUEL);
        this.setCurrentEnergie(MAX_ENERGIE);

        Random rand = new Random();
        position = new Vector2(rand.nextInt(GAME_WIDTH - SHIP_WIDTH), rand.nextInt(GAME_HEIGHT - SHIP_HEIGHT));

        int x = rand.nextInt((GAME_WIDTH / 3) - (4 * SHIP_WIDTH)) + 2 * SHIP_WIDTH;
        int y = rand.nextInt(GAME_HEIGHT - 4 * SHIP_HEIGHT) + 2 * SHIP_HEIGHT;

        if (this.shipNumber == 0) x += 2 * GAME_WIDTH / 3;

        position     = new Vector2(x, y);
        bounds       = new Circle(x, y, BOUNDS_RADIUS);
        currentFrame = texture;
    }

    public void dispose() {
    }

    public void forward(float delta) {
        if (getCurrentFuel() > 0) {
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

            this.currentFuel -= FUEL_RATE * delta;
        }
    }

    public void turn(float delta) {
        angle += delta * ROTATION_SPEED;
        if (angle > 360) angle = angle - 360;
        if (angle < 0) angle = 360 + angle;
    }

    public void draw(SpriteBatch batch) {
        update(Gdx.graphics.getDeltaTime());

        if (shipStatus == Status.EXPLODING) {
            batch.draw(currentFrame,position.x - SHIP_WIDTH / 2, position.y - SHIP_WIDTH / 2,
                    SHIP_WIDTH / 2,
                    SHIP_WIDTH / 2,
                    EXPLOSION_WIDTH,
                    EXPLOSION_HEIGHT, 2, 2, 0
            );
        } else {
            batch.draw(
                    currentFrame,
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
    }

    public void update(float delta) {
        stateTime += Gdx.graphics.getDeltaTime();

        if (shipStatus == Status.EXPLODING) {
            if (explodeAnimation.isAnimationFinished(stateTime)) {
                spawn();
            } else {
                currentFrame = (TextureRegion) explodeAnimation.getKeyFrame(stateTime, false);
            }

            return;
        } else {
            if (shipStatus == Status.MOVING && getCurrentFuel() > 0) {
                currentFrame = (TextureRegion) shipAnimation.getKeyFrame(stateTime, true);
            } else {
                currentFrame = texture;
            }

            if (getCurrentShield() < MAX_SHIELD) {
                setCurrentShield(getCurrentShield() + SHIELD_RESTORATION_RATE * delta);

                if (getCurrentShield() > MAX_SHIELD) setCurrentShield(MAX_SHIELD);
            }
        }

//        float distance = position.dst(0, 0);
        float angle = position.angle();
        Vector2 gravity = new Vector2(
            MathUtils.cos(angle * MathUtils.degreesToRadians) * GRAVITY,
            MathUtils.sin(angle * MathUtils.degreesToRadians) * GRAVITY
        ).scl(-delta);
        velocity.add(gravity);

        Vector2 change = new Vector2(velocity).scl(delta);
        position.add(change);

        if (position.x > GAME_WIDTH / 2) position.x = -GAME_WIDTH / 2;
        if (position.x < -GAME_WIDTH / 2) position.x = GAME_WIDTH / 2;
        if (position.y > OrbitalFight.GAME_HEIGHT / 2) position.y = -OrbitalFight.GAME_HEIGHT / 2;
        if (position.y < -OrbitalFight.GAME_HEIGHT / 2) position.y = OrbitalFight.GAME_HEIGHT / 2;

        bounds.setPosition(position);

        // Regain d'énergie.
        if (this.currentEnergie < MAX_ENERGIE) {
            this.currentEnergie += delta * ENERGY_RATE;

            if (this.currentEnergie < MAX_ENERGIE) {
                this.currentEnergie = MAX_ENERGIE;
            }
        }
    }

    public void setShipStatus(Status shipStatus) {
        if (this.shipStatus == Status.EXPLODING) return;

        stateTime = 0f;
        this.shipStatus = shipStatus;
    }

    public Status getShipStatus() {
        return this.shipStatus;
    }

    public Circle getBounds() {
        return bounds;
    }

    public void applyDamages(int damageValue) {
        if (damageValue <= this.currentShield) {
            this.currentShield = this.currentShield - damageValue;
        } else {
            this.currentLife = this.currentLife - (damageValue - this.currentShield);
            this.currentShield = 0;

            if (this.currentLife <= 0) {
                this.currentLife = 0;
                this.setShipStatus(Player.Status.EXPLODING);
            }
        }
    }
}
