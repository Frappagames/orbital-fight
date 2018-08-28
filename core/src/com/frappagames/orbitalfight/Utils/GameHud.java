package com.frappagames.orbitalfight.Utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.frappagames.orbitalfight.Actors.Player;
import com.frappagames.orbitalfight.OrbitalFight;

public class GameHud {
    private Stage stage;
    private Batch gameBatch;

    private Player player1, player2;

    public GameHud(SpriteBatch batch, Player player1, Player player2) {
        this.gameBatch = batch;
        this.player1 = player1;
        this.player2 = player2;

        Viewport viewport = new FitViewport(OrbitalFight.GAME_WIDTH, OrbitalFight.GAME_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        PlayerStatusWidget player1Status = new PlayerStatusWidget(this.player1, PlayerStatusWidget.Orientation.LTR);
        PlayerStatusWidget player2Status = new PlayerStatusWidget(this.player2, PlayerStatusWidget.Orientation.RTL);

        table.add(player1Status).width(364).height(96).pad(20);
        table.add().expandX();
        table.add(player2Status).width(364).height(96).pad(20);

        stage.addActor(table);
    }

    public void draw() {
        gameBatch.setProjectionMatrix(this.stage.getCamera().combined);
        this.stage.act();
        this.stage.draw();
    }
}
