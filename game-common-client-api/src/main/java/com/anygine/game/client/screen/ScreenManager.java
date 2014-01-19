package com.anygine.game.client.screen;

import com.anygine.core.common.client.UpdateResult;

public interface ScreenManager {
	Screen getStartScreen();
	Screen getScreen(Screen screen, UpdateResult screenUpdateResult);
}
