package com.anygine.game.client;

import com.anygine.core.common.client.domain.GameComponent;
import com.anygine.core.common.client.domain.Level;
import com.anygine.core.common.client.domain.Player;
import com.anygine.core.common.client.input.InputReader;
import com.anygine.game.client.render.LevelRenderer;
import com.anygine.game.client.render.PlayerRenderer;
import com.anygine.game.client.render.SimulationRenderer;
import com.anygine.game.client.screen.ScreenManager;
import com.anygine.game.client.storage.HighscoreStorage;
import com.anygine.game.client.view.LayoutManager;
import playn.core.Game;

public interface GameplayClientInjector
  <P extends Player<?, ?>,
  L extends Level<?, ?>,
  GC extends GameComponent<?, ?>> {

  SimulationRenderer getGameplayRenderer();
  
  ScreenManager getScreenManager();

  InputReader getInputReader();

  Game getGame();

  LevelRenderer<L> getLevelRenderer();

  PlayerRenderer<P, L, GC> getPlayerRenderer();

  HighscoreStorage getHighscoreStorage();

  FontManager getFontManager();

  ProfileManager getProfileManager();

  LayoutManager getLayoutManager();

  ClientSessionManager getClientSessionManager();
}
