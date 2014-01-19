package com.anygine.game.client;

import playn.core.Game;

import com.anygine.core.common.client.domain.GameComponent;
import com.anygine.core.common.client.domain.GameComponentState;
import com.anygine.core.common.client.domain.Level;
import com.anygine.core.common.client.domain.Player;
import com.anygine.core.common.client.input.InputReader;
import com.anygine.game.client.render.LevelRenderer;
import com.anygine.game.client.render.PlayerRenderer;
import com.anygine.game.client.render.SimulationRenderer;
import com.anygine.game.client.screen.ScreenManager;
import com.anygine.game.client.storage.HighscoreStorage;
import com.anygine.game.client.view.LayoutManager;

public interface GameplayClientInjector
/* <S extends GameComponentState,
P extends Player<S, P, L, GC>, 
L extends Level<S, P, L, GC>,
GC extends GameComponent<S, P, L, GC>> */ 
  <S extends GameComponentState,
  P extends Player<?, ?, ?, ?>, 
  L extends Level<?, ?, ?, ?>,
  GC extends GameComponent<?, ?, ?, ?>> {

  SimulationRenderer getGameplayRenderer();
  
  ScreenManager getScreenManager();

 InputReader getInputReader();

  Game getGame();

  LevelRenderer<S, P, L, GC> getLevelRenderer();

  PlayerRenderer<S, P, L, GC> getPlayerRenderer();

  HighscoreStorage getHighscoreStorage();

  FontManager getFontManager();

  ProfileManager getProfileManager();

  LayoutManager getLayoutManager();

  ClientSessionManager getClientSessionManager();
}
