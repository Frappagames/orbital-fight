package com.frappagames.orbitalfight.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.frappagames.orbitalfight.OrbitalFight;

import static com.frappagames.orbitalfight.OrbitalFight.GAME_WIDTH;

public abstract class AbstractPhysicObject {

    private Vector2 position;
    private Vector2 velocity;
    private Circle  bounds;
    private int gravityValue;
    private boolean respawn = true;

    public void setPosition(Vector2 position, int size) {
        this.position = position;
        this.bounds = new Circle(position.x, position.y, size);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Circle getBounds() {
        return bounds;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setGravityValue(int gravityValue) {
        this.gravityValue = gravityValue;
    }


    public boolean isRespawn() {
        return respawn;
    }

    public void setRespawn(boolean respawn) {
        this.respawn = respawn;
    }

    public void update(float delta) {
        position = position.add(velocity);

        float angle = position.angle();
        Vector2 gravity = new Vector2(
                MathUtils.cos(angle * MathUtils.degreesToRadians) * gravityValue,
                MathUtils.sin(angle * MathUtils.degreesToRadians) * gravityValue
        ).scl(-delta);
        velocity.add(gravity);

        Vector2 change = new Vector2(velocity).scl(Gdx.graphics.getDeltaTime());
        position.add(change);

        if (isRespawn()) {
            if (position.x > GAME_WIDTH / 2) position.x = -GAME_WIDTH / 2;
            if (position.x < -GAME_WIDTH / 2) position.x = GAME_WIDTH / 2;
            if (position.y > OrbitalFight.GAME_HEIGHT / 2)
                position.y = -OrbitalFight.GAME_HEIGHT / 2;
            if (position.y < -OrbitalFight.GAME_HEIGHT / 2)
                position.y = OrbitalFight.GAME_HEIGHT / 2;
        }

        bounds.setPosition(position.x, position.y);
    }
}
