package com.anygine.game.client.view;

import playn.core.Canvas;
import playn.core.Color;
import playn.core.Font;
import playn.core.Font.Style;
import playn.core.Image;
import playn.core.PlayN;
import playn.core.TextFormat;
import playn.core.TextLayout;

import com.anygine.core.common.client.geometry.Vector2;
import com.anygine.game.client.common.AlignmentX;
import com.anygine.game.client.common.AlignmentY;

public class Button extends SingleViewBase implements View {

	private String text;
	private Image image;

	protected Button(
			String id, Width width, Height height, AlignmentX alignmentX, 
			AlignmentY alignmentY, String text, 
			Image image) {
		super(id, width, height, alignmentX, alignmentY);
		this.text = text;
		this.image = image;
	}

  @Override
  public float getContentWidth() {
    return image.width();
  }

  @Override
  public float getContentHeight() {
    return image.height();
  }

  @Override
  public Vector2 renderSingleView(
      Canvas canvas, Vector2 offset, float startX, float startY,
      float scaledWidth, float scaledHeight) {
    canvas.drawImage(image, startX, startY);
    Font font = PlayN.graphics().createFont("Test", Style.BOLD, 15);
/*
    TextFormat textFormat = new TextFormat(
        font, 100, TextFormat.Alignment.LEFT, Color.rgb(0, 0, 0), Effect.NONE);
        */
    TextFormat textFormat = new TextFormat(
        font, 100, TextFormat.Alignment.LEFT);
    TextLayout textLayout = PlayN.graphics().layoutText(text, textFormat);
    canvas.setStrokeColor(Color.rgb(0, 0, 0));
    canvas.strokeText(textLayout, startX + 7.0f, startY + 4.0f);
    return new Vector2(image.width(), image.height());
  }

}
