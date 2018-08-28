package com.frappagames.orbitalfight.Utils;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.frappagames.orbitalfight.Actors.Player;

public class PlayerStatusWidget extends Widget {
    public enum Orientation{LTR, RTL}
    private Orientation orientation;
    private Player player;

    PlayerStatusWidget(Player player, Orientation orientation) {
        this.player      = player;
        this.orientation = orientation;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        double lifeCount   = (player.getCurrentLife() / player.getMaxLife()) * 20;
        double shieldCount = (player.getCurrentShield() / player.getMaxShield()) * 20;
        double fuelCount   = (player.getCurrentFuel() / player.getMaxFuel()) * 20;

        if (this.orientation == Orientation.LTR) {
            // Display player 1 stats background
            batch.draw(Assets.player1Stats.getRegion(), this.getX(), this.getY());

            // show Life
            for (int i = 0 ; i < 20; i++) {
                int x = (int) this.getX() + 9 * i + 167;

                if (i <= lifeCount) {
                    batch.draw(Assets.lifeSquare.getRegion(), x, this.getY() + 59);
                } else {
                    batch.draw(Assets.emptySquare.getRegion(), x, this.getY() + 59);
                }
            }

            // show Shield
            for (int i = 0 ; i < 20; i++) {
                int x = (int) this.getX() + 9 * i + 167;

                if (i <= shieldCount) {
                    batch.draw(Assets.shieldSquare.getRegion(), x, this.getY() + 42);
                } else {
                    batch.draw(Assets.emptySquare.getRegion(), x, this.getY() + 42);
                }
            }

            // show Fuel
            for (int i = 0 ; i < 20; i++) {
                int x = (int) this.getX() + 9 * i + 167;

                if (i <= fuelCount) {
                    batch.draw(Assets.fuelSquare.getRegion(), x, this.getY() + 25);
                } else {
                    batch.draw(Assets.emptySquare.getRegion(), x, this.getY() + 25);
                }
            }

        } else {
            // Display player 2 stats background
            batch.draw(Assets.player2Stats.getRegion(), this.getX(), this.getY());

            // show Life
            for (int i = 0 ; i < 20; i++) {
                int x = (int) this.getX() + 9 * (20 - i) + 10;

                if (i <= lifeCount) {
                    batch.draw(Assets.lifeSquare.getRegion(), x, this.getY() + 59);
                } else {
                    batch.draw(Assets.emptySquare.getRegion(), x, this.getY() + 59);
                }
            }

            // show Shield
            for (int i = 0 ; i < 20; i++) {
                int x = (int) this.getX() + 9 * (20 - i) + 10;

                if (i <= shieldCount) {
                    batch.draw(Assets.shieldSquare.getRegion(), x, this.getY() + 42);
                } else {
                    batch.draw(Assets.emptySquare.getRegion(), x, this.getY() + 42);
                }
            }

            // show Fuel
            for (int i = 0 ; i < 20; i++) {
                int x = (int) this.getX() + 9 * (20 - i) + 10;

                if (i <= fuelCount) {
                    batch.draw(Assets.fuelSquare.getRegion(), x, this.getY() + 25);
                } else {
                    batch.draw(Assets.emptySquare.getRegion(), x, this.getY() + 25);
                }
            }
        }
    }
}
