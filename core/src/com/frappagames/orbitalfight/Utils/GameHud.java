package com.frappagames.orbitalfight.Utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.frappagames.orbitalfight.OrbitalFight;

import java.util.Locale;

public class GameHud {
    public  Stage    stage;
    private Viewport viewport;

    private Integer playerMaxLife;
    private Integer playerMaxFuel;
    private Integer playerMaxShield;
    private Integer playerMaxEnergy;
    private Integer playerMaxRocket;

    private Integer playerCurrentLife;
    private Integer playerCurrentFuel;
    private Integer playerCurrentShield;
    private Integer playerCurrentEnergy;
    private Integer playerCurrentRocket;

    Label lifeLabel;
    Label fuelLabel;
    Label shieldLabel;
    Label energyLabel;
    Label rocketLabel;

    public GameHud(SpriteBatch batch) {
        viewport = new FitViewport(OrbitalFight.GAME_WIDTH, OrbitalFight.GAME_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        lifeLabel = new Label(String.format(Locale.FRANCE, "Life: %03d / %03d", playerCurrentLife, playerMaxLife), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        fuelLabel = new Label(String.format(Locale.FRANCE,"Fuel: %03d / %03d", playerCurrentFuel, playerMaxFuel), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        shieldLabel = new Label(String.format(Locale.FRANCE,"Shield: %03d / %03d", playerCurrentShield, playerMaxShield), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        energyLabel = new Label(String.format(Locale.FRANCE,"Energy: %03d / %03d", playerCurrentEnergy, playerMaxEnergy), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        rocketLabel = new Label(String.format(Locale.FRANCE,"Rocket: %01d / %01d", playerCurrentRocket, playerMaxRocket), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        table.add(lifeLabel).expandX().padTop(10);
        table.add(fuelLabel).expandX().padTop(10);
        table.add(shieldLabel).expandX().padTop(10);
        table.add(energyLabel).expandX().padTop(10);
        table.add(rocketLabel).expandX().padTop(10);

        stage.addActor(table);
    }

    public void setPlayerMaxLife(Integer playerMaxLife) {
        this.playerMaxLife = playerMaxLife;
        this.playerCurrentLife = playerMaxLife;
        lifeLabel.setText(String.format(Locale.FRANCE, "Life: %03d / %03d", playerCurrentLife, playerMaxLife));
    }

    public void setPlayerMaxFuel(Integer playerMaxFuel) {
        this.playerMaxFuel = playerMaxFuel;
        this.playerCurrentFuel = playerMaxFuel;
        fuelLabel.setText(String.format(Locale.FRANCE,"Fuel: %03d / %03d", playerCurrentFuel, playerMaxFuel));
    }

    public void setPlayerMaxShield(Integer playerMaxShield) {
        this.playerMaxShield = playerMaxShield;
        this.playerCurrentShield = playerMaxShield;
        shieldLabel.setText(String.format(Locale.FRANCE,"Shield: %03d / %03d", playerCurrentShield, playerMaxShield));
    }

    public void setPlayerMaxEnergy(Integer playerMaxEnergy) {
        this.playerMaxEnergy = playerMaxEnergy;
        this.playerCurrentEnergy = playerMaxEnergy;
        energyLabel.setText(String.format(Locale.FRANCE,"Energy: %03d / %03d", playerCurrentEnergy, playerMaxEnergy));
    }

    public void setPlayerMaxRocket(Integer playerMaxRocket) {
        this.playerMaxRocket = playerMaxRocket;
        this.playerCurrentRocket = playerMaxRocket;
        rocketLabel.setText(String.format(Locale.FRANCE,"Rocket: %01d / %01d", playerCurrentRocket, playerMaxRocket));
    }

    public void setPlayerCurrentLife(Integer playerCurrentLife) {
        this.playerCurrentLife = playerCurrentLife;
        lifeLabel.setText(String.format(Locale.FRANCE, "Life: %03d / %03d", playerCurrentLife, playerMaxLife));
    }

    public void setPlayerCurrentFuel(Integer playerCurrentFuel) {
        this.playerCurrentFuel = playerCurrentFuel;
        fuelLabel.setText(String.format(Locale.FRANCE,"Fuel: %03d / %03d", playerCurrentFuel, playerMaxFuel));
    }

    public void setPlayerCurrentShield(Integer playerCurrentShield) {
        this.playerCurrentShield = playerCurrentShield;
        shieldLabel.setText(String.format(Locale.FRANCE,"Shield: %03d / %03d", playerCurrentShield, playerMaxShield));
    }

    public void setPlayerCurrentEnergy(Integer playerCurrentEnergy) {
        this.playerCurrentEnergy = playerCurrentEnergy;
        energyLabel.setText(String.format(Locale.FRANCE,"Energy: %03d / %03d", playerCurrentEnergy, playerMaxEnergy));
    }

    public void setPlayerCurrentRocket(Integer playerCurrentRocket) {
        this.playerCurrentRocket = playerCurrentRocket;
        rocketLabel.setText(String.format(Locale.FRANCE,"Rocket: %01d / %01d", playerCurrentRocket, playerMaxRocket));
    }
}
