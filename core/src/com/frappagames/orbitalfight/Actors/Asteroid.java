package com.frappagames.orbitalfight.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.frappagames.orbitalfight.Utils.AbstractPhysicObject;
import com.frappagames.orbitalfight.Utils.Assets;

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
    private static final int GRAVITY = 0;

    private float   stateTime;

    public Asteroid() {
        stateTime = 0f;
        this.setPosition(new Vector2(-800, 300), ASTEROID1_SIZE);
        this.setVelocity(new Vector2(2, 0.5f));
        this.setGravityValue(GRAVITY);
    }

    public void update(float delta) {
        super.update(delta);
    }

    public void draw(SpriteBatch batch) {
        update(Gdx.graphics.getDeltaTime());

        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = (TextureRegion) Assets.asteroid2Animation.getKeyFrame(stateTime, true);
        batch.draw(
            currentFrame,
            this.getPosition().x - currentFrame.getRegionWidth() / 2,
            this.getPosition().y - currentFrame.getRegionHeight() / 2
        );
    }
}
