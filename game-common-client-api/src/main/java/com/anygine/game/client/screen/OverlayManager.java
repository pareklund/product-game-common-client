package com.anygine.game.client.screen;

import playn.core.Surface;

import com.anygine.core.common.client.UpdateResult;
import com.anygine.core.common.client.input.Input;

public interface OverlayManager {
	public UpdateResult update(Input input);
	public void render(Surface surface);
}
