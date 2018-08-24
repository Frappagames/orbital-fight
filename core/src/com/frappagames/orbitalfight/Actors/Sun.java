package com.frappagames.orbitalfight.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.frappagames.orbitalfight.Utils.Assets;

public class Sun extends AbstractPhysicObject {
    private static final int SUN_SIZE = 75;
    private static final int GRAVITY = 0;

    public Sun() {
        this.setPosition(new Vector2(0, 0), SUN_SIZE);
        this.setVelocity(new Vector2(0, 0));
        this.setGravityValue(GRAVITY);
    }

    public void update(float delta) {
        super.update(delta);
    }

    public void draw(SpriteBatch batch) {
        update(Gdx.graphics.getDeltaTime());
        batch.draw(
                Assets.star,
            this.getPosition().x - Assets.star.getWidth() / 2,
            this.getPosition().y - Assets.star.getHeight() / 2
        );
    }
}
