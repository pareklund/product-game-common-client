package com.anygine.game.client.render;

import com.anygine.core.common.client.domain.GameComponent;
import com.anygine.core.common.client.domain.Level;
import com.anygine.core.common.client.domain.Player;
import playn.core.Surface;

public interface PlayerRenderer 
  <P extends Player<?, ?>,
  L extends Level<?, ?>,
  GC extends GameComponent<?, ?>>
  extends GameComponentRenderer<L, GC> {
	boolean renderAnimation(P player, float gameTime, Surface surface);
}
