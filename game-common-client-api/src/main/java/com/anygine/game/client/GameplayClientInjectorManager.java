package com.anygine.game.client;

public class GameplayClientInjectorManager {

	private static GameplayClientInjector injector;

	public static GameplayClientInjector getInjector() {
		return injector;
	}

	public static void setInjector(GameplayClientInjector injector) {
		GameplayClientInjectorManager.injector = injector;
	}

}
