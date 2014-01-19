package com.anygine.game.client.screen;

public enum ScreenType {
	SPLASH, MENU, GAMEPLAY, HIGHSCORE;

	public static ScreenType getEnum(String name) {
		if ("SplashScreen".equals(name)) {
			return SPLASH;
		} else if ("MenuScreen".equals(name)) {
			return MENU;
		} else if ("SimulationScreen".equals(name)) {
			return GAMEPLAY;
		} else if ("HighscoreScreen".equals(name)) {
			return HIGHSCORE;
		} else {
			return null;
		}
	}
}
