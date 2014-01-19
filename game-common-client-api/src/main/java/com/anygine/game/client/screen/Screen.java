package com.anygine.game.client.screen;

import playn.core.Surface;

import com.anygine.core.common.client.UpdateResult;
import com.anygine.core.common.client.input.Input;

public interface Screen {
	String getId();
	String getName();
	ScreenType getType();
	String getNext();
	UpdateResult update(Input input, float delta);
	void render(Surface surface, UpdateResult updateResult);
	void postRender(Surface surface, UpdateResult updateResult);
	void onExit();
}
