package com.anygine.game.client.view;

import playn.core.Canvas;

import com.anygine.core.common.client.geometry.Vector2;
import com.anygine.game.client.FontManager;
import com.anygine.game.client.GameplayClientInjectorManager;
import com.anygine.game.client.common.AlignmentX;
import com.anygine.game.client.common.AlignmentY;
import com.anygine.game.client.font.BitmapFont;

public class TextView extends SingleViewBase implements View {

	protected final FontManager fontManager;
	
	protected String text;
	
	public TextView(
	    String id, Width width, Height height, AlignmentX alignmentX, 
	    AlignmentY alignmentY, String text) {
		super(id, width, height, alignmentX, alignmentY);
		this.setText(text);
		fontManager = GameplayClientInjectorManager.getInjector().getFontManager();
	}

	public void setText(String text) {
		this.text = text;
	}

  @Override
  public float getContentWidth() {
    return fontManager.getFont().getRenderSize(
        text, parentView.getWidth()).X;
  }

  @Override
  public float getContentHeight() {
    return fontManager.getFont().getRenderSize(
        text, parentView.getWidth()).Y;
  }

  // TODO: Add support for scaling in font.renderText()
  @Override
  public Vector2 renderSingleView(
      Canvas canvas, Vector2 offset, float startX, float startY,
      float scaledWidth, float scaledHeight) {
    BitmapFont font = fontManager.getFont();
    // NOTE: Alignment is already taken care of by layout/view
    //       mechanisms - keep as LEFT + TOP (or remove from
    //       method signature if not needed outside decl. view
    //       management)
    return font.renderText(
        canvas, text, startX, startY, AlignmentX.LEFT, AlignmentY.TOP);
  }

}
