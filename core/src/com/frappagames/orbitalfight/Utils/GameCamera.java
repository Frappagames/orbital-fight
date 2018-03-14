package com.frappagames.orbitalfight.Utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.frappagames.orbitalfight.OrbitalFight;


public class GameCamera extends OrthographicCamera {
    private Viewport viewport;

    public GameCamera() {
        super();
        viewport = new FitViewport(OrbitalFight.GAME_WIDTH, OrbitalFight.GAME_HEIGHT, this);
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
