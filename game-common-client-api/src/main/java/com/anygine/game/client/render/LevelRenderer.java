package com.anygine.game.client.render;

import com.anygine.core.common.client.domain.Level;
import playn.core.Surface;

public interface LevelRenderer
  <L extends Level<?, ?>> {
	void render(L level, float gameTime, Surface surface);
}
