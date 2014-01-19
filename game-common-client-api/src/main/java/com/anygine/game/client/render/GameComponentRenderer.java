package com.anygine.game.client.render;

import playn.core.Surface;

import com.anygine.core.common.client.domain.GameComponent;
import com.anygine.core.common.client.domain.GameComponentState;
import com.anygine.core.common.client.domain.Level;
import com.anygine.core.common.client.domain.Player;

public interface GameComponentRenderer
  <S extends GameComponentState,
  P extends Player<?, ?, ?, ?>,
  L extends Level<?, ?, ?, ?>,
  GC extends GameComponent<?, ?, ?, ?>> {

  // TODO: Revisit this
	boolean renderAnimation(GC gameComponent, float gameTime, Surface surface);

	void renderRenderCount(int renderCount, L level, Surface surface);
}
