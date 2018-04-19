package com.frappagames.orbitalfight.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.frappagames.orbitalfight.OrbitalFight;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = OrbitalFight.GAME_TITLE;
		config.width = OrbitalFight.GAME_WIDTH / 2;
		config.height = OrbitalFight.GAME_HEIGHT / 2;
		new LwjglApplication(new OrbitalFight(), config);
	}
}
