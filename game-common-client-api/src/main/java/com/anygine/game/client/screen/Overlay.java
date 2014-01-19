package com.anygine.game.client.screen;

import playn.core.Surface;

import com.anygine.core.common.client.input.Input;
import com.anygine.game.client.view.ViewAction;

public interface Overlay {
	ViewAction update(Input input);
	void render(Surface surface);
	void onInactivate();
	void onActivate();
}
