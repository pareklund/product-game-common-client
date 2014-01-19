package com.anygine.game.client.render;

import playn.core.Surface;

import com.anygine.core.common.client.domain.GameComponent;
import com.anygine.core.common.client.domain.GameComponentState;
import com.anygine.core.common.client.domain.Level;
import com.anygine.core.common.client.domain.Player;

public interface LevelRenderer
/* <S extends GameComponentState,
P extends Player<S, P, L, GC>, 
L extends Level<S, P, L, GC>,
GC extends GameComponent<S, P, L, GC>> { */
  <S extends GameComponentState,
  P extends Player<?, ?, ?, ?>, 
  L extends Level<?, ?, ?, ?>,
  GC extends GameComponent<?, ?, ?, ?>> {
	void render(L level, float gameTime, Surface surface);
}
