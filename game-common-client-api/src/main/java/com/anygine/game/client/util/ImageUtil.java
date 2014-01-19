package com.anygine.game.client.util;

import static playn.core.PlayN.graphics;
import playn.core.Canvas;
import playn.core.Canvas.LineCap;
import playn.core.CanvasImage;
import playn.core.Image;
import playn.core.PlayN;

import com.anygine.game.client.common.AlignmentX;
import com.anygine.game.client.common.AlignmentY;
import com.anygine.game.client.font.BitmapFont;

public final class ImageUtil {

	public static Image createOverlay(
			int sizeX, int sizeY, String text, BitmapFont font, 
			AlignmentX alignX, AlignmentY alignY) {
		return createOverlay(sizeX, sizeY, text, font, null, alignX, alignY);
	}  

	public static Image createOverlay(
			int sizeX, int sizeY, String text, BitmapFont font, Image image, 
			AlignmentX alignX, AlignmentY alignY) {
		CanvasImage canvasImage = graphics().createImage(sizeX, sizeY);
		Canvas canvas = canvasImage.canvas();
		canvas.drawImage(PlayN.assets().getImageSync("Overlays/empty.png"), 0.0f, 0.0f);
		font.renderText(
				canvas, text, 0.0f, 0.0f, alignX, alignY);
		if (image != null) {
			canvas.drawImage(
					image, 
					canvasImage.width() / 2 - image.width() / 2, 
					80.0f);
		}
		return canvasImage;		
	}

	public static void drawSquare(
			Canvas canvas, float xMin, float yMin, float xMax, float yMax,
			int strokeColor, int strokeWidth, LineCap lineCap) {
		canvas.setStrokeColor(strokeColor);
		canvas.setStrokeWidth(strokeWidth);
		canvas.setLineCap(lineCap);
		canvas.drawLine(xMin, yMin, xMax, yMin);
		canvas.drawLine(xMax, yMin, xMax, yMax);
		canvas.drawLine(xMax, yMax, xMin, yMax);
		canvas.drawLine(xMin, yMax, xMin, yMin);
	}

}
