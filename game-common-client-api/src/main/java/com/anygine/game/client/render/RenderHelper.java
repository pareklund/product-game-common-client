package com.anygine.game.client.render;

import static playn.core.PlayN.graphics;

import java.util.List;

import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.Image;
import playn.core.PlayN;
import playn.core.Surface;

import com.anygine.core.common.client.domain.GameComponent;
import com.anygine.core.common.client.domain.GameComponentState;
import com.anygine.core.common.client.domain.Level;
import com.anygine.core.common.client.domain.Player;
import com.anygine.core.common.client.domain.Tile;
import com.anygine.core.common.client.geometry.Vector2;

public class RenderHelper {
	
	// TODO: Probably move into another class, not related to rendering
	public final static boolean DEBUG = true;

	public static void renderText(String text, Vector2 position, Surface surface) {
		CanvasImage textImage = PlayN.graphics().createImage(200, 50);
		Canvas textCanvas = textImage.canvas();
		textCanvas.drawText(text, 0.0f, 50.0f);
		surface.drawImage(textImage, position.X - 15.0f, position.Y - 85.0f);	
	}
	
	public static boolean isOnScreen(GameComponent<?, ?, ?, ?> gameComponent) {

		Level<?, ?, ?, ?> level = gameComponent.getLevel();
		float screenWidth = graphics().width();
		float cameraPositionX = level.getCameraPosition().X;
		float marginLeft = cameraPositionX;
		float xMin = Math.min(level.getWidth() * Tile.Width - screenWidth, marginLeft);

		float screenHeight = graphics().height();
		float cameraPositionY = level.getCameraPosition().Y;
		float marginBottom = cameraPositionY;
		float yMin = Math.min(level.getHeight() * Tile.Height - screenHeight, marginBottom);

		float positionX = gameComponent.getPosition().X;
		float positionY = gameComponent.getPosition().Y;

		return (positionX >= xMin && positionX < xMin + screenWidth + Tile.Width
				&& positionY >= yMin && positionY < yMin + screenHeight + Tile.Height);
	}

	public static 
	  <S extends GameComponentState, 
	  P extends Player<?, ?, ?, ?>, 
	  L extends Level<?, ?, ?, ?>, 
	  GC extends GameComponent<?, ?, ?, ?>> 
	  void renderGameComponents(
			List<? extends GC> gameComponents,
			GameComponentRenderer<S, P, L, GC> renderer,
			L level, float gameTime, Surface surface) {
		int renderCount = 0;
		for (GC gameComponent : gameComponents) {
			if (renderer.renderAnimation(gameComponent, gameTime, surface)) {
				renderCount++;
			}
		}
		if (RenderHelper.DEBUG) {
			renderer.renderRenderCount(renderCount, level, surface);
		}
	}

/*	
 * TODO: Remove(?)
  public static 
  <S extends GameComponentState, 
  P extends Player<?, ?, ?, ?>, 
  L extends Level<?, ?, ?, ?>, 
  GC extends GameComponent<?, ?, ?, ?>, 
  GCR extends GameComponentRenderer<? extends S, ? extends P, ? extends L, ? extends GC>> 
  boolean renderAnimation(
      GCR renderer, GC gameComponent, 
      Surface surface, float gameTime, boolean flip) {
    return renderer.renderAnimation(gameComponent, gameTime, surface)
  }
  */
	// TODO: Implement more efficiently (e.g. cache per frame)
	//       E.g. implement a FrameListener framework where classes can
	//       register for event onNewFrame()
	public static Vector2 getTopOfScreen(Level<?, ?, ?, ?> level) {
		float screenWidth = graphics().width();
		float cameraPositionX = level.getCameraPosition().X;
		float marginLeft = cameraPositionX;
		float xMin = Math.min(level.getWidth() * Tile.Width - screenWidth, marginLeft);

		float screenHeight = graphics().height();
		float cameraPositionY = level.getCameraPosition().Y;
		float marginBottom = cameraPositionY;
		float yMin = Math.min(level.getHeight() * Tile.Height - screenHeight, marginBottom);

		return new Vector2(xMin, yMin);
	}

	public static void renderProgressBar(
			Image progressBar, int progress, float x, float y, Surface surface) {
		CanvasImage progressImage = 
			PlayN.graphics().createImage(Math.max(1, progressBar.width() * progress / 500), 10);
		Canvas progressCanvas = progressImage.canvas();
		progressCanvas.drawImage(progressBar, 0.0f, 0.0f);
		surface.drawImage(progressImage, x, y);	
	}
}
