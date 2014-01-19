package com.anygine.game.client.render;

import playn.core.Surface;

public interface SimulationRenderer {
	void init();
	void render(Surface surface);
	void onExit();
}
